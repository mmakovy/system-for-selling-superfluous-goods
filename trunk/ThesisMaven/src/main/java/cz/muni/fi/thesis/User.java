package cz.muni.fi.thesis;

/**
 *
 * @author Matus Makovy
 */
public class User {
    
    private String userName;
    private byte[] hash;
    private Long id;  
    private String hashVer;
    private int active;

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getHashVer() {
        return hashVer;
    }

    public void setHashVer(String hashVer) {
        this.hashVer = hashVer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.hashVer == null) ? (other.hashVer != null) : !this.hashVer.equals(other.hashVer)) {
            return false;
        }
        if (this.active != other.active) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + (this.userName != null ? this.userName.hashCode() : 0);
        hash = 41 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 41 * hash + (this.hashVer != null ? this.hashVer.hashCode() : 0);
        hash = 41 * hash + this.active;
        return hash;
    }
    
    
    
    
}
