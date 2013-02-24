package cz.muni.fi.thesis;

import java.math.BigDecimal;

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
    private Long id;
    private Long company_id;

    public Long getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Long company_id) {
        this.company_id = company_id;
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
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.company_id != other.company_id && (this.company_id == null || !this.company_id.equals(other.company_id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 59 * hash + (this.price != null ? this.price.hashCode() : 0);
        hash = 59 * hash + this.quantity;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 59 * hash + (this.company_id != null ? this.company_id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Offer{" + "name=" + name + ", price=" + price + ", quantity=" + quantity + ", description=" + description + ", id=" + id + ", company_id=" + company_id + '}';
    }
    
    
}
