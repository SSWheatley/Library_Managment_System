import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class IssueCD {
    //public declared variables
    public static JFrame baseFrame = mainWindow.mainWindow;
    //private declared variables
    private static JPanel homePanel = new JPanel();
    private static JLabel studentNumberLabel = new JLabel();
    private static JTextField studentIDTextField = new JTextField();
    private static JLabel cdIDLabel = new JLabel();
    private static JTextField cdIDTextField = new JTextField();
    private static JLabel issueDateLabel = new JLabel();
    private static JFormattedTextField issueDateTextField = new JFormattedTextField();
    private static JLabel dueDateLabel = new JLabel();
    private static JFormattedTextField dueDateTextField = new JFormattedTextField();
    private static JButton submitButton = new JButton();
    private static JButton backButton = new JButton();

    public static void issueCDisplay(){
        //creating the display panel
        homePanel.setSize(1000, 1000);
        homePanel.setMaximumSize(new Dimension(100, 100));
        homePanel.setLayout(null);
        homePanel.setBackground(GUI.colorSelector("PolarNightBlue4"));

        baseFrame.add(homePanel);

        //creating student ID label and textfield
        studentNumberLabel.setText("Enter Student Number:");
        studentNumberLabel.setFont(GUI.fontSelector("Font1Bold"));
        studentNumberLabel.setSize(150, 50);
        studentNumberLabel.setLocation(0,0);

        studentIDTextField.setSize(150,50);
        studentIDTextField.setLocation(200,0);

        //creating the cd id label and textfield
        cdIDLabel.setText("Enter CD ID:");
        cdIDLabel.setFont(GUI.fontSelector("Font1Bold"));
        cdIDLabel.setSize(150, 50);
        cdIDLabel.setLocation(0,100);

        cdIDTextField.setSize(150,50);
        cdIDTextField.setLocation(200,100);

        //creating the issue date label and textfield
        issueDateLabel.setText("Enter Issue Date:");
        issueDateLabel.setFont(GUI.fontSelector("Font1Bold"));
        issueDateLabel.setSize(150, 50);
        issueDateLabel.setLocation(0,200);

        issueDateTextField.setSize(150,50);
        issueDateTextField.setLocation(200,200);
        LocalDate today = LocalDate.now();
        issueDateTextField.setValue(today);
        issueDateTextField.setEditable(false);

        //creating the due date label and textfield
        dueDateLabel.setText("Enter Due Date:");
        dueDateLabel.setFont(GUI.fontSelector("Font1Bold"));
        dueDateLabel.setSize(150,50);
        dueDateLabel.setLocation(0,300);

        dueDateTextField.setSize(150, 50);
        dueDateTextField.setLocation(200,300);
        dueDateTextField.setValue(today.plusWeeks(2));
        dueDateTextField.setEditable(false);

        //creating the submit button
        submitButton.setText("Submit");
        submitButton.setFont(GUI.fontSelector("Font1Bold"));
        submitButton.setSize(150, 50);
        submitButton.setLocation(0, 400);
        submitButton.addActionListener(e -> submitButtonActionPressed());

        //creating the back button
        backButton.setText("Back");
        backButton.setFont(GUI.fontSelector("Font1Bold"));
        backButton.setSize(150, 50);
        backButton.setLocation(200,400);
        backButton.addActionListener(e -> backButtonActionPressed());

        //creating the book image and resizing it then adding it to a label
        ImageIcon cdIcon = new ImageIcon("./Icons/CDIcon.png");
        Image cdScaledImage = cdIcon.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT);
        JLabel cdImageLabel = new JLabel(new ImageIcon(cdScaledImage));
        cdImageLabel.setSize(100,100);
        cdImageLabel.setLocation(800,800);

        //adding all the components to the homepage
        homePanel.add(studentNumberLabel);
        homePanel.add(studentIDTextField);
        homePanel.add(cdIDLabel);
        homePanel.add(cdIDTextField);
        homePanel.add(issueDateLabel);
        homePanel.add(issueDateTextField);
        homePanel.add(dueDateLabel);
        homePanel.add(dueDateTextField);
        homePanel.add(submitButton);
        homePanel.add(backButton);
        homePanel.add(cdImageLabel);

        homePanel.setVisible(true);

    }
    public static void submitButtonActionPressed() {
        //connects to the database
        DBConnector.connect();
        if (DBConnector.connection != null){
            try{
                //grabbing the text from the textfields to verify against the database
                String studentNumberString = studentIDTextField.getText();
                String cdIDString = cdIDTextField.getText();
                String issueDateString = issueDateTextField.getText();
                String dueDateString = dueDateTextField.getText();

                ResultSet rs=DBConnector.stmt.executeQuery("SELECT * FROM cd WHERE CDID='"+ cdIDString + "'");
                if (rs.next()) {
                    rs.close();
                    ResultSet rs2 = DBConnector.stmt.executeQuery("SELECT * FROM students WHERE StudentNumber='"
                            + studentNumberString + "'");
                    if (rs2.next()) {
                        rs2.close();
                        ResultSet rs3 = DBConnector.stmt.executeQuery("SELECT CDLoaned FROM cd WHERE CDID = '"
                                + cdIDString + "'");
                        if (rs3.next()) {
                            String hasBookBeenLoaned = rs3.getString("CDLoaned");
                            //checks if the book is loanable
                            if (hasBookBeenLoaned.equals("N")) {
                                //prepares a statement to be executed into the database
                                String query = ("INSERT INTO loanCDRecords(StudentNumber,CDID,IssueDate,DueDate) " +
                                        "VALUES (?,?,?,?)");
                                PreparedStatement pstmt = DBConnector.connection.prepareStatement(query);
                                pstmt.setString(1, studentNumberString);
                                pstmt.setString(2, cdIDString);
                                pstmt.setString(3, issueDateString);
                                pstmt.setString(4, dueDateString);
                                pstmt.execute();
                                pstmt.close();

                                //setting if the cd is loaned in the database to yes
                                String query2 = ("UPDATE cd SET CDLoaned = 'Y' WHERE CDID ='" + cdIDString + "'");
                                PreparedStatement pstmt2 = DBConnector.connection.prepareStatement(query2);
                                pstmt2.execute();
                                pstmt2.close();

                                movingBackToHomeScreen();
                            } else {
                                JOptionPane.showMessageDialog(null, "CD Cannot be Loaned \n" +
                                        "Please Select another CD :)", "Error", JOptionPane.ERROR_MESSAGE);
                            }

                        } else {
                            JOptionPane.showMessageDialog(null, "Incorrect Student Number",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect CD ID", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            catch (Exception databaseError) {
                JOptionPane.showMessageDialog(null, "Database Connection Error"+ databaseError,
                        "Error", JOptionPane.ERROR_MESSAGE);

            }
        }
    }


    private static void movingBackToHomeScreen() {

        if (DBConnector.connection != null) {
            try {
                //grabs the most recent loan cd id which will be the one that was just completed
                String query3 = ("SELECT * FROM loanCDRecords WHERE LoanID =(SELECT MAX(LoanID) FROM loanCDRecords)");
                ResultSet rs = DBConnector.stmt.executeQuery(query3);
                String cdLoanID = rs.getString("LoanID");
                rs.close();
                DBConnector.disconnect();
                //shows a message dialog with the loan id on
                JOptionPane.showMessageDialog(null, "CD Successfully Loaned\nCD LoanID: "
                        + cdLoanID + "\nReturning to main menu", "Success", JOptionPane.INFORMATION_MESSAGE);
                backButtonActionPressed();
            } catch (Exception databaseError) {
                //shows a message dialog with the database error
                JOptionPane.showMessageDialog(null, "Database Connection Error" + databaseError,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }


    public static void backButtonActionPressed() {
        //clears the textfields before going back to the homepage
        studentIDTextField.setText("");
        issueDateTextField.setText("");
        dueDateTextField.setText("");

        homePanel.setVisible(false);
        Homepage.backToHomePage();
    }
}