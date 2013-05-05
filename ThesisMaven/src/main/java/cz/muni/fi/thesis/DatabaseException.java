package cz.muni.fi.thesis;

/**
 * The Exception for errors occuring in connection management.
 * 
 * @author Matus Makovy
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
