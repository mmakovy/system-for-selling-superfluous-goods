/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.thesis;

/**
 *
 * @author matus
 */
public class DatabaseException extends Exception{
    
    public DatabaseException(String msg) {
        super(msg);
    }
    
    public DatabaseException(Throwable cause) {
        super(cause);
    }
    
    public DatabaseException(String msg, Throwable cause) {
        super(msg, cause);
    }
    
}