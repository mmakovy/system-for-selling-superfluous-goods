package cz.muni.fi.thesis;

/**
 * This class represents one customer, one company 
 * 
 * @author Matus Makovy
 */
public class Company {
    
    private String name;
    private String email;
    private String phoneNumber;
    private String street;
    private String city;
    private String country;
    private String psc;
    private String other;
    private Long id;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getPsc() {
        return psc;
    }

    public void setPsc(String psc) {
        this.psc = psc;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Company other = (Company) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.email == null) ? (other.email != null) : !this.email.equals(other.email)) {
            return false;
        }
        if ((this.phoneNumber == null) ? (other.phoneNumber != null) : !this.phoneNumber.equals(other.phoneNumber)) {
            return false;
        }
        if ((this.street == null) ? (other.street != null) : !this.street.equals(other.street)) {
            return false;
        }
        if ((this.city == null) ? (other.city != null) : !this.city.equals(other.city)) {
            return false;
        }
        if ((this.country == null) ? (other.country != null) : !this.country.equals(other.country)) {
            return false;
        }
        if ((this.psc == null) ? (other.psc != null) : !this.psc.equals(other.psc)) {
            return false;
        }
        if ((this.other == null) ? (other.other != null) : !this.other.equals(other.other)) {
            return false;
        }
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 73 * hash + (this.email != null ? this.email.hashCode() : 0);
        hash = 73 * hash + (this.phoneNumber != null ? this.phoneNumber.hashCode() : 0);
        hash = 73 * hash + (this.street != null ? this.street.hashCode() : 0);
        hash = 73 * hash + (this.city != null ? this.city.hashCode() : 0);
        hash = 73 * hash + (this.country != null ? this.country.hashCode() : 0);
        hash = 73 * hash + (this.psc != null ? this.psc.hashCode() : 0);
        hash = 73 * hash + (this.other != null ? this.other.hashCode() : 0);
        hash = 73 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        
        String newline = System.getProperty("line.separator");
        
        return "Company" + newline
                + "Name: " + name + newline
                + "E-mail: " + email + newline
                + "Phone number: " + phoneNumber + newline
                + "Address: " + newline
                + "Street: " + street + newline
                + "City: " + city + newline
                + "Psc: " + psc + newline
                + "Country: " + country + newline
                + "Other info: " + other + newline;
    }

    
    
}
