package cz.muni.fi.thesis;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;

/**
 * Class for manipulating with objects of class Offer
 *
 * @author Matus Makovy
 */
public class OfferManagerImpl implements OfferManager {

    final static org.slf4j.Logger log = LoggerFactory.getLogger(CompanyManagerImpl.class);

    @Override
    public Offer addOffer(Company company, Offer offer) throws DatabaseException {

        if (company == null) {
            throw new IllegalArgumentException("comapny");
        }

        if (company.getId() == null) {
            throw new IllegalArgumentException("comapny ID");
        }

        if (offer == null) {
            throw new IllegalArgumentException("offer");
        }

        Connection con = DatabaseConnection.getConnection();

        if (con == null) {
            throw new DatabaseException("Connection wasnt established");
        } else {
            try {
                PreparedStatement st = con.prepareStatement("INSERT INTO offer (name,description,quantity,id_company,price,category,purchase_date,minimal_buy_quantity,photo_url) VALUES (?,?,?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
                st.setString(1, offer.getName());
                st.setString(2, offer.getDescription());
                st.setInt(3, offer.getQuantity());
                st.setLong(4, company.getId());
                st.setBigDecimal(5, offer.getPrice());
                st.setString(6, offer.getCategory().toString());
                st.setDate(7, offer.getPurchaseDate());
                st.setInt(8, offer.getMinimalBuyQuantity());
                st.setString(9, offer.getPhotoUrl());

                if (st.executeUpdate() == 0) {
                    log.error("Error when adding offer to database");
                    return null;
                }

                ResultSet keys = st.getGeneratedKeys();
                Offer returnOffer = offer;
                if (keys.next()) {
                    returnOffer.setId(keys.getLong(1));
                }

                return returnOffer;
            } catch (SQLException ex) {
                log.error(ex.getMessage());
            } finally {
                DatabaseConnection.closeConnection(con);
            }
        }
        return null;
    }

    @Override
    public void removeOffer(Offer offer) throws DatabaseException, OfferException {
        if (offer == null) {
            throw new IllegalArgumentException("offer");
        }
        if (offer.getId() == null) {
            throw new NullPointerException("offer.getId()");
        }

        Connection con = DatabaseConnection.getConnection();

        if (con == null) {
            throw new DatabaseException("Connection wasnt established");
        } else {
            try {

                PreparedStatement st = con.prepareStatement("DELETE FROM offer WHERE id_offer=?;");
                st.setLong(1, offer.getId());

                if (st.executeUpdate() == 0) {
                    DatabaseConnection.doRollback(con);
                    log.error("Offer wasnt deleted from database - offers");
                    throw new OfferException("Offer wasnt deleted from database - offers");
                }
                
                con.commit();

            } catch (Exception ex) {
                DatabaseConnection.doRollback(con);
                log.error(ex.getMessage());
                throw new OfferException(ex.getMessage());
            } finally {
                DatabaseConnection.closeConnection(con);
            }
        }
    }

    @Override
    public void updateOffer(Offer offer) throws DatabaseException, OfferException {

        if (offer == null) {
            throw new IllegalArgumentException("offer");
        }
        if (offer.getId() == null) {
            throw new IllegalArgumentException("offer-id");
        }

        Connection con = DatabaseConnection.getConnection();

        if (con == null) {
            throw new DatabaseException("Connection wasnt established");
        } else {
            try {
                PreparedStatement st = con.prepareStatement("UPDATE offer SET name=?, description=?, id_company=?, price=?, quantity=?, category=?, purchase_date=?, minimal_buy_quantity=?, photo_url=? WHERE id_offer=?;");
                st.setString(1, offer.getName());
                st.setString(2, offer.getDescription());
                st.setLong(3, offer.getCompany_id());
                st.setBigDecimal(4, offer.getPrice());
                st.setInt(5, offer.getQuantity());
                st.setString(6, offer.getCategory().toString());
                st.setDate(7, offer.getPurchaseDate());
                st.setInt(8, offer.getMinimalBuyQuantity());
                st.setLong(10, offer.getId());
                st.setString(9, offer.getPhotoUrl());


                if (st.executeUpdate() == 0) {
                    log.error("Update of offer wasnt completed");
                    throw new OfferException("Update wasnt completed");
                }
            } catch (SQLException ex) {
                log.error(ex.getMessage());
            } finally {
                DatabaseConnection.closeConnection(con);
            }
        }
    }

    @Override
    public List<Offer> getAllOffers() throws DatabaseException {

        Connection con = DatabaseConnection.getConnection();
        List<Offer> offers = new ArrayList<Offer>();

        if (con == null) {
            throw new DatabaseException("Connection to database wasnt established");
        } else {
            try {
                PreparedStatement st = con.prepareStatement("SELECT id_company,name,description,id_offer,price,quantity,category,purchase_date,minimal_buy_quantity,photo_url FROM offer;");
                ResultSet offerDB = st.executeQuery();
                while (offerDB.next()) {
                    Offer offer = new Offer();
                    offer.setCompany_id(offerDB.getLong("id_company"));
                    offer.setName(offerDB.getString("name"));
                    offer.setDescription(offerDB.getString("description"));
                    offer.setId(offerDB.getLong("id_offer"));
                    offer.setPrice(offerDB.getBigDecimal("price"));
                    offer.setQuantity(offerDB.getInt("quantity"));
                    offer.setCategory(Category.valueOf(offerDB.getString("category")));
                    offer.setPurchaseDate(offerDB.getDate("purchase_date"));
                    offer.setMinimalBuyQuantity(offerDB.getInt("minimal_buy_quantity"));
                    offer.setPhotoUrl(offerDB.getString("photo_url"));
                    offers.add(offer);
                }
                return offers;
            } catch (SQLException ex) {
                log.error(ex.getMessage());
            } finally {
                DatabaseConnection.closeConnection(con);
            }
        }
        return null;
    }

    @Override
    public List<Offer> getOffersByCompany(Company company) throws DatabaseException {
        if (company == null) {
            throw new IllegalArgumentException("company");
        }
        if (company.getId() == null) {
            throw new IllegalArgumentException("companyID");
        }
        Connection con = DatabaseConnection.getConnection();
        List<Offer> offers = new ArrayList<Offer>();

        if (con == null) {
            throw new DatabaseException("Connection wasnt established");
        } else {
            try {
                PreparedStatement st = con.prepareStatement("SELECT id_company,name,description,id_offer,price,quantity,category,purchase_date,minimal_buy_quantity,photo_url FROM offer WHERE id_company=?;");
                st.setLong(1, company.getId());
                ResultSet offerDB = st.executeQuery();
                while (offerDB.next()) {
                    Offer offer = new Offer();
                    offer.setCompany_id(offerDB.getLong("id_company"));
                    offer.setName(offerDB.getString("name"));
                    offer.setDescription(offerDB.getString("description"));
                    offer.setId(offerDB.getLong("id_offer"));
                    offer.setPrice(offerDB.getBigDecimal("price"));
                    offer.setQuantity(offerDB.getInt("quantity"));
                    offer.setCategory(Category.valueOf(offerDB.getString("category")));
                    offer.setPurchaseDate(offerDB.getDate("purchase_date"));
                    offer.setMinimalBuyQuantity(offerDB.getInt("minimal_buy_quantity"));
                    offer.setPhotoUrl(offerDB.getString("photo_url"));
                    offers.add(offer);
                }
                return offers;
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            } finally {
                DatabaseConnection.closeConnection(con);
            }
        }
        return null;
    }

    public Offer getOffer(Long id) throws DatabaseException {

        if (id == null) {
            throw new IllegalArgumentException("id");
        }

        Connection con = DatabaseConnection.getConnection();

        if (con == null) {
            throw new DatabaseException("Conection to database wasnt established");
        } else {
            try {
                PreparedStatement st = con.prepareStatement("SELECT id_company,name,description,id_offer,price,quantity,category,purchase_date,minimal_buy_quantity,photo_url FROM offer WHERE id_offer=?");
                st.setLong(1, id);
                ResultSet offerDB = st.executeQuery();
                Offer offer = null;
                while (offerDB.next()) {
                    offer = new Offer();
                    offer.setCompany_id(offerDB.getLong("id_company"));
                    offer.setName(offerDB.getString("name"));
                    offer.setDescription(offerDB.getString("description"));
                    offer.setId(offerDB.getLong("id_offer"));
                    offer.setPrice(offerDB.getBigDecimal("price"));
                    offer.setQuantity(offerDB.getInt("quantity"));
                    offer.setCategory(Category.valueOf(offerDB.getString("category")));
                    offer.setPurchaseDate(offerDB.getDate("purchase_date"));
                    offer.setMinimalBuyQuantity(offerDB.getInt("minimal_buy_quantity"));
                    offer.setPhotoUrl(offerDB.getString("photo_url"));
                }
                return offer;
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            } finally {
                DatabaseConnection.closeConnection(con);
            }
        }
        return null;

    }

    @Override
    public List<Offer> findOffer(String expression) throws DatabaseException {
        if (expression == null) {
            throw new IllegalArgumentException("expression");
        }

        List<Offer> offers = new ArrayList<Offer>();
        Connection con = DatabaseConnection.getConnection();

        if (con == null) {
            throw new DatabaseException("Connection to database wasnt established");
        } else {
            try {
                PreparedStatement st = con.prepareStatement("SELECT id_company,name,description,id_offer,price,quantity,purchase_date,minimal_buy_quantity,category,photo_url FROM offer WHERE name LIKE CONCAT('%',?,'%')");
                st.setString(1, expression);
                ResultSet offerDB = st.executeQuery();
                Offer offer;

                while (offerDB.next()) {
                    offer = new Offer();
                    offer.setCompany_id(offerDB.getLong("id_company"));
                    offer.setName(offerDB.getString("name"));
                    offer.setDescription(offerDB.getString("description"));
                    offer.setId(offerDB.getLong("id_offer"));
                    offer.setPrice(offerDB.getBigDecimal("price"));
                    offer.setQuantity(offerDB.getInt("quantity"));
                    offer.setCategory(Category.valueOf(offerDB.getString("category")));
                    offer.setPurchaseDate(offerDB.getDate("purchase_date"));
                    offer.setMinimalBuyQuantity(offerDB.getInt("minimal_buy_quantity"));
                    offer.setPhotoUrl(offerDB.getString("photo_url"));
                    offers.add(offer);
                }

                st = con.prepareStatement("SELECT id_company,name,description,id_offer,price,quantity,purchase_date,minimal_buy_quantity,category,photo_url FROM offer WHERE description LIKE CONCAT('%',?,'%')");
                st.setString(1, expression);
                offerDB = st.executeQuery();


                while (offerDB.next()) {
                    offer = new Offer();
                    offer.setCompany_id(offerDB.getLong("id_company"));
                    offer.setName(offerDB.getString("name"));
                    offer.setDescription(offerDB.getString("description"));
                    offer.setId(offerDB.getLong("id_offer"));
                    offer.setPrice(offerDB.getBigDecimal("price"));
                    offer.setQuantity(offerDB.getInt("quantity"));
                    offer.setCategory(Category.valueOf(offerDB.getString("category")));
                    offer.setPurchaseDate(offerDB.getDate("purchase_date"));
                    offer.setMinimalBuyQuantity(offerDB.getInt("minimal_buy_quantity"));
                    offer.setPhotoUrl(offerDB.getString("photo_url"));

                    if (!offers.contains(offer)) {
                        offers.add(offer);
                    }
                }

                return offers;

            } catch (SQLException ex) {
                log.error(ex.getMessage());
            } finally {
                DatabaseConnection.closeConnection(con);
            }
        }
        return null;
    }

    public List<Offer> getOffersFromCategory(Category category) throws DatabaseException {

        if (category == null) {
            throw new IllegalArgumentException("category");
        }

        Connection con = DatabaseConnection.getConnection();
        List<Offer> offers = new ArrayList<Offer>();

        if (con == null) {
            throw new DatabaseException("Connection wasnt established");
        } else {
            try {
                PreparedStatement st = con.prepareStatement("SELECT id_company,name,description,id_offer,price,quantity,category,purchase_date,minimal_buy_quantity,photo_url FROM offer WHERE category=?;");
                st.setString(1, category.toString());
                ResultSet offerDB = st.executeQuery();
                while (offerDB.next()) {
                    Offer offer = new Offer();
                    offer.setCompany_id(offerDB.getLong("id_company"));
                    offer.setName(offerDB.getString("name"));
                    offer.setDescription(offerDB.getString("description"));
                    offer.setId(offerDB.getLong("id_offer"));
                    offer.setPrice(offerDB.getBigDecimal("price"));
                    offer.setQuantity(offerDB.getInt("quantity"));
                    offer.setCategory(Category.valueOf(offerDB.getString("category")));
                    offer.setPurchaseDate(offerDB.getDate("purchase_date"));
                    offer.setMinimalBuyQuantity(offerDB.getInt("minimal_buy_quantity"));
                    offer.setPhotoUrl(offerDB.getString("photo_url"));
                    offers.add(offer);
                }
                return offers;
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            } finally {
                DatabaseConnection.closeConnection(con);
            }
        }
        return null;

    }

    @Override
    public List<Offer> filterQuantity(List<Offer> offers, int min, int max) {

        List<Offer> returnOffers = new ArrayList<Offer>();

        if (offers == null) {
            throw new IllegalArgumentException("offers");
        }


        for (Offer offer : offers) {
            if (offer.getQuantity() <= max && offer.getQuantity() >= min) {
                returnOffers.add(offer);
            }
        }

        return returnOffers;
    }

    @Override
    public List<Offer> filterQuantityToBuy(List<Offer> offers, int min, int max) {

        List<Offer> returnOffers = new ArrayList<Offer>();

        if (offers == null) {
            throw new IllegalArgumentException("offers");
        }

        for (Offer offer : offers) {
            if (offer.getMinimalBuyQuantity() <= max && offer.getMinimalBuyQuantity() >= min) {
                returnOffers.add(offer);
            }
        }

        return returnOffers;
    }

    @Override
    public List<Offer> filterPrice(List<Offer> offers, BigDecimal min, BigDecimal max) {

        List<Offer> returnOffers = new ArrayList<Offer>();

        if (offers == null) {
            throw new IllegalArgumentException("offers");
        }

        for (Offer offer : offers) {
            BigDecimal price = offer.getPrice();
            if ((price.compareTo(max) == -1 || price.compareTo(max) == 0) && (price.compareTo(min) == 1 || price.compareTo(min) == 0)) {
                returnOffers.add(offer);
            }
        }

        return returnOffers;
    }

    @Override
    public List<Offer> filterCategory(List<Offer> offers, Category category) {

        List<Offer> returnOffers = new ArrayList<Offer>();

        if (offers == null) {
            throw new IllegalArgumentException("offers");
        }

        for (Offer offer : offers) {
            if (offer.getCategory().equals(category)) {
                returnOffers.add(offer);
            }
        }

        return returnOffers;
    }
}
