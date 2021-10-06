import java.sql.*;

public class DBConnector {
    public static Statement stmt;
    public static Connection connection;
    public static void connect() {
        try
        {
            //creating a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:Library-Management-System.db");
            stmt = connection.createStatement();
            Statement stmt1 = stmt;
            stmt1.setQueryTimeout(30);  // set timeout to 30 sec.

        }
        catch (Exception e) {
            //prints the database connection error
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

        }

    }

    public static void disconnect() {
        try {
            //if there is no database connection then it closes the connection
            connection = DriverManager.getConnection("jdbc:sqlite:Library-Management-System.db");
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
