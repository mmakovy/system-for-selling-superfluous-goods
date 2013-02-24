/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.thesis;

/**
 *
 * @author matus
 */
public class UserException extends Exception {
    
    public UserException(String msg) {
        super(msg);
    }
    
    public UserException(Throwable cause) {
        super(cause);
    }
    
    public UserException(String msg, Throwable cause) {
        super(msg, cause);
    }
    
}
