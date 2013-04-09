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
 * @author matus
 */
public class MailingListManagerImpl implements MailingListManager {

    final static org.slf4j.Logger log = LoggerFactory.getLogger(CompanyManagerImpl.class);

    public void addEmail(String email, Long id) throws DatabaseException {

        if (email == null) {
            throw new IllegalArgumentException("email");
        }

        if (id == null) {
            throw new IllegalArgumentException("id");
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

    public void removeEmail(String email, Long id) throws DatabaseException {
        if (email == null) {
            throw new IllegalArgumentException("email");
        }

        if (id == null) {
            throw new IllegalArgumentException("id");
        }

        Connection con = DatabaseConnection.getConnection();

        if (con == null) {
            throw new DatabaseException("Connection to database wasnt established");
        } else {
            try {
                PreparedStatement st = con.prepareStatement("DELETE FROM mailing_list WHERE email=? AND offer_id=?;");
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

    public List<String> getEmails(Long id) throws DatabaseException {

        if (id == null) {
            throw new IllegalArgumentException("id");
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

}
