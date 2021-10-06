import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class NewBook {
    //initialising background
    public static JFrame baseFrame = mainWindow.mainWindow;

    //initialising components
    public static JPanel newBookPanel = new JPanel();
    private static JLabel bookTitleLabel = new JLabel();
    private static JTextField bookTitleTextField = new JTextField();
    private static JLabel bookAuthorLabel = new JLabel();
    private static JTextField bookAuthorTextField = new JTextField();
    private static JLabel bookISBNLabel = new JLabel();
    private static JTextField bookISBNTextField = new JTextField();
    private static JLabel bookPublisherYearLabel = new JLabel();
    private static JTextField bookPublisherYearTextField = new JTextField();
    private static JButton newBookSubmitButton = new JButton();
    private static JButton backButton = new JButton();



    public static void newBook() {

        baseFrame.setTitle("Library Management System: New Book");
        //creating new panel for the new book
        newBookPanel.setSize(1000, 1000);
        newBookPanel.setLocation(0,0);
        newBookPanel.setLayout(null);
        newBookPanel.setBackground(GUI.colorSelector("PolarNightBlue4"));
        baseFrame.add(newBookPanel);

        //creating book ID label
        bookTitleLabel.setText("Enter Book Title:");
        bookTitleLabel.setSize(200, 50);
        bookTitleLabel.setLocation(0,0);

        //creating book ID text field
        bookTitleTextField.setSize(200, 50);
        bookTitleTextField.setLocation(200, 0);

        //creating book author label
        bookAuthorLabel.setText("Enter Book Author:");
        bookAuthorLabel.setSize(200, 50);
        bookAuthorLabel.setLocation(0,100);

        //creating book author text field
        bookAuthorTextField.setSize(200, 50);
        bookAuthorTextField.setLocation(200, 100);

        //creating book ISBN label
        bookISBNLabel.setText("Enter Book ISBN:");
        bookISBNLabel.setSize(200, 50);
        bookISBNLabel.setLocation(0,200);

        //creating book ISBN text field
        bookISBNTextField.setSize(200, 50);
        bookISBNTextField.setLocation(200, 200);

        //creating book publisher year label
        bookPublisherYearLabel.setText("Enter Book Publisher Year:");
        bookPublisherYearLabel.setSize(200, 50);
        bookPublisherYearLabel.setLocation(0,300);

        //creating book publisher year text field
        bookPublisherYearTextField.setSize(200, 50);
        bookPublisherYearTextField.setLocation(200, 300);

        newBookSubmitButton.setText("Submit");
        newBookSubmitButton.setSize(150, 50);
        newBookSubmitButton.setLocation(0, 400);
        newBookSubmitButton.addActionListener(evt -> submitButtonActionPressed());


        backButton.setText("Back");
        backButton.setSize(150, 50);
        backButton.setLocation(225, 400);
        backButton.addActionListener(evt -> backButtonActionPressed());

        //creating the book image and resizing it then adding it to a label
        ImageIcon bookIcon = new ImageIcon("./Icons/BookIcon.png");
        Image bookScaledImage = bookIcon.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT);
        JLabel bookImageLabel = new JLabel(new ImageIcon(bookScaledImage));
        bookImageLabel.setSize(100,100);
        bookImageLabel.setLocation(800,800);

        //adding components to the main panel
        newBookPanel.add(bookTitleLabel);
        newBookPanel.add(bookTitleTextField);
        newBookPanel.add(bookAuthorLabel);
        newBookPanel.add(bookAuthorTextField);
        newBookPanel.add(bookISBNLabel);
        newBookPanel.add(bookISBNTextField);
        newBookPanel.add(bookPublisherYearLabel);
        newBookPanel.add(bookPublisherYearTextField);
        newBookPanel.add(newBookSubmitButton);
        newBookPanel.add(backButton);
        newBookPanel.add(bookImageLabel);
        baseFrame.add(newBookPanel);
        newBookPanel.setVisible(true);
    }

    private static void backButtonActionPressed() {
        newBookPanel.setVisible(false);
        Homepage.main("admin");
    }

    private static void submitButtonActionPressed() {
        DBConnector.connect();

        if (DBConnector.connection != null){
            try {
                String bookTitleString = bookTitleTextField.getText();
                String bookAuthorString = bookAuthorTextField.getText();
                String bookISBNString = bookISBNTextField.getText();
                String bookPublisherYearString = bookPublisherYearTextField.getText();

                String query = ("INSERT INTO book(BookTitle,BookAuthor,BookISBN,BookPubYear) VALUES(?,?,?,?)");
                PreparedStatement pstmt = DBConnector.connection.prepareStatement(query);
                pstmt.setString(1, bookTitleString);
                pstmt.setString(2, bookAuthorString);
                pstmt.setString(3, bookISBNString);
                pstmt.setString(4, bookPublisherYearString);
                pstmt.execute();
                pstmt.close();
                movingBackToHomeScreen();
            }
            catch (Exception databaseError) {
                JOptionPane.showMessageDialog(null, "Database Connection Error"+ databaseError,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Database Connection Error",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private static void movingBackToHomeScreen() {

        try {
            //searches the database and find the highest value for book ID which will be the book
            // that was just added
            String query2 = ("SELECT BookID FROM book WHERE BookID =(SELECT MAX(BookID) FROM book)");
            ResultSet rs = DBConnector.stmt.executeQuery(query2);
            String bookID = rs.getString("BookID");
            rs.close();

            DBConnector.disconnect();
            JOptionPane.showMessageDialog(null, "Book Successfully Added\nBook ID: "
                    + bookID + "\nReturning to main menu", "Success", JOptionPane.INFORMATION_MESSAGE);
            backButtonActionPressed();
        } catch (Exception databaseError) {
            JOptionPane.showMessageDialog(null, "Database Connection Error" + databaseError,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }


    }
}




