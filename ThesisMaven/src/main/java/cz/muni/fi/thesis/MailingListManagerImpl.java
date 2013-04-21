package cz.muni.fi.thesis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matus Makovy
 */
public class MailingListManagerImpl implements MailingListManager {

    final static org.slf4j.Logger log = LoggerFactory.getLogger(MailingListManagerImpl.class);

    @Override
    public void addEntry(Company company, Offer offer) throws DatabaseException {

        if (company == null) {
            throw new IllegalArgumentException("company");
        }

        if (offer == null) {
            throw new IllegalArgumentException("offer");
        }
        
        String email = company.getEmail();
        Long id = offer.getId();
        
        if (email == null) {
            throw new IllegalArgumentException("company email");
        }

        if (id == null) {
            throw new IllegalArgumentException("offer id");
        }
        
        

        Connection con = DatabaseConnection.getConnection();

        if (con == null) {
            throw new DatabaseException("Connection to database wasnt established");
        } else {
            try {
                PreparedStatement st = con.prepareStatement("INSERT INTO mailing_list(email,id_offer) VALUES (?,?);");
                st.setString(1, email);
                st.setLong(2, id);

                if (st.executeUpdate() == 0) {
                    log.error("Error when adding email to mailing list - DB");
                }

            } catch (SQLException ex) {
                log.error(ex.getMessage());
            } finally {
                DatabaseConnection.closeConnection(con);
            }
        }

    }

    @Override
    public void removeEntry(Company company, Offer offer) throws DatabaseException {
        
        if (company == null) {
            throw new IllegalArgumentException("company");
        }

        if (offer == null) {
            throw new IllegalArgumentException("offer");
        }
        
        String email = company.getEmail();
        Long id = offer.getId();
        
        if (email == null) {
            throw new IllegalArgumentException("company email");
        }

        if (id == null) {
            throw new IllegalArgumentException("offer id");
        }

        Connection con = DatabaseConnection.getConnection();

        if (con == null) {
            throw new DatabaseException("Connection to database wasnt established");
        } else {
            try {
                PreparedStatement st = con.prepareStatement("DELETE FROM mailing_list WHERE email=? AND id_offer=?;");
                st.setString(1, email);
                st.setLong(2, id);

                if (st.executeUpdate() == 0) {
                    log.error("Error when deleting email from mailing list - DB");
                }

            } catch (SQLException ex) {
                log.error(ex.getMessage());
            } finally {
                DatabaseConnection.closeConnection(con);
            }
        }


    }

    @Override
    public List<String> getEmails(Offer offer) throws DatabaseException {        

        if (offer == null) {
            throw new IllegalArgumentException("offer");
        }
        
        Long id = offer.getId();
       
        if (id == null) {
            throw new IllegalArgumentException("offer id");
        }

        Connection con = DatabaseConnection.getConnection();

        if (con == null) {
            throw new DatabaseException("Connection to database wasnt established");
        } else {
            try {
                PreparedStatement st = con.prepareStatement("SELECT email FROM mailing_list WHERE id_offer=?");
                st.setLong(1, id);

                ResultSet emailsFromDB = st.executeQuery();
                List<String> emails = new ArrayList<String>();

                while (emailsFromDB.next()) {
                    String email = emailsFromDB.getString("email");
                    emails.add(email);
                }

                return emails;

            } catch (SQLException ex) {
                log.error(ex.getMessage());
            } finally {
                DatabaseConnection.closeConnection(con);
            }
        }
        return null;
    }

    @Override
    public List<Offer> getOffers(Company company) throws DatabaseException, MailingListException, OfferException {
        if (company == null) {
            throw new IllegalArgumentException("company");
        }
      
        String email = company.getEmail();
       
        if (email == null) {
            throw new IllegalArgumentException("company email");
        }

        OfferManager offerManager = new OfferManagerImpl();

        Connection con = DatabaseConnection.getConnection();

        if (con == null) {
            throw new DatabaseException("Connection to database wasnt established");
        } else {
            try {
                PreparedStatement st = con.prepareStatement("SELECT id_offer FROM mailing_list WHERE email=?");
                st.setString(1, email);

                ResultSet idsFromDB = st.executeQuery();
                List<Offer> offers = new ArrayList<Offer>();

                while (idsFromDB.next()) {
                    Long id = idsFromDB.getLong("id_offer");
                    Offer offer = offerManager.getOffer(id);
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
    public void removeAllEntriesFrom(Company company) throws DatabaseException, MailingListException {
        if (company == null) {
            throw new IllegalArgumentException("company");
        }

        String email = company.getEmail();
        
        if (email == null) {
            throw new IllegalArgumentException("company email");
        }

        Connection con = DatabaseConnection.getConnection();

        if (con == null) {
            throw new DatabaseException("Connection to database wasnt established");
        } else {
            try {
                PreparedStatement st = con.prepareStatement("DELETE FROM mailing_list WHERE email=?;");
                st.setString(1, email);

                if (st.executeUpdate() == 0) {
                    log.error("Error when deleting entries from mailing list - DB");
                }

            } catch (SQLException ex) {
                log.error(ex.getMessage());
            } finally {
                DatabaseConnection.closeConnection(con);
            }
        }
    }

    @Override
    public boolean isFollowingThisOffer(Company company, Offer offer) throws DatabaseException, MailingListException {
        if (company == null) {
            throw new IllegalArgumentException("company");
        }

        if (offer == null) {
            throw new IllegalArgumentException("offer");
        }
        
        String email = company.getEmail();
        Long id = offer.getId();
        
        if (email == null) {
            throw new IllegalArgumentException("company email");
        }

        if (id == null) {
            throw new IllegalArgumentException("offer id");
        }

        Connection con = DatabaseConnection.getConnection();

        if (con == null) {
            throw new DatabaseException("Connection to database wasnt established");
        } else {
            try {
                PreparedStatement st = con.prepareStatement("SELECT FROM mailing_list WHERE email=? AND id_offer=?");
                st.setString(1, email);
                st.setLong(2, id);

                ResultSet results = st.executeQuery();
                if (results.next()) {
                    return true;
                } else {
                    return false;
                }

            } catch (SQLException ex) {
                log.error(ex.getMessage());
            } finally {
                DatabaseConnection.closeConnection(con);
            }
        }
        return false;
    }
}
