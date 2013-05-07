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
    void verifyEmail(String code)throws DatabaseException, UserException;
    
    /**
     * Determines whether the user e-mail address is verified
     * 
     * @param user specifies user, about which we need information
     * @return true when user address is verified, false otherwise
     * @throws DatabaseException when the connection to database wasn't established
     * @throws UserException on SQL error, or when code wasnt found in database
     */
    boolean isVerified(User user)throws DatabaseException, UserException;
    
    /**
     * Determines whether the username is in database or not
     * 
     * @param username specifies submitted username
     * @return true when username is in database, false otherwise
     * @throws DatabaseException when the connection to database wasn't established
     */
    boolean isUsernameInDatabase(String username) throws DatabaseException;
    
    /**
     * Method that takes care of "forgot password" functionality
     * 
     * @param email specifies e-mail address of company that doesn't know the passwrod
     * @return new password
     * @throws DatabaseException  when the connection to database wasn't established
     * @throws UserException when problem with hashing occurs
     */
    String newPassword(String email) throws DatabaseException, UserException;
    
    /**
     * Method that takes care of "change password" functionality
     * 
     * @param user specifies user that wants to change password
     * @param oldPassword represents old password
     * @param newPassword represents new password
     * @throws DatabaseException when the connection to database wasn't established
     * @throws UserException when other errors occur
     */
    void changePassword(User user, String oldPassword,String newPassword) throws DatabaseException, UserException;
    
    /**
     * Hashes entered string(password)
     * 
     * @param password represents password to be hashed
     * @return hash of password
     */
    Blob hashPassword(String password);
    
}
