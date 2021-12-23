import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class NewCD {
    //initialising background
    public static JFrame baseFrame = mainWindow.mainWindow;

    //initialising components
    public static JPanel newCDPanel = new JPanel();
    private static JLabel cdTitleLabel = new JLabel();
    private static JTextField cdTitleTextField = new JTextField();
    private static JLabel cdArtistLabel = new JLabel();
    private static JTextField cdArtistTextField = new JTextField();
    private static JLabel cdReleaseYearLabel = new JLabel();
    private static JTextField cdReleaseYearTextField = new JTextField();
    private static JButton newCDSubmitButton = new JButton();
    private static JButton backButton = new JButton();



    public static void newCD() {

        baseFrame.setTitle("Library Management System: New CD");

        newCDPanel.setSize(1000, 1000);
        newCDPanel.setLocation(0,0);
        newCDPanel.setLayout(null);
        newCDPanel.setBackground(GUI.colorSelector("PolarNightBlue4"));
        baseFrame.add(newCDPanel);

        //creating book ID label
        cdTitleLabel.setText("Enter Book Title:");
        cdTitleLabel.setSize(200, 50);
        cdTitleLabel.setLocation(0,0);

        //creating book ID text field
        cdTitleTextField.setSize(200, 50);
        cdTitleTextField.setLocation(200, 0);

        //creating book author label

        cdArtistLabel.setText("Enter Book Author:");
        cdArtistLabel.setSize(200, 50);
        cdArtistLabel.setLocation(0,100);

        //creating book author text field
        cdArtistTextField.setSize(200, 50);
        cdArtistTextField.setLocation(200, 100);

        //creating book publisher year label
        cdArtistLabel.setText("Enter CD Release Year:");
        cdArtistLabel.setSize(200, 50);
        cdArtistLabel.setLocation(0,300);

        //creating book publisher year text field
        cdArtistTextField.setSize(200, 50);
        cdArtistTextField.setLocation(200, 300);

        newCDSubmitButton.setText("Submit");
        newCDSubmitButton.setSize(150, 50);
        newCDSubmitButton.setLocation(0, 400);
        newCDSubmitButton.addActionListener(evt -> submitButtonActionPressed());


        backButton.setText("Back");
        backButton.setSize(150, 50);
        backButton.setLocation(225, 400);
        backButton.addActionListener(evt -> backButtonActionPressed());

        //creating the book image and resizing it then adding it to a label
        ImageIcon cdIcon = new ImageIcon("./Icons/CDIcon.png");
        Image cdScaledImage = cdIcon.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT);
        JLabel cdImageLabel = new JLabel(new ImageIcon(cdScaledImage));
        cdImageLabel.setSize(100,100);
        cdImageLabel.setLocation(800,800);

        //adding components to the main panel
        newCDPanel.add(cdTitleLabel);
        newCDPanel.add(cdTitleTextField);
        newCDPanel.add(cdArtistLabel);
        newCDPanel.add(cdArtistTextField);
        newCDPanel.add(cdReleaseYearLabel);
        newCDPanel.add(cdReleaseYearTextField);
        newCDPanel.add(newCDSubmitButton);
        newCDPanel.add(backButton);
        newCDPanel.add(cdImageLabel);

        baseFrame.add(newCDPanel);
        newCDPanel.setVisible(true);
    }

    private static void backButtonActionPressed() {
        //resets the textfields back to blank
        cdTitleTextField.setText("");
        cdArtistTextField.setText("");
        cdReleaseYearTextField.setText("");
        newCDPanel.setVisible(false);
        Homepage.main("admin");
    }

    private static void submitButtonActionPressed() {
        DBConnector.connect();

        if (DBConnector.connection != null){
            //testing
            try {
                String cdTitleString = cdTitleTextField.getText();
                String cdArtistString = cdArtistTextField.getText();
                String cdReleaseYearString = cdReleaseYearLabel.getText();

                String query = ("INSERT INTO cd(CDName,CDArtist,CDRelYear) VALUES(?,?,?)");
                PreparedStatement pstmt = DBConnector.connection.prepareStatement(query);
                pstmt.setString(1, cdTitleString);
                pstmt.setString(2, cdArtistString);
                pstmt.setString(3, cdReleaseYearString);
                pstmt.execute();
                pstmt.close();

                movingBackToHomeScreen();
            }
            catch (Exception databaseError) {
                JOptionPane.showMessageDialog(null, "Database Connection Error"+ databaseError,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void movingBackToHomeScreen() {

        if(DBConnector.connection != null) {
            try {
                //searches the database and find the highest value for book ID which will be the book
                // that was just added
                String query2 = ("SELECT CDID FROM cd WHERE CDID =(SELECT MAX(CDID) FROM cd)");
                ResultSet rs = DBConnector.stmt.executeQuery(query2);
                String cdID = rs.getString("CDID");
                rs.close();

                DBConnector.disconnect();
                JOptionPane.showMessageDialog(null, "CD Successfully Added\nCD ID: "
                        + cdID + "\nReturning to main menu", "Success", JOptionPane.INFORMATION_MESSAGE);
                backButtonActionPressed();

            } catch (Exception databaseError) {
                JOptionPane.showMessageDialog(null, "Database Connection Error" + databaseError,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }
}




