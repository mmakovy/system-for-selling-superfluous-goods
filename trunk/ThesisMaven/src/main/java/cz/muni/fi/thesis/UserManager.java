package cz.muni.fi.thesis;

import java.sql.Blob;

/**
 * Interface for manipulation with objects of class User 
 * 
 * @author Matus Makovy
 */
public interface UserManager {
      
    /**
     * Finds user in database according to user name and password
     * 
     * @param username specifies user name to search in database
     * @param password specifies password to search in database
     * @return user if it finds some, null otherwise
     * @throws DatabaseException when the connection to database wasn't established
     */
    User findUser(String username, String password) throws DatabaseException;
    
    /**
     * Returns user according to id
     * 
     * @param id specifies id of user
     * @return user if system finds some, null otherwise
     * @throws DatabaseException when the connection to database wasn't established
     */
    User getUser(Long id) throws DatabaseException;
    
    /**
     * Changes the verified value in database 
     * 
     * @param code specifies identification code from URL
     * @throws DatabaseException when the connection to database wasn't established
     * @throws UserException when code is wrong
     */
    void verifyEmail(String code)throws DatabaseException;
    
    /**
     * Determines whether the user e-mail address is verified
     * 
     * @param user specifies user, about which we need information
     * @return true when user address is verified, false otherwise
     * @throws DatabaseException when the connection to database wasn't established
     */
    boolean isVerified(User user)throws DatabaseException, UserException;
    
    /**
     * 
     * 
     * @param username
     * @return
     * @throws DatabaseException 
     */
    boolean isUsernameInDatabase(String username) throws DatabaseException;
    String newPassword(String email) throws DatabaseException, UserException;
    void changePassword(User user, String oldPassword,String newPassword) throws DatabaseException, UserException;
    Blob hashPassword(String password);
    
}
