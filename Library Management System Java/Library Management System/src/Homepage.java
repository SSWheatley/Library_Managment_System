import javax.swing.*;
import java.awt.*;

public class Homepage {

    public static String userPrivilege;
    /*Fonts :
             Font1
             Font1Bold

       Colours:
               PolarNightBlue4 - background colour
               Frost3          - text box colour

     */

    public static void homepageChecker(String userPrivilegePassThrough){
        userPrivilege = userPrivilegePassThrough;
        if (userPrivilege.equals("admin")) {
            adminHomePage.display();
        }
        else if (userPrivilege.equals("operator")) {
            operatorHomePage.display(userPrivilege);
        }
        else {
            JOptionPane.showMessageDialog(null, "Error grabbing User Privilege\n" +
                    "Please try closing the program and rerunning it :)", "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    //this exists to make sure that the homepage that the user goes back to is the correct one
    public static void backToHomePage() {
        String userPrivilegeChecker = userPrivilege;
        homepageChecker(userPrivilegeChecker);

    }

    public static void main(String userPrivilege) {
        homepageChecker(userPrivilege);
    }


}
class operatorHomePage extends GUI{
    public static JFrame baseFrame = mainWindow.mainWindow;
    public static JPanel homePanel = new JPanel();
    public static JButton issueBookButton = new JButton();
    public static JButton issueCDButton = new JButton();
    public static JButton issuedBooksButton = new JButton();
    public static JButton issuedCDButton = new JButton();
    public static JButton returnBookButton = new JButton();
    public static JButton returnCDButton = new JButton();
    public static JButton logoutButton = new JButton();
    public static JButton printButton = new JButton();

    public static  void display(String userPrivilege) {
        //changing the title of the window based on what homepage is shown
        baseFrame.setTitle("Library Management System: Operator Home Page");
        baseFrame.add(homePanel);

        //creating the issue book button
        issueBookButton.setText("Issue Book");
        issueBookButton.setFont(GUI.fontSelector("Font1Bold"));
        issueBookButton.setSize(150, 50);
        issueBookButton.setLocation(0, 0);
        issueBookButton.addActionListener(e -> issueBookButtonActionPressed());

        //creating the issue cd button
        issueCDButton.setText("Issue CD");
        issueCDButton.setFont(GUI.fontSelector("Font1Bold"));
        issueCDButton.setSize(150, 50);
        issueCDButton.setLocation(200, 0);
        issueCDButton.addActionListener(e -> issueCDButtonActionPressed());


        //creating the return book button
        returnBookButton.setText("Return Books");
        returnBookButton.setFont(GUI.fontSelector("Font1Bold"));
        returnBookButton.setSize(150, 50);
        returnBookButton.setLocation(0, 100);
        returnBookButton.addActionListener(e -> returnBookButtonActionPressed());

        //creating the return CD button
        returnCDButton.setText("Return CD");
        returnCDButton.setFont(GUI.fontSelector("Font1Bold"));
        returnCDButton.setSize(150, 50);
        returnCDButton.setLocation(200, 100);
        returnCDButton.addActionListener(e -> returnCDButtonActionPressed());

        //creating the print Book button
        printButton.setText("Print");
        printButton.setFont(GUI.fontSelector("Font1Bold"));
        printButton.setSize(150, 50);
        printButton.setLocation(400, 300);
        printButton.addActionListener(e -> printButtonActionPressed(userPrivilege));

        //creating the logout button
        logoutButton.setText("Logout");
        logoutButton.setFont(GUI.fontSelector("Font1Bold"));
        logoutButton.setSize(150, 50);
        logoutButton.setLocation(400, 400);
        logoutButton.addActionListener(e -> adminHomePage.backToLoginActionPressed());


        //creating the operator homepage panel

        homePanel.setSize(1000, 1000);
        homePanel.setMaximumSize(new Dimension(100, 100));
        //loginPanel.setLayout(new GridLayout(3,2));
        homePanel.setLayout(null);
        homePanel.setBackground(colorSelector("PolarNightBlue4"));


        homePanel.add(issueBookButton);
        homePanel.add(issueCDButton);
        homePanel.add(returnBookButton);
        homePanel.add(returnCDButton);
        homePanel.add(printButton);
        homePanel.add(logoutButton);

        homePanel.setVisible(true);


    }

    private static void printButtonActionPressed(String userPrivilege) {
        Print.print(userPrivilege);
    }

    public static void issueBookButtonActionPressed(){
        homePanel.setVisible(false);
        IssueBook.issueBookDisplay();

    }

    public static void issueCDButtonActionPressed() {
        homePanel.setVisible(false);
        IssueCD.issueCDisplay();
    }

    public static void returnBookButtonActionPressed() {
        homePanel.setVisible(false);
        ReturnBook.display();
    }

    public static void returnCDButtonActionPressed() {
        homePanel.setVisible(false);
        //ReturnCD();
    }

}

class adminHomePage extends operatorHomePage{

    private static JButton newBookButton = new JButton();
    private static JButton newCDButton = new JButton();
    private static JButton newUserButton = new JButton();
    private static JButton newStudentButton = new JButton();
    private static JLabel adminLabel = new JLabel();
    private static JLabel adminLabel2 = new JLabel();

    public static void display() {
        String userPrivilege = "admin";
        operatorHomePage.display(userPrivilege);
        baseFrame.setTitle("Library Management System: Admin Home Page");
        baseFrame.add(homePanel);

        homePanel.setSize(1000, 1000);
        homePanel.setLocation(0,0);
        homePanel.setLayout(null);
        homePanel.setBackground(colorSelector("PolarNightBlue4"));

        //creating the admin label
        adminLabel.setText("Admin Features");
        adminLabel.setSize(150,20);
        adminLabel.setLocation(0,250);
        adminLabel.setFont(GUI.fontSelector("Font1Bold"));
        adminLabel2.setText("--------------------------------------------------------------------------------" +
                "---------");
        adminLabel2.setSize(1000,20);
        adminLabel2.setLocation(0,270);

        //creating the book button
        newBookButton.setText("New Book");
        newBookButton.setFont(GUI.fontSelector("Font1Bold"));
        newBookButton.setSize(150, 50);
        newBookButton.setLocation(0, 300);
        newBookButton.addActionListener(evt -> newBookButtonActionPressed());

        //creating the new cd button
        newCDButton.setText("New CD");
        newCDButton.setFont(GUI.fontSelector("Font1Bold"));
        newCDButton.setSize(150, 50);
        newCDButton.setLocation(200, 300);
        newCDButton.addActionListener(evt -> newCDButtonActionPressed());

        //creating the new user button
        newUserButton.setText("New User");
        newUserButton.setFont(GUI.fontSelector("Font1Bold"));
        newUserButton.setSize(150, 50);
        newUserButton.setLocation(0, 400);
        newUserButton.addActionListener(evt -> newUserButtonActionPressed());

        //creating the new student button
        newStudentButton.setText("New Student");
        newStudentButton.setFont(GUI.fontSelector("Font1Bold"));
        newStudentButton.setSize(150, 50);
        newStudentButton.setLocation(200, 400);

        //adding all components to the panel
        homePanel.add(newBookButton);
        homePanel.add(newCDButton);
        homePanel.add(newUserButton);
        homePanel.add(newStudentButton);
        homePanel.add(newUserButton);
        homePanel.add(adminLabel);
        homePanel.add(adminLabel2);
        homePanel.setVisible(true);
    }

    private static void newUserButtonActionPressed() {
        homePanel.setVisible(false);
        NewUser.newUser();
    }

    private static void newBookButtonActionPressed() {
        homePanel.setVisible(false);
        NewBook.newBook();
    }

    private static void newCDButtonActionPressed() {
        homePanel.setVisible(false);
        NewCD.newCD();
    }

    public static void backToLoginActionPressed() {
        homePanel.setVisible(false);
        GUI.loginGui();
    }
}