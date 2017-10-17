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
                + "     newuser boolean,\n"
                + "     money integer,\n"
                + "     goal integer,\n"
                + "     currency text"
                + ");";
        
        String sql3 = "CREATE TABLE IF NOT EXISTS language (\n"
                + "     language text);";
        
        String sql4 = "CREATE TABLE IF NOT EXISTS loginfo (\n"
                + "     id integer,\n"
                + "     keeploggedin boolean,\n"
                + "     name text);";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            stmt.execute(sql2);
            stmt.execute(sql3);
            stmt.execute(sql4);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private Connection connect() {
        createNewTables();
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
    
    public void insertInitialAppData(boolean newuser, int money, 
            int goal, String currency) {
        String sql = "INSERT INTO appdata(newuser,"
                + "money,goal,currency) VALUES(?,?,?,?)";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, newuser);
            pstmt.setInt(2, money);
            pstmt.setInt(3, goal);
            pstmt.setString(4, currency);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void updateLoginData(String name, String password, String email) {
        System.out.println("test");
        String sql = "UPDATE logindata SET name = ? , "
                + "    password = ? , "
                + "    email = ?";
 
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
    
    public int selectUserId(String n){
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
    
    public boolean selectUserName(String n){
        boolean verify = false;
        String sql = "SELECT name FROM logindata";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            while (rs.next()) {
               String name = rs.getString("name");
                if (name.equals(n)) {
                    verify = true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return verify;
    }
    
    public boolean selectUserEmail(String mail){
        boolean verify = false;
        String sql = "SELECT email FROM logindata";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            while (rs.next()) {
               String email = rs.getString("email");
                if (email.equals(mail)) {
                    verify = true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return verify;
    }
    
    public int selectMoney(int userId) {
        int money = 0;
        String sql = "SELECT id, money FROM appdata";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            while (rs.next()) {
               int id = rs.getInt("id");
               money = rs.getInt("money");
                if (id == userId) {
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return money;
    }
    
    public void updateMoney(int money, int id) {
        String sql = "UPDATE appdata SET money = ? "
                + "WHERE id = ?";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setInt(1, money);
            pstmt.setInt(2, id);
            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public int selectGoal(int userId) {
        int goal = 0;
        String sql = "SELECT id, goal FROM appdata";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            while (rs.next()) {
               int id = rs.getInt("id");
               goal = rs.getInt("goal");
                if (id == userId) {
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return goal;
    }
    
    public void updateGoal(int goal, int id) {
        String sql = "UPDATE appdata SET goal = ? "
                + "WHERE id = ?";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setInt(1, goal);
            pstmt.setInt(2, id);
            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    // *************LANGAUGE******************
    
    public void insertLanguage(String language) {
        String sql = "INSERT INTO language(language) VALUES(?)";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, language);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public String selectLanguage() {
        String language = null;
        String sql = "SELECT language FROM language";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            while (rs.next()) {
               language = rs.getString("language"); //sprawdzić czy zadziała bez pętli
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return language;
    }
    
    public void updateLanguage(String language) {
        String sql = "UPDATE language SET language = ? ";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, language);
            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    // *************CURRENCY******************
    
    public String selectCurrency(int userId) {
        String currency = null;
        String sql = "SELECT id, currency FROM appdata";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            while (rs.next()) {
               int id = rs.getInt("id");
               currency = rs.getString("currency");
                if (id == userId) {
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return currency;
    }
    
    public void updateCurrency(String currency, int id) {
        String sql = "UPDATE appdata SET currency = ? "
                + "WHERE id = ?";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, currency);
            pstmt.setInt(2, id);
            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    // *************KEEP LOGGED IN******************
    
    public void insertLoginfo(int id, boolean keeploggedin, String name) {
        String sql = "INSERT INTO loginfo(id,keeploggedin,name) VALUES(?,?,?)";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setBoolean(2, keeploggedin);
            pstmt.setString(3, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public boolean selectLoginfo() {
        boolean keeploggedin = false;
        String sql = "SELECT keeploggedin FROM loginfo";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            while (rs.next()) {
               keeploggedin = rs.getBoolean("keeploggedin"); //sprawdzić czy zadziała bez pętli
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return keeploggedin;
    }
    
    public String selectLoginfoName() {
        String name = null;
        String sql = "SELECT name FROM loginfo";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            while (rs.next()) {
               name = rs.getString("name"); //sprawdzić czy zadziała bez pętli
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return name;
    }
    
    public void deleteLoginfo(int id) {
        String sql = "DELETE FROM loginfo WHERE id = ?";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            pstmt.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //************NEW USER **************************
    
    public boolean selectNewUser(int id) {
        boolean newUser = true;
        String sql = "SELECT id, newuser FROM appdata";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            while (rs.next()) {
               int userId = rs.getInt("id");
               boolean newUserInfo = rs.getBoolean("newUser");
                if (id == userId) {
                    newUser = newUserInfo;
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return newUser;
    }
    
    public void updateNewUser(boolean newUser, int id) {
        String sql = "UPDATE appdata SET newuser = ? "
                + "WHERE id = ?";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setBoolean(1, newUser);
            pstmt.setInt(2, id);
            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
     //************DELETE USER **************************
    
    public void deleteUser(int id) {
        String sql = "DELETE FROM logindata WHERE id = ?";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            pstmt.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        String sql2 = "DELETE FROM appdata WHERE id = ?";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql2)) {
 
            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            pstmt.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void updateUserName(String name, int id) {
        String sql = "UPDATE logindata SET name = ? "
                + "WHERE id = ?";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, name);
            pstmt.setInt(2, id);
            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void updateUserPassword(String pass, int id) {
        String sql = "UPDATE logindata SET password = ? "
                + "WHERE id = ?";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, pass);
            pstmt.setInt(2, id);
            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
     public String selectUserPassword(String name){
        String password = null;
        String sql = "SELECT password, name FROM logindata";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            while (rs.next()) {
               String n = rs.getString("name");
               password = rs.getString("password");
                if (name.equals(n)) {
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return password;
    }
    
}
