package cz.muni.fi.thesis;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for manipulationg with objects of class Company
 *
 * @author Matus Makovy
 */
public class CompanyManagerImpl implements CompanyManager {

    final static Logger log = LoggerFactory.getLogger(CompanyManagerImpl.class);

    @Override
    public Company addCompany(Company company, String username, String password) throws DatabaseException {

        UserManager usrManager = new UserManagerImpl();

        Blob blobHash = usrManager.hashPassword(password);

        if (company == null) {
            throw new IllegalArgumentException("company");
        }

        Connection con = DatabaseConnection.getConnection();

        if (con == null) {
            throw new DatabaseException("Conection to database wasnt established");
        } else {
            try {
                con.setAutoCommit(false);

                PreparedStatement st = con.prepareStatement("INSERT INTO company(name,email,phone_number,street,city,country,psc,other) VALUES (?,?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
                st.setString(1, company.getName());
                st.setString(2, company.getEmail());
                st.setString(3, company.getPhoneNumber());
                st.setString(4, company.getStreet());
                st.setString(5, company.getCity());
                st.setString(6, company.getCountry());
                st.setString(7, company.getPsc());
                st.setString(8, company.getOther());


                if (st.executeUpdate() == 0) {
                    DatabaseConnection.doRollback(con);
                    log.error("Error while adding Company to database");
                    return null;
                }

                ResultSet keys = st.getGeneratedKeys();
                Company returnCompany = company;
                if (keys.next()) {
                    returnCompany.setId(keys.getLong(1));
                }


                st = con.prepareStatement("INSERT INTO users(username, userId,hash_pwd,hash_ver) VALUES (?,?,?,?);");

                st.setString(1, username);
                st.setLong(2, returnCompany.getId());
                st.setBlob(3, blobHash);
                st.setString(4, UUID.randomUUID().toString());


                if (st.executeUpdate() == 0) {
                    DatabaseConnection.doRollback(con);
                    log.error("Error while adding Company-USER to database");
                    return null;
                }

                con.commit();

                return returnCompany;

            } catch (SQLException ex) {
                DatabaseConnection.doRollback(con);
                log.error(ex.getMessage());
            } finally {
                DatabaseConnection.closeConnection(con);
            }
        }
        return null;
    }

    @Override
    public List<Company> getAllCompanies() throws DatabaseException {

        Connection con = DatabaseConnection.getConnection();
        List<Company> companies = new ArrayList<Company>();

        if (con == null) {
            throw new DatabaseException("Conection to database wasnt established");
        } else {
            try {
                PreparedStatement st = con.prepareStatement("SELECT id_company,name,email,phone_number,street,city,country,psc,other FROM company;");
                ResultSet companyDB = st.executeQuery();
                while (companyDB.next()) {
                    Company company = new Company();
                    company.setId(companyDB.getLong("id_company"));
                    company.setName(companyDB.getString("name"));
                    company.setEmail(companyDB.getString("email"));
                    company.setPhoneNumber(companyDB.getString("phone_number"));
                    company.setCity(companyDB.getString("city"));
                    company.setCountry(companyDB.getString("country"));
                    company.setPsc(companyDB.getString("psc"));
                    company.setStreet(companyDB.getString("street"));
                    company.setOther(companyDB.getString("other"));
                    companies.add(company);
                }
                return companies;
            } catch (SQLException ex) {
                log.error(ex.getMessage());
            } finally {
                DatabaseConnection.closeConnection(con);
            }
        }

        return null;
    }

    @Override
    public void removeCompany(Company company) throws DatabaseException, CompanyException, OfferException {
        if (company == null) {
            throw new IllegalArgumentException("company");
        }
        if (company.getId() == null) {
            throw new NullPointerException("company - ID");
        }

        Connection con = DatabaseConnection.getConnection();

        try {

            PreparedStatement st1 = con.prepareStatement("DELETE FROM company WHERE id_company=?;");
            st1.setLong(1, company.getId());
            if (st1.executeUpdate() == 0) {
                DatabaseConnection.doRollback(con);
                log.error("Contact wasnt removed");
                throw new CompanyException("Contact wasnt removed");
            }


        } catch (SQLException ex) {
            DatabaseConnection.doRollback(con);
            log.error(ex.getMessage());
            throw new CompanyException(ex.getMessage());
        } finally {
            DatabaseConnection.closeConnection(con);
        }
    }

    @Override
    public void updateCompany(Company company) throws DatabaseException, CompanyException {
        if (company == null) {
            throw new IllegalArgumentException("company is null");
        }

        if (company.getId() == null) {
            throw new IllegalArgumentException("company id is null");
        }

        Connection con = DatabaseConnection.getConnection();

        if (con == null) {
            throw new DatabaseException("Conection to database wasnt established");
        } else {
            try {
                PreparedStatement st = con.prepareStatement("UPDATE company SET name=?, email=?, phone_number=?, street=?, city=?, country=?, psc=?, other=? WHERE id_company=?;");
                st.setString(1, company.getName());
                st.setString(2, company.getEmail());
                st.setString(3, company.getPhoneNumber());
                st.setString(4, company.getStreet());
                st.setString(5, company.getCity());
                st.setString(6, company.getCountry());
                st.setString(7, company.getPsc());
                st.setString(8, company.getOther());
                st.setLong(9, company.getId());

                if (st.executeUpdate() == 0) {
                    log.error("Update wasnt done");
                    throw new CompanyException("Update wasnt done");
                }

            } catch (SQLException ex) {
                log.error(ex.getMessage());
            } finally {
                DatabaseConnection.closeConnection(con);
            }

        }

    }

    @Override
    public Company getCompanyById(Long id) throws DatabaseException {
        if (id == null) {
            throw new IllegalArgumentException("id");
        }

        Connection con = DatabaseConnection.getConnection();

        if (con == null) {
            throw new DatabaseException("Conection to database wasnt established");
        } else {
            try {
                PreparedStatement st = con.prepareStatement("SELECT id_company,name,email,phone_number,street,city,country,psc,other FROM company WHERE id_company=?");
                st.setLong(1, id);
                ResultSet companyDB = st.executeQuery();
                Company company = null;
                while (companyDB.next()) {
                    company = new Company();
                    company.setId(companyDB.getLong("id_company"));
                    company.setName(companyDB.getString("name"));
                    company.setEmail(companyDB.getString("email"));
                    company.setPhoneNumber(companyDB.getString("phone_number"));
                    company.setCity(companyDB.getString("city"));
                    company.setCountry(companyDB.getString("country"));
                    company.setPsc(companyDB.getString("psc"));
                    company.setStreet(companyDB.getString("street"));
                    company.setOther(companyDB.getString("other"));
                }
                return company;

            } catch (SQLException ex) {
                log.error(ex.getMessage());
            } finally {
                DatabaseConnection.closeConnection(con);
            }
        }
        return null;

    }

    @Override
    public boolean isEmailInDatabase(String email) throws DatabaseException {
        if (email == null) {
            throw new IllegalArgumentException("email");
        }

        Connection con = DatabaseConnection.getConnection();

        if (con == null) {
            throw new DatabaseException("Conection to database wasnt established");
        } else {
            try {
                PreparedStatement st = con.prepareStatement("SELECT email FROM company WHERE email=?;");
                st.setString(1, email);

                ResultSet usersDB = st.executeQuery();

                if (usersDB.next()) {
                    return true;
                }

                return false;

            } catch (SQLException ex) {
                log.error(ex.getMessage());
            } finally {
                DatabaseConnection.closeConnection(con);
            }
            return false;
        }
    }

    @Override
    public Company getCompanyByEmail(String email) throws DatabaseException {
        if (email == null) {
            throw new IllegalArgumentException("email");
        }

        Connection con = DatabaseConnection.getConnection();

        if (con == null) {
            throw new DatabaseException("Conection to database wasnt established");
        } else {
            try {
                PreparedStatement st = con.prepareStatement("SELECT id_company,name,email,phone_number,street,city,country,psc,other FROM company WHERE email=?");
                st.setString(1, email);
                ResultSet companyDB = st.executeQuery();
                Company company = null;
                while (companyDB.next()) {
                    company = new Company();
                    company.setId(companyDB.getLong("id_company"));
                    company.setName(companyDB.getString("name"));
                    company.setEmail(companyDB.getString("email"));
                    company.setPhoneNumber(companyDB.getString("phone_number"));
                    company.setCity(companyDB.getString("city"));
                    company.setCountry(companyDB.getString("country"));
                    company.setPsc(companyDB.getString("psc"));
                    company.setStreet(companyDB.getString("street"));
                    company.setOther(companyDB.getString("other"));
                }
                return company;

            } catch (SQLException ex) {
                log.error(ex.getMessage());
            } finally {
                DatabaseConnection.closeConnection(con);
            }
        }
        return null;
    }
}
