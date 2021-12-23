//import dependencies
import java.util.Scanner;
import java.sql.*;

public class Login {
    public String passWord;
    public String userName;


    public static void createUser() {
       DBConnector.connect();

        if(DBConnector.connection !=null)
        {
            System.out.println("Database Still open");
        }
        try {

            Scanner usernameInput = new Scanner(System.in);
            System.out.println("Enter Username: ");
            String userName = usernameInput.next();

            Scanner passwordInput = new Scanner(System.in);
            System.out.println("Enter Password: ");
            String passWord = passwordInput.next();

            String query = "select * from user where Username=? and Password=?";
            PreparedStatement stmt = DBConnector.connection.prepareStatement(query);
            //Parameters for query
            stmt.setString(1, userName);
            stmt.setString(2, passWord);
            //executing the query
            ResultSet res = stmt.executeQuery();
            //testing
            System.out.println("Test001");
            if(res.next())
            {

                Login user = new Login();
                user.userName = res.getString("Username");
                user.passWord = res.getString("Password");
                System.out.println(user.userName + ", "+ user.passWord);
            }
            else{
                //testing
                System.out.println("Incorrect username or password");
            }

        }
        catch(Exception e) {
            System.out.println(e);
            //testing
            System.out.println("It doesn't work");
        }
        //testing
        System.out.println("done?");

    }

    public static void main(String[] args) {
        createUser();
    }

    public static void main() {
        createUser();
    }
}