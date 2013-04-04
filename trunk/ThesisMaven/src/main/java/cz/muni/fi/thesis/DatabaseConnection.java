package cz.muni.fi.thesis;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matus Makovy
 */
public final class DatabaseConnection {
    private final static Logger log = LoggerFactory.getLogger(DatabaseConnection.class);
    private static DataSource source;
    private static String url;
    private static String user;
    private static String pass;
    
    public static void loadProperties(){
        Properties config = new Properties();
        try {
            config.load(new FileInputStream("/home/matus/system-for-selling-superfluous-goods/ThesisMaven/config.properties"));
            url = config.getProperty("url");
            user = config.getProperty("userName");
            pass = config.getProperty("password");
        } catch (Exception ex){
            log.error("Loading properties failed");  
        }
        
    }
            
    
    static 
    {   
        
        BasicDataSource ds = new BasicDataSource();
        loadProperties();
        ds.setUrl("jdbc:" + url + "?user=" + user + "&password=" + pass + "&useUnicode=yes&characterEncoding=UTF-8");
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        source = ds;
    }
    
    public static Connection getConnection()  {
        
        Connection con = null;
        try {
            con = source.getConnection();
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
        return con;
    }
    
    public static void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }
    
    public static void doRollback(Connection con) {
        if (con != null) {
            try {
                if (con.getAutoCommit()) {
                    throw new IllegalStateException("Connection is in the autocommit mode!");
                }
                con.rollback();
            } catch (SQLException ex) {
                log.error(ex.getMessage());
            }
        }
    }
}
