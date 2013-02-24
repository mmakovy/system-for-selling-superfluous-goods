/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.thesis;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  FOR TESTING PURPOSES
 * 
 * @author Matus Makovy
 */
public class demo {
    public static void main(String[] args) throws DatabaseException{
        CompanyManager manager = new CompanyManagerImpl();
        OfferManager offerMng = new OfferManagerImpl();
        
        /**PRIDAVANIE NOVEHO ZAZNAMU DO TABULKY COMPANY
        Company newCompany = new Company();
        newCompany.setName("nova1");
        newCompany.setEmail("nova@nov1a.cz");
        newCompany.setPhoneNumber("123456789");
        Company added = manager.addCompany(newCompany,"user","pass");
    System.out.println("Pridana: " + added.toString());*/
        
        /**VYPIS COMPANIES
        List<Company> companies = new ArrayList<Company>();
        try {
            companies = manager.getAllCompanies();
        } catch (DatabaseException ex) {
            Logger.getLogger(demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Vsetky companies");
        for (int i = 0; i<companies.size(); i++){
        System.out.println(companies.get(i).toString());
        } 
        System.out.println();   */
        
       /** Long abc = Long.parseLong("26");
        Company returned = manager.getCompany(abc);
        System.out.println(manager.toString());*/
        
         /**VYMAZAVANIE COMPANY
        System.out.println("Ma byt vymazana " + companies.get(0).toString());
        manager.removeCompany(companies.get(0));
        
        companies = manager.getAllCompanies();
        
        for (int i = 0; i<companies.size(); i++){
        System.out.println(companies.get(i).toString());
        }*/
        
        /** UPDATE COMPANY
        Company update = companies.get(1);
        update.setName("updateDDD");
        update.setEmail("updateeeeeeed@dateeed.com");
        manager.updateCompany(update); */
        
        /** PRIDAVANIE PONUKY 
        Offer offer = new Offer();
        offer.setName("offer");
        offer.setQuantity(55);
        offer.setDescription("descriptionnnn");
        offer.setPrice(new BigDecimal(52.58));
        offerMng.addOffer(companies.get(3), offer);*/
        
        /**VYPIS OFFERS
        List<Offer> offers = new ArrayList<Offer>();
        offers = offerMng.getAllOffers();
        
        System.out.println("Vsetky offers");
        for (int i = 0; i<offers.size(); i++){
            System.out.println(offers.get(i).toString());
        } 
        System.out.println();*/
        
        /** UPDATE Offer
        Offer update = offers.get(1);
        update.setName("updateDDDoffer");
        update.setDescription("updateeoffereed@dateeedof.com");
        offerMng.updateOffer(update); */
        
        /** VYPIS OFFERS PODLA COMPANY
        offers = offerMng.getOffersByCompany(companies.get(0));
        
        System.out.println("Offers company id " + companies.get(0).getId());
        for (int i = 0; i<offers.size(); i++){
            System.out.println(offers.get(i).toString());       
        } 
        System.out.println();*/
        
        /** REMOVE OFFER 
        offerMng.removeOffer(offers.get(0));*/
    }

}
