package cz.muni.fi.thesis;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matus
 */
public class UserManagerImpl implements UserManager {

    final static Logger log = LoggerFactory.getLogger(CompanyManagerImpl.class);

    public void updateUser(User user) throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<User> getAllUsers() throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public User findUser(String username, String password) throws DatabaseException {

        if (username == null) {
            throw new IllegalArgumentException("Username is null");
        }

        if (password == null) {
            throw new IllegalArgumentException("Password is null");
        }

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
        }
        try {
            md.update(password.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
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

        Connection con = DatabaseConnection.getConnection();

        if (con == null) {
            throw new DatabaseException("Conection to database wasnt established");
        } else {
            try {
                PreparedStatement st = con.prepareStatement("SELECT userId,username,hash_pwd FROM users WHERE username = ? AND hash_pwd = ?;");
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
                PreparedStatement st = con.prepareStatement("SELECT userId,username,hash_pwd,hash_ver FROM users WHERE userId = ?;");
                st.setLong(1, id);

                ResultSet usersDB = st.executeQuery();
                User user = null;
                if (usersDB.next()) {
                    user = new User();
                    user.setId(usersDB.getLong("userId"));
                    user.setUserName(usersDB.getString("username"));
                    user.setHashVer(usersDB.getString("hash_ver"));
                    Blob blob = usersDB.getBlob("hash_pwd");
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
    public void verifyEmail(String code) throws DatabaseException, UserException{
        
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
    public boolean isVerified(User user) throws DatabaseException, UserException{
        
        if (user == null) {
            throw new IllegalArgumentException("code");
        }
        
        Connection con = DatabaseConnection.getConnection();

        if (con == null) {
            throw new DatabaseException("Conection to database wasnt established");
        } else {
            try {
                PreparedStatement st = con.prepareStatement("SELECT active FROM users WHERE userId=?");                                                       
                st.setLong(1, user.getId());

                ResultSet usersDB = st.executeQuery();
                int active = 2;
                
                while (usersDB.next()){
                    active = usersDB.getInt("active");
                }
                
                if (active == 0) {
                    return false;
                } else if (active == 2) {
                    throw new UserException("User wasnt found in database");
                } else {
                    return true;
                }


            } catch (SQLException ex) {
                log.error(ex.getMessage());
            } finally {
                DatabaseConnection.closeConnection(con);
            }
        }
        return false;
    }
}
