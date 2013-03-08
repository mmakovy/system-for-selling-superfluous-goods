/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.thesis;

import java.util.List;

/**
 *
 * @author matus
 */
public interface UserManager {
    
    List<User> getAllUsers()throws DatabaseException;
    User findUser(String username, String password) throws DatabaseException;
    User getUser(Long id) throws DatabaseException;
    void verifyEmail(String code)throws DatabaseException, UserException;
    boolean isVerified(User user)throws DatabaseException, UserException;
    
}
