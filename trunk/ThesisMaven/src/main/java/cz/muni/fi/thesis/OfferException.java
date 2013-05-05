package cz.muni.fi.thesis;

/**
 * Exception for errors occuring in offer management
 * 
 * @author Matus Makovy
 */
public class OfferException extends Exception {
    
    public OfferException(String msg) {
        super(msg);
    }
    
    public OfferException(Throwable cause) {
        super(cause);
    }
    
    public OfferException(String msg, Throwable cause) {
        super(msg, cause);
    }
    
}
