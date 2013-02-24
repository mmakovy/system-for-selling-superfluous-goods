package cz.muni.fi.thesis;

import java.sql.*;

/**
 *
 * @author Matus Makovy
 */
public final class DatabaseConnection {
    
    public static Connection getConnection()  {
        
        Connection connection = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");  
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thesis", "root", "pavot");             
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        } 
        
        return connection;
    }
    
    public static void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
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
               System.err.println(ex.getMessage());
            }
        }
    }
}
