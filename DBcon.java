import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBcon {
    private Connection connection = null;
    private Statement stmt = null;

    DBcon() {
        System.out.println("***** My Color Constructor ******");
        // Database connection properties
        String url = "jdbc:mysql://localhost:3306/mycolor";
        String username = "root";
        String password = "";
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            // Create the connection to the database
            connection = DriverManager.getConnection(url, username, password);
            // Create a statement
            stmt = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean Register(String name, String email, String password) {
        try {
            String query = "INSERT INTO user (name, email, password) VALUES ('" + name + "', '" + email + "', '"
                    + password + "')";
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean Login(String email, String password) {
        try {
            String query = "SELECT * FROM user WHERE email = '" + email + "' AND password = '" + password + "'";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ResultSet GetUserData(String email, String password) {
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM user WHERE email = '" + email + "' AND password = '" + password + "'";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public void addcolor(String wheelId, String colorname, int userId) {
        try {
            String query = "INSERT INTO colorlist (wheelId, colorname, userId) VALUES ('" + wheelId + "', '"
                    + colorname + "', '"
                    + userId + "')";
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletecolor(int colorid) {
        try {
            String query = "DELETE FROM colorlist WHERE colorid = '" + colorid + "'";
            System.out.println("Delete Query: " + query);
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getcolorUser(int userId) {
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM colorlist WHERE userId = '" + userId + "'";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public void CloseDb() {
        System.out.println("***** Close Data ******");
        // Close the database connection
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}