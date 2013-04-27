package cz.muni.fi.thesis;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * Object representing one offer in system
 * 
 * @author Matus Makovy
 */
public class Offer {
    
    private String name;
    private BigDecimal price;
    private int quantity;
    private String description;
    private Date purchaseDate;
    private Category category;
    private int minimalBuyQuantity;
    private Long id;
    private Long companyId;
    private String photoUrl;

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long company_id) {
        this.companyId = company_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMinimalBuyQuantity() {
        return minimalBuyQuantity;
    }

    public void setMinimalBuyQuantity(int minimalBuyQuantity) {
        this.minimalBuyQuantity = minimalBuyQuantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Offer other = (Offer) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.price != other.price && (this.price == null || !this.price.equals(other.price))) {
            return false;
        }
        if (this.quantity != other.quantity) {
            return false;
        }
        if ((this.description == null) ? (other.description != null) : !this.description.equals(other.description)) {
            return false;
        }
        if (this.purchaseDate != other.purchaseDate && (this.purchaseDate == null || !this.purchaseDate.equals(other.purchaseDate))) {
            return false;
        }
        if (this.category != other.category) {
            return false;
        }
        if (this.minimalBuyQuantity != other.minimalBuyQuantity) {
            return false;
        }
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.companyId != other.companyId && (this.companyId == null || !this.companyId.equals(other.companyId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 67 * hash + (this.price != null ? this.price.hashCode() : 0);
        hash = 67 * hash + this.quantity;
        hash = 67 * hash + (this.description != null ? this.description.hashCode() : 0);
        hash = 67 * hash + (this.purchaseDate != null ? this.purchaseDate.hashCode() : 0);
        hash = 67 * hash + (this.category != null ? this.category.hashCode() : 0);
        hash = 67 * hash + this.minimalBuyQuantity;
        hash = 67 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 67 * hash + (this.companyId != null ? this.companyId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        
        String newline = System.getProperty("line.separator");
        
        return "Offer" + newline
                + "Name: " + name + newline
                + "Price: " + price + newline
                + "Quantity: " + quantity + newline
                + "Description: " + description + newline
                + "Purchase Date: " + purchaseDate + newline
                + "Category: " + category + newline
                + "Minimal Buy Quantity: " + minimalBuyQuantity + newline;
    }

    
    
    
}
