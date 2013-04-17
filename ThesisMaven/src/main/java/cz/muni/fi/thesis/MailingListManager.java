package cz.muni.fi.thesis;

import java.util.List;

/**
 *
 * @author Matus Makovy
 */
public interface MailingListManager {
    
    void addEmail(String email, Long id) throws DatabaseException, MailingListException;
    void removeEmail(String email, Long id) throws DatabaseException, MailingListException;
    List<String> getEmails(Long id) throws DatabaseException, MailingListException;
    List<Offer> getOffers(String email) throws DatabaseException, MailingListException, OfferException;
    void removeAllEntriesFrom(String email) throws DatabaseException, MailingListException;
}
