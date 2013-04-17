package cz.muni.fi.thesis;

/**
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
