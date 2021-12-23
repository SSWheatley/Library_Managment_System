import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;

public class NewUser {
    //public declared variables
    public static JFrame baseFrame = mainWindow.mainWindow;
    public static JPanel newUserPanel = new JPanel();
    public static JLabel usernameLabel = new JLabel();
    public static JTextField usernameTextField = new JTextField();
    public static JLabel passwordLabel = new JLabel();
    public static JTextField passwordTextField = new JTextField();
    public static JLabel userEmailLabel = new JLabel();
    public static JTextField userEmailTextField = new JTextField();
    public static JLabel userPrivilegeLabel = new JLabel();
    public static String[] privileges = new String[] {"operator", "admin"};
    public static JComboBox<String> userPrivilegeComboBox = new JComboBox<>(privileges);
    public static JButton newUserSubmitButton = new JButton();
    public static JButton backButton = new JButton();



    public static void newUser() {
        baseFrame.setTitle("Library Management System: New User");
        //creating the user panel
        newUserPanel.setSize(1000, 1000);
        newUserPanel.setLocation(0,0);
        newUserPanel.setLayout(null);
        newUserPanel.setBackground(GUI.colorSelector("PolarNightBlue4"));
        baseFrame.add(newUserPanel);

        //creating the username label
        usernameLabel.setText("Username:");
        usernameLabel.setSize(200,50);
        usernameLabel.setLocation(0,0);
        usernameTextField.setSize(200,50);
        usernameTextField.setLocation(200,0);

        //creating the password label and text field

        passwordLabel.setText("Password:");
        passwordLabel.setSize(200,50);
        passwordLabel.setLocation(0,100);
        passwordTextField.setSize(200,50);
        passwordTextField.setLocation(200,100);

        //creating the user email label and textfield
        userEmailLabel.setText("Email:");
        userEmailLabel.setSize(200,50);
        userEmailLabel.setLocation(0,200);
        userEmailTextField.setSize(200,50);
        userEmailTextField.setLocation(200,200);

        //creating the user privilege label and combo box
        userPrivilegeLabel.setText("Privilege:");
        userPrivilegeLabel.setSize(200,50);
        userPrivilegeLabel.setLocation(0,300);
        userPrivilegeComboBox.setSize(200,50);
        userPrivilegeComboBox.setLocation(200,300);

        //creating the user submit button
        newUserSubmitButton.setText("Submit");
        newUserSubmitButton.setSize(150, 50);
        newUserSubmitButton.setLocation(0, 400);
        newUserSubmitButton.addActionListener(evt -> submitButtonActionPressed());

        //creating the back button
        backButton.setText("Back");
        backButton.setSize(150, 50);
        backButton.setLocation(225, 400);
        backButton.addActionListener(evt -> backButtonActionPressed());

        //creating the user image and resizing it then adding it to a label
        ImageIcon userIcon = new ImageIcon("./Icons/UserIcon.png");
        Image userScaledImage = userIcon.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT);
        JLabel userImageLabel = new JLabel(new ImageIcon(userScaledImage));
        userImageLabel.setSize(100,100);
        userImageLabel.setLocation(800,800);

        //adding components to the panel
        baseFrame.add(newUserPanel);
        newUserPanel.add(usernameLabel);
        newUserPanel.add(usernameTextField);
        newUserPanel.add(passwordLabel);
        newUserPanel.add(passwordTextField);
        newUserPanel.add(userEmailLabel);
        newUserPanel.add(userEmailTextField);
        newUserPanel.add(userPrivilegeLabel);
        newUserPanel.add(userPrivilegeComboBox);
        newUserPanel.add(newUserSubmitButton);
        newUserPanel.add(backButton);
        newUserPanel.add(userImageLabel);

        newUserPanel.setVisible(true);

    }
    private static void backButtonActionPressed() {
        //goes back to the homepage as well as clearing the text fields
        newUserPanel.setVisible(false);
        usernameTextField.setText("");
        passwordTextField.setText("");
        userEmailTextField.setText("");
        Homepage.main("admin");
    }

    private static void submitButtonActionPressed() {
        //connects to the database
        DBConnector.connect();

        if (DBConnector.connection != null){
            try {
                //grabbing the text from the textfields to put into the database
                String usernameString = usernameTextField.getText();
                String passwordString = passwordTextField.getText();
                String userEmailString = userEmailTextField.getText();
                Object userPrivilegeSObj = userPrivilegeComboBox.getSelectedItem();
                String userPrivilegeString = ""+ userPrivilegeSObj;

                //preparing the statement ready to execute into the database
                String query = ("INSERT INTO user(Username,Password,UserEmail,userPrivilege) VALUES(?,?,?,?)");
                PreparedStatement pstmt = DBConnector.connection.prepareStatement(query);
                pstmt.setString(1, usernameString);
                pstmt.setString(2, passwordString);
                pstmt.setString(3, userEmailString);
                pstmt.setString(4,userPrivilegeString);
                System.out.println(pstmt);
                pstmt.execute();

                //shows a message dialog on successful input of data
                JOptionPane.showMessageDialog(null, "User Successfully added \n" +
                        "Returning to main menu", "Success", JOptionPane.INFORMATION_MESSAGE) ;
                pstmt.close();
                DBConnector.disconnect();
                backButtonActionPressed();

            }
            catch (Exception databaseError) {
                //shows a message dialog with the database error
                JOptionPane.showMessageDialog(null, "Database Error" + databaseError,
                        "Error", JOptionPane.ERROR_MESSAGE);

            }
        }
        else {
            //will display if the database has a connection error
            JOptionPane.showMessageDialog(null, "Database Connection Error", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

