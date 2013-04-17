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
                PreparedStatement st = con.prepareStatement("SELECT userId,username,hash_pwd,hash_ver,active FROM users WHERE username = ? AND hash_pwd = ?;");
                st.setString(1, username);
                st.setBlob(2, blobHash);

                ResultSet usersDB = st.executeQuery();
                User user = null;
                if (usersDB.next()) {
                    user = new User();
                    user.setId(usersDB.getLong("userId"));
                    user.setUserName(usersDB.getString("username"));
                    Blob blob = usersDB.getBlob("hash_pwd");
                    int blobLength = (int) blob.length();
                    user.setHash(blob.getBytes(1, blobLength));
                    user.setHashVer(usersDB.getString("hash_ver"));
                    user.setActive(usersDB.getInt("active"));
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
                PreparedStatement st = con.prepareStatement("SELECT userId,username,hash_pwd,hash_ver,active FROM users WHERE userId = ?;");
                st.setLong(1, id);

                ResultSet usersDB = st.executeQuery();
                User user = null;
                if (usersDB.next()) {
                    user = new User();
                    user.setId(usersDB.getLong("userId"));
                    user.setUserName(usersDB.getString("username"));
                    user.setHashVer(usersDB.getString("hash_ver"));
                    Blob blob = usersDB.getBlob("hash_pwd");
                    user.setActive(usersDB.getInt("active"));
                    int blobLength = (int) blob.length();
                    user.setHash(blob.getBytes(1, blobLength));
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
                PreparedStatement st = con.prepareStatement("UPDATE users SET active=1 WHERE hash_ver=?");
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

        if (user.getActive() == 1) {
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
                PreparedStatement st = con.prepareStatement("SELECT username FROM users WHERE username=?;");
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

    public String forgotPassword(String email) throws DatabaseException {
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
                PreparedStatement st = con.prepareStatement("UPDATE users SET hash_pwd=? WHERE userId=?;");
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
                changePasswordInDB(user.getId(),passwordHash);
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
