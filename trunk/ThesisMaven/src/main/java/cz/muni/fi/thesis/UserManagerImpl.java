package cz.muni.fi.thesis;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.UUID;
import java.util.logging.Level;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matus Makovy
 */
public class UserManagerImpl implements UserManager {

    final static Logger log = LoggerFactory.getLogger(UserManagerImpl.class);

    public User findUser(String username, String password) throws DatabaseException {

        if (username == null) {
            throw new IllegalArgumentException("Username is null");
        }

        if (password == null) {
            throw new IllegalArgumentException("Password is null");
        }

        Blob blobHash = hashPassword(password);

        Connection con = DatabaseConnection.getConnection();

        if (con == null) {
            throw new DatabaseException("Conection to database wasnt established");
        } else {
            try {
                PreparedStatement st = con.prepareStatement("SELECT company_id,username,hash_password,verification_code,verified FROM user WHERE username = ? AND hash_password = ?;");
                st.setString(1, username);
                st.setBlob(2, blobHash);

                ResultSet usersDB = st.executeQuery();
                User user = null;
                if (usersDB.next()) {
                    user = new User();
                    user.setIdCompany(usersDB.getLong("company_id"));
                    user.setUserName(usersDB.getString("username"));
                    Blob blob = usersDB.getBlob("hash_password");
                    int blobLength = (int) blob.length();
                    user.setPasswordHash(blob.getBytes(1, blobLength));
                    user.setVerificationString(usersDB.getString("verification_code"));
                    user.setVerified(usersDB.getInt("verified"));
                    return user;
                } else {
                    return null;
                }

            } catch (SQLException ex) {
                log.error(ex.getMessage());
            } finally {
                DatabaseConnection.closeConnection(con);
            }
        }
        return null;
    }

    public User getUser(Long id) throws DatabaseException {

        if (id == null) {
            throw new IllegalArgumentException("ID is null");
        }

        Connection con = DatabaseConnection.getConnection();

        if (con == null) {
            throw new DatabaseException("Conection to database wasnt established");
        } else {
            try {
                PreparedStatement st = con.prepareStatement("SELECT company_id,username,hash_password,verification_code,verified FROM user WHERE company_id = ?;");
                st.setLong(1, id);

                ResultSet usersDB = st.executeQuery();
                User user = null;
                if (usersDB.next()) {
                    user = new User();
                    user.setIdCompany(usersDB.getLong("company_id"));
                    user.setUserName(usersDB.getString("username"));
                    user.setVerificationString(usersDB.getString("verification_code"));
                    Blob blob = usersDB.getBlob("hash_password");
                    user.setVerified(usersDB.getInt("verified"));
                    int blobLength = (int) blob.length();
                    user.setPasswordHash(blob.getBytes(1, blobLength));
                    return user;
                } else {
                    return null;
                }

            } catch (SQLException ex) {
                log.error(ex.getMessage());
            } finally {
                DatabaseConnection.closeConnection(con);
            }
        }
        return null;
    }

    @Override
    public void verifyEmail(String code) throws DatabaseException, UserException {

        if (code == null) {
            throw new IllegalArgumentException("code");
        }

        Connection con = DatabaseConnection.getConnection();

        if (con == null) {
            throw new DatabaseException("Conection to database wasnt established");
        } else {
            try {
                PreparedStatement st = con.prepareStatement("UPDATE user SET verified=1 WHERE verification_code=?");
                st.setString(1, code);

                int result = st.executeUpdate();
                if (result == 0) {
                    throw new UserException("E-mail wasnt verified, code wasnt found in DB");
                }


            } catch (SQLException ex) {
                log.error(ex.getMessage());
            } finally {
                DatabaseConnection.closeConnection(con);
            }
        }
    }

    @Override
    public boolean isVerified(User user) throws DatabaseException, UserException {

        if (user == null) {
            throw new IllegalArgumentException("code");
        }

        if (user.getVerified() == 1) {
            return true;
        }

        return false;
    }

    public boolean isUsernameInDatabase(String username) throws DatabaseException {
        if (username == null) {
            throw new IllegalArgumentException("username");
        }

        Connection con = DatabaseConnection.getConnection();

        if (con == null) {
            throw new DatabaseException("Conection to database wasnt established");
        } else {
            try {
                PreparedStatement st = con.prepareStatement("SELECT username FROM user WHERE username=?;");
                st.setString(1, username);

                ResultSet usersDB = st.executeQuery();

                if (usersDB.next()) {
                    return true;
                }

                return false;

            } catch (SQLException ex) {
                log.error(ex.getMessage());
            } finally {
                DatabaseConnection.closeConnection(con);
            }
            return false;
        }
    }

    public String newPassword(String email) throws DatabaseException {
        
        if (email == null) {
            throw new IllegalArgumentException("email");
        }

        CompanyManager companyManager = new CompanyManagerImpl();
        Company company = companyManager.getCompanyByEmail(email);


        if (company == null) {
            return null;
        } else {

            Long id = company.getId();

            String password = UUID.randomUUID().toString().substring(0, 8);

            Blob blobHash = hashPassword(password);

            try {
                changePasswordInDB(id, blobHash);
            } catch (UserException ex) {
                return null;
            }

            return password;
        }

    }

    private void changePasswordInDB(Long id, Blob blobHash) throws DatabaseException, UserException {

        if (id == null) {
            throw new IllegalArgumentException("id");
        }

        if (blobHash == null) {
            throw new IllegalArgumentException("hash");
        }

        Connection con = DatabaseConnection.getConnection();

        if (con == null) {
            throw new DatabaseException("Connection to DB wasnt established");
        } else {
            try {
                PreparedStatement st = con.prepareStatement("UPDATE user SET hash_password=? WHERE company_id=?;");
                st.setBlob(1, blobHash);
                st.setLong(2, id);

                int result = st.executeUpdate();

                if (result == 0) {
                    throw new UserException("Password wasnt updated");
                }
            } catch (SQLException ex) {
                log.error(ex.getMessage());
            } finally {
                DatabaseConnection.closeConnection(con);
            }
        }
    }

    @Override
    public void changePassword(User user, String oldPassword, String newPassword) throws DatabaseException,UserException {

        if (user == null) {
            throw new IllegalArgumentException("user");
        }

        if (oldPassword == null) {
            throw new IllegalArgumentException("oldPassword");
        }

        if (newPassword == null) {
            throw new IllegalArgumentException("newPassword");
        }      
        
        if (user.equals(findUser(user.getUserName(), oldPassword))) {
            
            Connection con = DatabaseConnection.getConnection();
            if (con == null) {
                throw new DatabaseException("Connection to database wasnt established");
            } else {
                Blob passwordHash = hashPassword(newPassword);
                changePasswordInDB(user.getIdCompany(),passwordHash);
            }
        } else {
            throw new UserException("Wrong OLD password");
        }

    }
    
    @Override
    public Blob hashPassword(String password) {
        
        MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException ex) {
            }
            try {
                md.update(password.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException ex) {
                java.util.logging.Logger.getLogger(CompanyManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            byte[] digest = md.digest();

            Blob blobHash = null;

            try {
                blobHash = new SerialBlob(digest);
            } catch (SerialException ex) {
                java.util.logging.Logger.getLogger(CompanyManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(CompanyManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return blobHash;
        
    }
}
