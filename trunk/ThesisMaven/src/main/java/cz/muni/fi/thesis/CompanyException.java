package cz.muni.fi.thesis;

/**
 * The Exception for errors occuring in company management.
 * 
 * @author Matus Makovy
 */
public class CompanyException extends Exception {
    
    public CompanyException(String msg) {
        super(msg);
    }
    
    public CompanyException(Throwable cause) {
        super(cause);
    }
    
    public CompanyException(String msg, Throwable cause) {
        super(msg, cause);
    }
    
}
