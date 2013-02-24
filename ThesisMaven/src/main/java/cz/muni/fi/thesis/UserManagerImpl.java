package cz.muni.fi.thesis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matus
 */
public class UserManagerImpl implements UserManager {

    final static Logger log = LoggerFactory.getLogger(CompanyManagerImpl.class);

    public void updateUser(User user) throws DatabaseException{
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<User> getAllUsers() throws DatabaseException{
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public User findUser(String username, String password) throws DatabaseException {
        
        if (username == null) {
            throw new IllegalArgumentException("Username is null");
        }
        
        if (password == null) {
            throw new IllegalArgumentException("Password is null");
        }
        
        Connection con = DatabaseConnection.getConnection();
        
        if (con == null) {
            throw new DatabaseException("Conection to database wasnt established");
        } else {
            try {
                PreparedStatement st = con.prepareStatement("SELECT userId,username,passwd FROM users WHERE username = ? AND passwd = ?;");
                st.setString(1,username);
                st.setString(2,password);
                
                ResultSet usersDB = st.executeQuery();
                User user = null;
                if (usersDB.next()) {
                    user = new User();
                    user.setId(usersDB.getLong("userId"));
                    user.setUserName(usersDB.getString("username"));
                    user.setPassword(usersDB.getString("passwd"));
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
                PreparedStatement st = con.prepareStatement("SELECT userId,username,passwd FROM users WHERE userId = ?;");
                st.setLong(1,id);
                
                ResultSet usersDB = st.executeQuery();
                User user = null;
                if (usersDB.next()) {
                    user = new User();
                    user.setId(usersDB.getLong("userId"));
                    user.setUserName(usersDB.getString("username"));
                    user.setPassword(usersDB.getString("passwd"));
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
}
