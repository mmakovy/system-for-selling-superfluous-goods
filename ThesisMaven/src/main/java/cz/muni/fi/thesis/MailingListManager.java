package cz.muni.fi.thesis;

import java.util.List;

/**
 * Interface for manipulation with mailing list entries.
 * 
 * @author Matus Makovy
 */
public interface MailingListManager {
    
    /**
     * Adds entry to database
     * 
     * @param company that wants to follow particular offer
     * @param offer to be followed
     * @throws DatabaseException when connection to database wasn't established
     * @throws MailingListException when INSERT wasn't succesful
     */
    void addEntry(Company company, Offer offer) throws DatabaseException, MailingListException;
    
    /**
     *  Removes entry form database 
     * 
     * @param company that follows offer
     * @param offer that is followed
     * @throws DatabaseException DatabaseException when connection to database wasn't established
     * @throws MailingListException when DELETE wasn't succesful
     */
    void removeEntry(Company company, Offer offer) throws DatabaseException, MailingListException;
    
    /**
     * Returns list of e-mail address that the system should send e-mail to 
     * on change or remove of offer.
     * 
     * @param offer represents changed or removed offer
     * @return List of e-mail addresses
     * @throws DatabaseException when connection to database wasn't established
     */
    List<String> getEmails(Offer offer) throws DatabaseException;
    
    /**
     * Returns list of offers that are followed by certain company
     * 
     * @param company specifies company, that is asking about it's offers
     * @return List of offers
     * @throws DatabaseException when connection to database wasn't established
     */
    List<Offer> getOffers(Company company) throws DatabaseException;
    
    /**
     * Removes all entries from certain company in database
     * 
     * @param company specifies company, that wants to remove it's subscriptions
     * @throws DatabaseException when connection to database wasn't established
     * @throws MailingListException in case of SQL error
     */
    void removeAllEntriesFrom(Company company) throws DatabaseException, MailingListException;
}
