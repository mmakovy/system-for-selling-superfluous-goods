package cz.muni.fi.thesis;

/**
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
