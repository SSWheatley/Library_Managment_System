import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class IssueBook {
    //public declared variables
    public static JFrame baseFrame = mainWindow.mainWindow;

    //private declared variables
    private static JPanel homePanel = new JPanel();
    private static JLabel studentNumberLabel = new JLabel();
    private static JTextField studentIDTextField = new JTextField();
    private static JLabel bookIDLabel = new JLabel();
    private static JTextField bookIDTextField = new JTextField();
    private static JLabel issueDateLabel = new JLabel();
    private static JFormattedTextField issueDateTextField = new JFormattedTextField();
    private static JLabel dueDateLabel = new JLabel();
    private static JFormattedTextField dueDateTextField = new JFormattedTextField();
    private static JButton submitButton = new JButton();
    private static JButton backButton = new JButton();

    public static void issueBookDisplay() {
        baseFrame.setTitle("Library Management System: Issue Book");
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

        //creating the book id label and textfield
        bookIDLabel.setText("Enter Book ID:");
        bookIDLabel.setFont(GUI.fontSelector("Font1Bold"));
        bookIDLabel.setSize(150, 50);
        bookIDLabel.setLocation(0,100);
        bookIDTextField.setSize(150,50);
        bookIDTextField.setLocation(200,100);

        //creating the issue data and textfield
        issueDateLabel.setText("Enter Issue Date:");
        issueDateLabel.setFont(GUI.fontSelector("Font1Bold"));
        issueDateLabel.setSize(150, 50);
        issueDateLabel.setLocation(0,200);

        issueDateTextField.setSize(150,50);
        issueDateTextField.setLocation(200,200);
        LocalDate today = LocalDate.now();
        issueDateTextField.setValue(today);
        issueDateTextField.setEditable(false);

        //creating due date label and text field
        dueDateLabel.setText("Enter Due Date:");
        dueDateLabel.setFont(GUI.fontSelector("Font1Bold"));
        dueDateLabel.setSize(150,50);
        dueDateLabel.setLocation(0,300);

        dueDateTextField.setSize(150, 50);
        dueDateTextField.setLocation(200,300);
        dueDateTextField.setValue(today.plusWeeks(2));
        dueDateTextField.setEditable(false);

        //creating submit button
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

        //adding all components
        homePanel.add(studentNumberLabel);
        homePanel.add(studentIDTextField);
        homePanel.add(bookIDLabel);
        homePanel.add(bookIDTextField);
        homePanel.add(issueDateLabel);
        homePanel.add(issueDateTextField);
        homePanel.add(dueDateLabel);
        homePanel.add(dueDateTextField);
        homePanel.add(submitButton);
        homePanel.add(backButton);
        homePanel.add(bookImageLabel);

        homePanel.setVisible(true);

    }

    public static void submitButtonActionPressed() {
        DBConnector.connect();
        if (DBConnector.connection != null){
            try{
                String studentNumberString = studentIDTextField.getText();
                String bookIDString = bookIDTextField.getText();
                String issueDateString = issueDateTextField.getText();
                String dueDateString = dueDateTextField.getText();

                ResultSet rs=DBConnector.stmt.executeQuery("SELECT * FROM book WHERE bookID='"+ bookIDString + "'");
                if (rs.next())
                {
                    rs.close();
                    ResultSet rs2=DBConnector.stmt.executeQuery("SELECT * FROM students WHERE StudentNumber='" +
                            studentNumberString + "'");
                    if (rs2.next())
                    {
                        rs2.close();
                        ResultSet rs3=DBConnector.stmt.executeQuery("SELECT BookLoaned FROM book WHERE BookID = '"
                                + bookIDString + "'");
                        while (rs3.next()) {
                            String hasBookBeenLoaned = rs3.getString("BookLoaned");
                            if (hasBookBeenLoaned.equals("N")){
                                String query = ("INSERT INTO loanBookRecords(StudentNumber,BookID,IssueDate,DueDate) " +
                                        "VALUES (?,?,?,?)");
                                PreparedStatement pstmt = DBConnector.connection.prepareStatement(query);
                                pstmt.setString(1, studentNumberString);
                                pstmt.setString(2, bookIDString);
                                pstmt.setString(3, issueDateString);
                                pstmt.setString(4, dueDateString);
                                System.out.println(pstmt);
                                pstmt.execute();
                                pstmt.close();

                                //setting if the book is loaned in the database to yes
                                String query2 = ("UPDATE book SET BookLoaned = 'Y' WHERE BookID ='" + bookIDString
                                        + "'");
                                PreparedStatement pstmt2 = DBConnector.connection.prepareStatement(query2);
                                pstmt2.execute();
                                pstmt2.close();

                                movingBackToHomeScreen();
                            }
                            else{
                                JOptionPane.showMessageDialog(null, "Book Cannot be Loaned \n" +
                                        "Please Select another book :)", "Error", JOptionPane.ERROR_MESSAGE);
                            }

                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Incorrect Student Number",
                                "Error",JOptionPane.ERROR_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Incorrect Book ID", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
            catch (Exception databaseError) {
                JOptionPane.showMessageDialog(null, "Database Connection Error"+ databaseError,
                        "Error", JOptionPane.ERROR_MESSAGE);

            }
        }
    }
    private static void movingBackToHomeScreen() {
        try {
            //searches the database and find the highest value for loanID which will be the loan that
            // was just completed
            String query3 = ("SELECT LoanID FROM loanBookRecords WHERE LoanID =(SELECT MAX(LoanID)" +
                    " FROM loanBookRecords)");
            ResultSet rs = DBConnector.stmt.executeQuery(query3);
            String bookLoanID = rs.getString("LoanID");
            rs.close();

            DBConnector.disconnect();
            JOptionPane.showMessageDialog(null, "Book Successfully Loaned\nBook LoanID: "
                    + bookLoanID + "\nReturning to main menu", "Success", JOptionPane.INFORMATION_MESSAGE);
            backButtonActionPressed();
        }
        catch (Exception databaseError) {
            JOptionPane.showMessageDialog(null, "Database Connection Error" + databaseError,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void backButtonActionPressed() {
        //goes back to the homepage
        homePanel.setVisible(false);
        Homepage.backToHomePage();
    }
}
