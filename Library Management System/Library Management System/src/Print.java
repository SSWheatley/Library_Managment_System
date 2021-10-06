import javax.swing.*;

public class Print {
    public static JFrame baseFrame = mainWindow.mainWindow;
    private static JPanel printPanel = new JPanel();
    private static JLabel itemIDLabel = new JLabel();
    private static JTextField itemIDTextField = new JTextField();


    public static void print(String userPrivilege){
        baseFrame.setTitle("Print Item");
        baseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        printPanel.setSize(1000,1000);
        printPanel.setLayout(null);
        printPanel.setBackground(GUI.colorSelector.getBackground());

        itemIDLabel.setText("Item ID");
        itemIDLabel.setSize(150,50);
        itemIDLabel.setLocation(100,100);
        itemIDLabel.setBackground(GUI.colorSelector("Frost4"));

        itemIDTextField.setSize(150, 50);
        itemIDTextField.setLocation(250,100);
        itemIDTextField.setBackground(GUI.colorSelector("Frost3"));

        printPanel.add(itemIDLabel);
        printPanel.add(itemIDTextField);

        JOptionPane.showMessageDialog(null, "incomplete page",
                "Error", JOptionPane.ERROR_MESSAGE);

        Homepage.homepageChecker(Homepage.userPrivilege);

    }
}



