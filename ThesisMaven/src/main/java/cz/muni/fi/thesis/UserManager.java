package cz.muni.fi.thesis;

import java.sql.Blob;

/**
 *
 * @author Matus Makovy
 */
public interface UserManager {
       
    User findUser(String username, String password) throws DatabaseException;
    User getUser(Long id) throws DatabaseException;
    void verifyEmail(String code)throws DatabaseException, UserException;
    boolean isVerified(User user)throws DatabaseException, UserException;
    boolean isUsernameInDatabase(String username) throws DatabaseException;
    String forgotPassword(String email) throws DatabaseException;
    void changePassword(User user, String oldPassword,String newPassword) throws DatabaseException, UserException;
    Blob hashPassword(String password);
    
}
