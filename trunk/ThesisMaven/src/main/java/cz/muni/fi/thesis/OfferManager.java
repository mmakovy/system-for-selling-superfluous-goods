package cz.muni.fi.thesis;

import java.math.BigDecimal;
import java.util.List;

/**
 *  Interface for manipulation with objects of class Offer
 * 
 * @author Matus Makovy
 */
public interface OfferManager {
    
    /**
     * Returns offer according to supplied id 
     * 
     * @param id
     * @return offer with specific id
     * @throws DatabaseException when connection to database wasn't established
     * @throws OfferException in case of SQL error
     */
    Offer getOffer(Long id) throws DatabaseException, OfferException;

    /**
     * Adds offer to database
     * 
     * @param company specifies company that is adding offer
     * @param offer specifies offer, that needs to be inserted in the database
     * @return inserted offer
     * @throws DatabaseException when connection to database wasn't established
     * @throws OfferException on SQL error
     */
    Offer addOffer(Company company, Offer offer) throws DatabaseException, OfferException;

    /**
     * Removes offer from database 
     * 
     * @param offer specifies offer to be removed from db
     * @throws DatabaseException when connection to database wasn't established
     * @throws OfferException on SQL error
     */
    void removeOffer(Offer offer) throws DatabaseException, OfferException;
    
    /**
     * Updates offer in database
     * 
     * @param offer specifies offer that needs to be updated
     * @throws DatabaseException when connection to database wasn't established
     * @throws OfferException on SQL error
     */
    void updateOffer(Offer offer) throws DatabaseException, OfferException;
    
    /**
     * Returns all offers from system
     * 
     * @return List of offers from database
     * @throws DatabaseException when connection to database wasn't established
     * @throws OfferException on SQL error
     */
    List<Offer> getAllOffers() throws DatabaseException, OfferException;

    /**
     * Returns offers that are offered by specified company
     * 
     * @param company specifies company that is asking about it's offers
     * @return List of offers from specified company
     * @throws DatabaseException when connection to database wasn't established
     * @throws OfferException on SQL error
     */
    List<Offer> getOffersByCompany(Company company) throws DatabaseException, OfferException;

    /**
     * Finds offer in the database. Method is searching in name and description.
     * 
     * @param expression specifies search expression
     * @return List of offers, that meet expression requirements
     * @throws DatabaseException when connection to database wasn't established
     * @throws OfferException on SQL error
     */
    List<Offer> findOffer(String expression) throws DatabaseException, OfferException;

    /**
     * Returns offers from selected category
     * 
     * @param category specifies category
     * @return List of offers from category
     * @throws DatabaseException when connection to database wasn't established
     * @throws OfferException on SQL error
     */
    List<Offer> getOffersFromCategory(Category category) throws DatabaseException, OfferException;

    /**
     * Filters supplied list of offers according to submitted criteria of quantity.
     * 
     * @param offers specifies list of offers to be filtered
     * @param min specifies minimal quantity
     * @param max specifies maximal quantity
     * @return List of filtered offers
     */
    List<Offer> filterQuantity(List<Offer> offers, int min, int max);

    /**
     * Filters supplied list of offers according to submitted criteria of quantity to buy.
     * 
     * @param offers specifies list of offers to be filtered
     * @param min specifies minimal quantity to buy
     * @param max specifies maximal quantity to buy
     * @return List of filtered offers
     */
    List<Offer> filterQuantityToBuy(List<Offer> offers, int min, int max);

    /**
     * Filters supplied list of offers according to submitted criteria of price.
     * 
     * @param offers specifies list of offers to be filtered
     * @param min specifies minimal price
     * @param max specifies maximal price
     * @return List of filtered offers
     */
    List<Offer> filterPrice(List<Offer> offers, BigDecimal min, BigDecimal max);

    /**
     * Filters supplied list of offers according to submitted criteria of category.
     * 
     * @param offers specifies list of offers to be filtered
     * @param category specifies category 
     * @return List of filtered offers 
     */
    List<Offer> filterCategory(List<Offer> offers, Category category);
}
