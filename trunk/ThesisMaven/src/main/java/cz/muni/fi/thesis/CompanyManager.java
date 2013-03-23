package cz.muni.fi.thesis;

import java.util.List;

/**
 * Interface for manipulating with objects of class Company
 * 
 * @author Matus Makovy
 */
public interface CompanyManager {
    
    /**
     * Inserts new company to database
     * 
     * @param company 
     * @return Company added to database or null if process wasnt succesful.
     * @throws DatabaseException when connection is not established
     */
    Company addCompany(Company company, String username, String password) throws DatabaseException;
    
    /**
     * Removes comapany from database
     * 
     * @param company
     * @throws CompanyException if company wasnt removed
     * @throws DatabaseException if connection wasnt established
     */
    void removeCompany(Company company) throws CompanyException, DatabaseException, OfferException;
    
    /**
     * Updates company in database
     * 
     * @param company
     * @throws CompanyException if company wasnt updated
     * @throws DatabaseException if connection wasnt established
     */
    void updateCompany(Company company) throws CompanyException, DatabaseException;
    
    /**
     *  Lists all companies from database
     * 
     * @return List of companies from database
     * @throws DatabaseException if connection wasnt established
     */
    List<Company> getAllCompanies() throws DatabaseException;
    
    /**
     * Returns Company with specific ID
     * 
     * @param id
     * @return company with specific ID
     * @throws DatabaseException if connection wasnt established
     */
    Company getCompanyById(Long id) throws DatabaseException;
    
    boolean isEmailInDatabase(String email) throws DatabaseException;
    
    Company getCompanyByEmail(String email) throws DatabaseException;
     
}
