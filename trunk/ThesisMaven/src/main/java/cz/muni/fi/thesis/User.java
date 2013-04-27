package cz.muni.fi.thesis;

import java.util.Arrays;

/**
 *
 * @author Matus Makovy
 */
public class User {
    
    private String userName;
    private byte[] passwordHash;
    private Long idCompany;  
    private String verificationString;
    private int verified;

    public Long getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(Long idCompany) {
        this.idCompany = idCompany;
    }

    public byte[] getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(byte[] passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVerificationString() {
        return verificationString;
    }

    public void setVerificationString(String verificationString) {
        this.verificationString = verificationString;
    }

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if ((this.userName == null) ? (other.userName != null) : !this.userName.equals(other.userName)) {
            return false;
        }
        if (!Arrays.equals(this.passwordHash, other.passwordHash)) {
            return false;
        }
        if (this.idCompany != other.idCompany && (this.idCompany == null || !this.idCompany.equals(other.idCompany))) {
            return false;
        }
        if ((this.verificationString == null) ? (other.verificationString != null) : !this.verificationString.equals(other.verificationString)) {
            return false;
        }
        if (this.verified != other.verified) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + (this.userName != null ? this.userName.hashCode() : 0);
        hash = 31 * hash + Arrays.hashCode(this.passwordHash);
        hash = 31 * hash + (this.idCompany != null ? this.idCompany.hashCode() : 0);
        hash = 31 * hash + (this.verificationString != null ? this.verificationString.hashCode() : 0);
        hash = 31 * hash + this.verified;
        return hash;
    }

    
}
