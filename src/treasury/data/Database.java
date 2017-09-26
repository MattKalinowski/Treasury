package treasury.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    
    public static void createNewTables() {
        // SQLite connection string
        String url = "jdbc:sqlite:userdata.db";
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS logindata (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text,\n"
                + "	password string,\n"
                + "     email string\n"
                + ");";
        
        String sql2 = "CREATE TABLE IF NOT EXISTS appdata (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	logged boolean,\n"
                + "	keeplogged boolean,\n"
                + "     withfacebook boolean,\n"
                + "     money integer,\n"
                + "     goal integer,\n"
                + "     language text,\n"
                + "     currency text"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            stmt.execute(sql2);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private Connection connect() {
        createNewTables();
        // SQLite connection string
        String url = "jdbc:sqlite:userdata.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    public void insertLoginData(String name, String password, String email) {
        String sql = "INSERT INTO logindata(name,password,email) VALUES(?,?,?)";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            pstmt.setString(3, email);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void insertInitialAppData(boolean logged, boolean keeplogged, 
            boolean withfacebook, int money, int goal, String lang, String currency) {
        String sql = "INSERT INTO appdata(logged,keeplogged,withfacebook,"
                + "money,goal,language,currency) VALUES(?,?,?,?,?,?,?)";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, logged);
            pstmt.setBoolean(2, keeplogged);
            pstmt.setBoolean(3, withfacebook);
            pstmt.setInt(4, money);
            pstmt.setInt(5, goal);
            pstmt.setString(6, lang);
            pstmt.setString(7, currency);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void updateLoginData(String name, String password, String email) {
        String sql = "UPDATE logindata SET name = ? , "
                + "password = ? , "
                + "email = ?";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            pstmt.setString(3, email);
            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /*
    PLACE FOR ANOTHER UPDATE METHOD
    */
    
    public boolean verifyLoginData(String n, String p){
        String sql = "SELECT name, password FROM logindata";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
               String name = rs.getString("name");
               String pass = rs.getString("password");
                if (name.equals(n) && pass.equals(p)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    public int getUserId(String n){
        int id = 0;
        String sql = "SELECT id, name FROM logindata";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            while (rs.next()) {
               id = rs.getInt("id");
               String name = rs.getString("name");
                if (name.equals(n)) {
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }
    
}
