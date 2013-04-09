/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.thesis;

import java.util.List;

/**
 *
 * @author matus
 */
public interface MailingListManager {
    
    void addEmail(String email, Long id) throws DatabaseException, MailingListException;
    void removeEmail(String email, Long id) throws DatabaseException, MailingListException;
    List<String> getEmails(Long id) throws DatabaseException, MailingListException;
}