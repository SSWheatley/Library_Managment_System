import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;


public class GUI {
    //public declared variables
    public static Component colorSelector;
    public static JFrame baseFrame = mainWindow.mainWindow;
    public static JPanel loginPanel = new JPanel();
    public static JLabel passwordLabel = new JLabel();

    //private declared variables
    private static JLabel usernameLabel = new JLabel();
    private static JTextField usernameTextField = new JTextField();
    private static JPasswordField passwordTextField = new JPasswordField();
    private static JButton loginButton = new JButton();



    // <editor-fold defaultstate="collapsed" desc="color and font selectors">//
    static Color colorSelector(String colorName){
        switch (colorName) {
            case "PolarNightBlue4" -> {
                //background colour (polarNightBlue4)
                return new Color(76, 86, 106);
            }
            case "Frost3" -> {
                //text field colour (frost3
                return new Color(129, 161, 193);
            }
            case "Frost4" -> {
                //foreground colour (frost4)
                return new Color(94, 129, 172);
            }
        }
        return null;
    }

    static Font fontSelector(String fontName){
        switch (fontName) {
            case "Font1" -> {
                //normal font1
                return new Font(Font.SERIF, Font.PLAIN, 14);
            }
            case "Font1Bold" -> {
                //normal font1Bold
                return new Font(Font.SERIF, Font.BOLD, 14);
            }
        }
        return null;
    }
    // </editor-fold>//

    public static void loginGui() {
        mainWindow.main();

        baseFrame.setTitle("Library Management System: Login Page");
        baseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //creating the login panel
        loginPanel.setSize(1000, 1000);
        loginPanel.setMaximumSize(new Dimension(1000, 1000));
        loginPanel.setLayout(null);
        loginPanel.setBackground(colorSelector("PolarNightBlue4"));

        // creating the label for username as well as the text field
        usernameLabel.setFont(fontSelector("font1Bold"));
        usernameLabel.setText("Username");
        usernameLabel.setBackground(colorSelector("Frost3"));
        usernameLabel.setSize(100, 50);
        usernameLabel.setLocation(0, 0);

        usernameTextField = new JTextField();
        usernameTextField.setSize(100, 50);
        usernameTextField.setLocation(150, 0);
        usernameTextField.setBackground(colorSelector("Frost3"));

        // creating the label for username as well as the text field

        passwordLabel.setFont(fontSelector("Font1Bold"));
        passwordLabel.setText("Password");
        passwordLabel.setBackground(colorSelector("Frost4"));
        passwordLabel.setSize(100, 50);
        passwordLabel.setLocation(0, 100);

        //creates the password text field
        passwordTextField.setLocation(150, 100);
        passwordTextField.setSize(100, 50);
        passwordTextField.setBackground(colorSelector("Frost3"));

        loginButton.setText("Login");
        loginButton.setLocation(50, 200);
        loginButton.setSize(100, 50);

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameTextField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordTextField);
        loginPanel.add(loginButton);

        //passes through the username to the homepage
        loginButton.addActionListener(evt -> {
            DBConnector.connect();

            if (DBConnector.connection != null) {
                try {
                    char[] password = passwordTextField.getPassword();
                    //this isn't the best way of doing this but its all i can do rn
                    String password1 = new String(password);
                    String query = ("SELECT * FROM user WHERE Username = '" + usernameTextField.getText()
                            + "' and Password = '" + password1 + "'");
                    ResultSet rs = DBConnector.stmt.executeQuery(query);

                    if (rs.next()) {
                        String userPrivilege = rs.getString("UserPrivilege");
                        if (userPrivilege.equals("admin")) {
                            usernameTextField.setText("");
                            passwordTextField.setText("");
                            JOptionPane.showMessageDialog(null, "Successfully Logged " +
                                    "In As Admin");
                            loginPanel.setVisible(false);
                            rs.close();
                            DBConnector.disconnect();
                            usernameTextField.setText("");
                            passwordTextField.setText("");
                            Homepage.main(userPrivilege);

                        } else if (userPrivilege.equals("operator")) {
                            usernameTextField.setText("");
                            passwordTextField.setText("");
                            JOptionPane.showMessageDialog(null, "Successfully Logged " +
                                    "In As Operator");
                            loginPanel.setVisible(false);
                            rs.close();
                            DBConnector.disconnect();
                            usernameTextField.setText("");
                            passwordTextField.setText("");
                            Homepage.main(userPrivilege);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect Username or Password");
                    }
                }
                catch (Exception databaseError) {
                    JOptionPane.showMessageDialog(null, "Database Connection Error"
                            + databaseError, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Database Not open close this window " +
                        "and try again");
                }
            });

        //creating the quit button
        JButton quitButton = new JButton("Quit");
        quitButton.setSize(100, 50);
        quitButton.setLocation(150, 200);
        loginPanel.add(quitButton);
        quitButton.addActionListener(e -> System.exit(0));

        //adding everything together
        baseFrame.add(loginPanel);
        baseFrame.setVisible(true);
        loginPanel.setVisible(true);

    }

}