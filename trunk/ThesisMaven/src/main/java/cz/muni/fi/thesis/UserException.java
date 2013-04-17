package cz.muni.fi.thesis;

/**
 *
 * @author Matus Makovy
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
