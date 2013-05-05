package cz.muni.fi.thesis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;

/**
 * Class implementing MailingListManager
 * 
 * @author Matus Makovy
 */
public class MailingListManagerImpl implements MailingListManager {

    final static org.slf4j.Logger log = LoggerFactory.getLogger(MailingListManagerImpl.class);

    @Override
    public void addEntry(Company company, Offer offer) throws DatabaseException, MailingListException {

        if (company == null) {
            throw new IllegalArgumentException("company");
        }

        if (offer == null) {
            throw new IllegalArgumentException("offer");
        }

        Long companyId = company.getId();
        Long offerId = offer.getId();

        if (companyId == null) {
            throw new IllegalArgumentException("company id");
        }

        if (offerId == null) {
            throw new IllegalArgumentException("offer id");
        }

        Connection con = DatabaseConnection.getConnection();

        if (con == null) {
            throw new DatabaseException("Connection to database wasn't established");
        } else {
            try {
                PreparedStatement st = con.prepareStatement("INSERT INTO mailing_list(company_id,offer_id) VALUES (?,?);");
                st.setLong(1, companyId);
                st.setLong(2, offerId);

                if (st.executeUpdate() == 0) {
                    log.error("Error when adding entry to mailing list. Execute update returned 0");
                    throw new MailingListException("Error when adding entry to mailing list. Execute update returned 0");
                }

            } catch (SQLException ex) {
                log.error(ex.getMessage());
                throw new MailingListException(ex.getMessage());
            } finally {
                DatabaseConnection.closeConnection(con);
            }
        }

    }

    @Override
    public void removeEntry(Company company, Offer offer) throws DatabaseException, MailingListException {

        if (company == null) {
            throw new IllegalArgumentException("company");
        }

        if (offer == null) {
            throw new IllegalArgumentException("offer");
        }

        Long companyId = company.getId();
        Long offerId = offer.getId();

        if (companyId == null) {
            throw new IllegalArgumentException("company id");
        }

        if (offerId == null) {
            throw new IllegalArgumentException("offer id");
        }

        Connection con = DatabaseConnection.getConnection();

        if (con == null) {
            throw new DatabaseException("Connection to database wasn't established");
        } else {
            try {
                PreparedStatement st = con.prepareStatement("DELETE FROM mailing_list WHERE company_id=? AND offer_id=?;");
                st.setLong(1, companyId);
                st.setLong(2, offerId);

                if (st.executeUpdate() == 0) {
                    log.error("Error when deleting entry from mailing list. Execute update returned 0.");
                    throw new MailingListException("Error when deleting entry from mailing list. Execute update returned 0.");
                }

            } catch (SQLException ex) {
                log.error(ex.getMessage());
                throw new MailingListException(ex.getMessage());
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

        Long offerId = offer.getId();

        if (offerId == null) {
            throw new IllegalArgumentException("offer id");
        }

        Connection con = DatabaseConnection.getConnection();

        if (con == null) {
            throw new DatabaseException("Connection to database wasn't established");
        } else {
            try {
                PreparedStatement st = con.prepareStatement("SELECT email FROM mailing_list JOIN company ON mailing_list.company_id=company.id WHERE offer_id=?");
                st.setLong(1, offerId);

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
    public List<Offer> getOffers(Company company) throws DatabaseException {
        if (company == null) {
            throw new IllegalArgumentException("company");
        }

        Long companyId = company.getId();

        if (companyId == null) {
            throw new IllegalArgumentException("company id");
        }

        Connection con = DatabaseConnection.getConnection();
        List<Offer> offers = new ArrayList<Offer>();

        if (con == null) {
            throw new DatabaseException("Connection to database wasn't established");
        } else {
            try {
                PreparedStatement st = con.prepareStatement("SELECT offer.company_id,name,description,id,price,quantity,category,purchase_date,minimal_buy_quantity,photo_url FROM mailing_list JOIN offer ON mailing_list.offer_id=offer.id WHERE mailing_list.company_id=?");
                st.setLong(1, companyId);

                ResultSet offerDB = st.executeQuery();
                while (offerDB.next()) {
                    Offer offer = new Offer();
                    offer.setCompanyId(offerDB.getLong("company_id"));
                    offer.setName(offerDB.getString("name"));
                    offer.setDescription(offerDB.getString("description"));
                    offer.setId(offerDB.getLong("id"));
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
    public void removeAllEntriesFrom(Company company) throws DatabaseException, MailingListException {
        if (company == null) {
            throw new IllegalArgumentException("company");
        }

        Long companyId = company.getId();

        if (companyId == null) {
            throw new IllegalArgumentException("company id");
        }

        Connection con = DatabaseConnection.getConnection();

        if (con == null) {
            throw new DatabaseException("Connection to database wasn't established");
        } else {
            try {
                PreparedStatement st = con.prepareStatement("DELETE FROM mailing_list WHERE company_id=?;");
                st.setLong(1, companyId);

                if (st.executeUpdate() == 0) {
                    log.error("Error when deleting entries from mailing list. Execute update returned 0.");
                    throw new MailingListException("Error when deleting entries from mailing list. Execute update returned 0.");
                }

            } catch (SQLException ex) {
                log.error(ex.getMessage());
                throw new MailingListException(ex.getMessage());
            } finally {
                DatabaseConnection.closeConnection(con);
            }
        }
    }
}
