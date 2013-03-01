package cz.muni.fi.thesis;

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
                PreparedStatement st = con.prepareStatement("INSERT INTO offer (name,description,quantity,id_company,price,category,purchase_date,minimal_buy_quantity) VALUES (?,?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
                st.setString(1, offer.getName());
                st.setString(2, offer.getDescription());
                st.setInt(3, offer.getQuantity());
                st.setLong(4, company.getId());
                st.setBigDecimal(5, offer.getPrice());
                st.setString(6,offer.getCategory().toString());
                st.setDate(7, offer.getPurchaseDate());
                st.setInt(8,offer.getMinimalBuyQuantity());
                
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
                    log.error("Offer wasnt deleted from database");
                    throw new OfferException("Offer wasnt deleted from database");
                }
            } catch (SQLException ex) {
                log.error(ex.getMessage());
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
                PreparedStatement st = con.prepareStatement("UPDATE offer SET name=?, description=?, id_company=?, price=?, quantity=?, category=?, purchase_date=?, minimal_buy_quantity=? WHERE id_offer=?;");
                st.setString(1, offer.getName());
                st.setString(2, offer.getDescription());
                st.setLong(3, offer.getCompany_id());
                st.setBigDecimal(4, offer.getPrice());
                st.setInt(5, offer.getQuantity());
                st.setString(6,offer.getCategory().toString());
                st.setDate(7, offer.getPurchaseDate());
                st.setInt(8,offer.getMinimalBuyQuantity());
                st.setLong(9, offer.getId());
                
                
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
                PreparedStatement st = con.prepareStatement("SELECT id_company,name,description,id_offer,price,quantity,category,purchase_date,minimal_buy_quantity FROM offer;");
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
                PreparedStatement st = con.prepareStatement("SELECT id_company,name,description,id_offer,price,quantity,category,purchase_date,minimal_buy_quantity FROM offer WHERE id_company=?;");
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
                PreparedStatement st = con.prepareStatement("SELECT id_company,name,description,id_offer,price,quantity,category,purchase_date,minimal_buy_quantity FROM offer WHERE id_offer=?");
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
                PreparedStatement st = con.prepareStatement("SELECT id_company,name,description,id_offer,price,quantity,purchase_date,minimal_buy_quantity,category FROM offer WHERE name LIKE CONCAT('%',?,'%')");
                st.setString(1, expression);
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
                    offers.add(offer);
                }
                
                st = con.prepareStatement("SELECT id_company,name,description,id_offer,price,quantity FROM offer WHERE description LIKE CONCAT('%',?,'%')");
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
}
