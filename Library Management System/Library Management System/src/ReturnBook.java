import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class ReturnBook {

    public static JFrame baseFrame = mainWindow.mainWindow;
    public static JPanel returnBookPanel = new JPanel();
    public static JLabel returnBookIDLabel = new JLabel();
    public static JTextField issueIDTextField = new JTextField();
    public static JButton submitButton = new JButton();
    public static JButton backButton = new JButton();

    public static void display(){
        //creating the display panel
        returnBookPanel.setSize(1000, 1000);
        returnBookPanel.setMaximumSize(new Dimension(100, 100));
        returnBookPanel.setLayout(null);
        returnBookPanel.setBackground(GUI.colorSelector("PolarNightBlue4"));

        baseFrame.add(returnBookPanel);

        //creating the return book id label
        returnBookIDLabel.setText("Enter the Loan ID:");
        returnBookIDLabel.setFont(GUI.fontSelector("Font1Bold"));
        returnBookIDLabel.setSize(150, 50);
        returnBookIDLabel.setLocation(0,0);

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
        ImageIcon bookIcon = new ImageIcon("./Icons/BookIcon.png");
        Image bookScaledImage = bookIcon.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT);
        JLabel bookImageLabel = new JLabel(new ImageIcon(bookScaledImage));
        bookImageLabel.setSize(100,100);
        bookImageLabel.setLocation(800,800);

        //adding components to panel to display
        returnBookPanel.add(returnBookIDLabel);
        returnBookPanel.add(issueIDTextField);
        returnBookPanel.add(submitButton);
        returnBookPanel.add(backButton);
        returnBookPanel.add(bookImageLabel);
    }

    public static void submitButtonActionPressed() {
        DBConnector.connect();
        String loanIDString = issueIDTextField.getText();
        if (DBConnector.connection != null) {
            try {
                                String query1 = ("SELECT * FROM loanBookRecords WHERE LoanID = (SELECT MAX(LoanID) FROM loanBookRecords)");
                ResultSet rs = DBConnector.stmt.executeQuery(query1);

                System.out.println("maybe works");

                System.out.println(rs);
                if (rs.next()){

/*
                    //grabs the local date
                    LocalDate today = LocalDate.now();
                    //inserting the return date into the record table
                    String query2 = ("UPDATE loanBookRecords set returnDate = '"+ today + "WHERE LoanID = '" + loanIDInt + "");
                    PreparedStatement pstmt1 = DBConnector.connection.prepareStatement(query2);
                    pstmt1.setString(2, String.valueOf(today));
                    pstmt1.execute();
                    pstmt1.close();
*/
                    //setting if the book is loaned in the database back to no
                    String bookIDString = rs.getString("BookID");
                    String query3 = ("UPDATE book SET BookLoaned = 'N' WHERE BookID ='" + bookIDString + "'");
                    PreparedStatement pstmt2 = DBConnector.connection.prepareStatement(query3);
                    pstmt2.execute();
                    pstmt2.close();

                    DBConnector.disconnect();
                    JOptionPane.showMessageDialog(null, "Book Successfully Returned\n" +
                            "Book LoanID: " + loanIDString + "\nReturning to main menu", "Success",
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
        returnBookPanel.setVisible(false);
        Homepage.backToHomePage();
    }
}
