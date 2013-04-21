package cz.muni.fi.thesis;

import java.util.List;

/**
 *
 * @author Matus Makovy
 */
public interface MailingListManager {
    
    void addEntry(Company company, Offer offer) throws DatabaseException, MailingListException;
    void removeEntry(Company company, Offer offer) throws DatabaseException, MailingListException;
    List<String> getEmails(Offer offer) throws DatabaseException, MailingListException;
    List<Offer> getOffers(Company company) throws DatabaseException, MailingListException, OfferException;
    void removeAllEntriesFrom(Company company) throws DatabaseException, MailingListException;
    boolean isFollowingThisOffer(Company company, Offer offer) throws DatabaseException, MailingListException;
}
