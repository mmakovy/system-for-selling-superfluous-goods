package cz.muni.fi.thesis;

import java.util.List;

/**
 *
 * @author Matus Makovy
 */
public interface OfferManager {
    
    Offer getOffer(Long id) throws DatabaseException;
    Offer addOffer(Company company, Offer offer) throws DatabaseException;
    void removeOffer(Offer offer) throws DatabaseException, OfferException;
    void updateOffer(Offer offer) throws DatabaseException, OfferException;
    List<Offer> getAllOffers() throws DatabaseException;
    List<Offer> getOffersByCompany(Company company) throws DatabaseException;
    List<Offer> findOffer(String expression) throws DatabaseException;
    
    
}
