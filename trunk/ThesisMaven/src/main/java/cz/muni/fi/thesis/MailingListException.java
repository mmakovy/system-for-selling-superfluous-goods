package cz.muni.fi.thesis;

/**
 * The Exception for errors occuring in mailing list management.
 * 
 * @author Matus Makovy
 */
public class MailingListException extends Exception {
    
    public MailingListException(String msg) {
        super(msg);
    }
    
    public MailingListException(Throwable cause) {
        super(cause);
    }
    
    public MailingListException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
