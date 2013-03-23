/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.thesis;

/**
 *
 * @author matus
 */
public interface UserManager {
       
    User findUser(String username, String password) throws DatabaseException;
    User getUser(Long id) throws DatabaseException;
    void verifyEmail(String code)throws DatabaseException, UserException;
    boolean isVerified(User user)throws DatabaseException, UserException;
    boolean isUsernameInDatabase(String username) throws DatabaseException;
    String forgotPassword(String email) throws DatabaseException;
    
}
