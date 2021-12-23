import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class returnCD {
    public static JFrame baseFrame = mainWindow.mainWindow;
    public static JPanel returnCDPanel = new JPanel();
    public static JLabel returnCDLabel = new JLabel();
    public static JTextField issueIDTextField = new JTextField();
    public static JButton submitButton = new JButton();
    public static JButton backButton = new JButton();

    public static void display(){
        //creating the display panel
        returnCDPanel.setSize(1000, 1000);
        returnCDPanel.setMaximumSize(new Dimension(100, 100));
        returnCDPanel.setLayout(null);
        returnCDPanel.setBackground(GUI.colorSelector("PolarNightBlue4"));

        baseFrame.add(returnCDPanel);

        //creating the return book id label
        returnCDLabel.setText("Enter the Loan ID:");
        returnCDLabel.setFont(GUI.fontSelector("Font1Bold"));
        returnCDLabel.setSize(150, 50);
        returnCDLabel.setLocation(0,0);

        //creating the return book id text field
        issueIDTextField.setSize(150,50);
        issueIDTextField.setLocation(200,0);

        //creating the submit button
        submitButton.setText("Submit");
        submitButton.setFont(GUI.fontSelector("Font1Bold"));
        submitButton.setSize(150, 50);
        submitButton.setLocation(0, 500);
        submitButton.addActionListener(e -> submitButtonActionPressed());

        //creating the back button
        backButton.setText("Back");
        backButton.setFont(GUI.fontSelector("Font1Bold"));
        backButton.setSize(150, 50);
        backButton.setLocation(200,500);
        backButton.addActionListener(e -> backButtonActionPressed());

        //creating the book image and resizing it then adding it to a label
        ImageIcon cdIcon = new ImageIcon("./Icons/CDIcon.png");
        Image cdScaledImage = cdIcon.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT);
        JLabel cdImageLabel = new JLabel(new ImageIcon(cdScaledImage));
        cdImageLabel.setSize(100,100);
        cdImageLabel.setLocation(800,800);

        //adding components to panel to display
        returnCDPanel.add(returnCDLabel);
        returnCDPanel.add(issueIDTextField);
        returnCDPanel.add(submitButton);
        returnCDPanel.add(backButton);
        returnCDPanel.add(cdImageLabel);
    }

    public static void submitButtonActionPressed() {
        DBConnector.connect();
        String loanIDString = issueIDTextField.getText();
        if (DBConnector.connection != null) {
            try {
                String query1 = ("SELECT * FROM loanCDRecords WHERE LoanID = (SELECT MAX(LoanID) FROM loanCDRecords)");
                ResultSet rs = DBConnector.stmt.executeQuery(query1);

                System.out.println("maybe works");

                System.out.println(rs);
                if (rs.next()){

                    //setting if the cd is loaned in the database back to no
                    String cdIDString = rs.getString("CDID");
                    String query3 = ("UPDATE cd SET CDLoaned = 'N' WHERE CDID ='" + cdIDString + "'");
                    PreparedStatement pstmt2 = DBConnector.connection.prepareStatement(query3);
                    pstmt2.execute();
                    pstmt2.close();

                    DBConnector.disconnect();
                    JOptionPane.showMessageDialog(null, "CD Successfully Returned\n" +
                                    "CD LoanID: " + loanIDString + "\nReturning to main menu", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    backButtonActionPressed();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Enter different ID",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
            catch(Exception databaseError){
                JOptionPane.showMessageDialog(null, "Database Error" + databaseError,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        }

    }


    public static void backButtonActionPressed() {
        issueIDTextField.setText("");

        //goes back to the homepage
        returnCDPanel.setVisible(false);
        Homepage.backToHomePage();
    }
}
