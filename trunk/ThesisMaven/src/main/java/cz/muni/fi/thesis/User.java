package cz.muni.fi.thesis;

/**
 *
 * @author matus
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
    
    
}
