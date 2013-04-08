package cz.muni.fi.thesis;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Matus Makovy
 */
public interface OfferManager {

    Offer getOffer(Long id) throws DatabaseException, OfferException;

    Offer addOffer(Company company, Offer offer) throws DatabaseException, OfferException;

    void removeOffer(Offer offer) throws DatabaseException, OfferException;

    void updateOffer(Offer offer) throws DatabaseException, OfferException;

    List<Offer> getAllOffers() throws DatabaseException, OfferException;

    List<Offer> getOffersByCompany(Company company) throws DatabaseException, OfferException;

    List<Offer> findOffer(String expression) throws DatabaseException, OfferException;

    List<Offer> getOffersFromCategory(Category category) throws DatabaseException, OfferException;

    List<Offer> filterQuantity(List<Offer> offers, int min, int max);

    List<Offer> filterQuantityToBuy(List<Offer> offers, int min, int max);

    List<Offer> filterPrice(List<Offer> offers, BigDecimal min, BigDecimal max);

    List<Offer> filterCategory(List<Offer> offers, Category category);
}
