/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.thesis;

/**
 *
 * @author matus
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
