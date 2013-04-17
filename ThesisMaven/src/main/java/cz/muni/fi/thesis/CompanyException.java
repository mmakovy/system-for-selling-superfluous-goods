package cz.muni.fi.thesis;

/**
 *  The exception for manipulations with objects of class Company
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
