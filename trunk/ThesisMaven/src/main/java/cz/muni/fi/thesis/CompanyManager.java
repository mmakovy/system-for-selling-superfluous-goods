package cz.muni.fi.thesis;

import java.util.List;

/**
 * Interface for manipulation with objects of class Company
 * 
 * @author Matus Makovy
 */
public interface CompanyManager {
    
    /**
     * Inserts new company to database
     * 
     * @param company represents new company
     * @param username represents username for authentication purpose
     * @param password represents password for authentication purpose
     * @return Company added to database or null if process wasn't succesful.
     * @throws DatabaseException when connection wasn't established
     */
    Company addCompany(Company company, String username, String password) throws DatabaseException;
    
    /**
     * Removes comapany from database
     * 
     * @param company represents company. that user wants to remove
     * @throws CompanyException if company wasn't removed.
     * @throws DatabaseException if connection wasn't established.
     */
    void removeCompany(Company company) throws CompanyException, DatabaseException;
    
    /**
     * Updates company in database
     * 
     * @param company represents company to be updated
     * @throws CompanyException if company wasnt updated
     * @throws DatabaseException if connection wasn't established
     */
    void updateCompany(Company company) throws CompanyException, DatabaseException;
    
    /**
     *  Lists all companies from database
     * 
     * @return List of companies from database
     * @throws DatabaseException if connection wasn't established
     */
    List<Company> getAllCompanies() throws DatabaseException;
    
    /**
     * Returns Company with specific ID
     * 
     * @param id of company
     * @return company with specific ID
     * @throws DatabaseException if connection wasn't established
     */
    Company getCompanyById(Long id) throws DatabaseException;
    
    /**
     * Determines whether submitted email is in database
     * 
     * @param email represents email that system looks up in database
     * @return true when specified e-mail address is in database, false otherwise
     * @throws DatabaseException if connection wasn't established
     */  
    boolean isEmailInDatabase(String email) throws DatabaseException;
    
    /**
     * Returns company according to the e-mail address
     * 
     * @param email of company
     * @return company with specified e-mail address
     * @throws DatabaseException when connection wasn't established
     */
    Company getCompanyByEmail(String email) throws DatabaseException;
     
}
