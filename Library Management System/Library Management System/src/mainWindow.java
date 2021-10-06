import javax.swing.*;

//this class is for the main window in which everything can be placed


public class mainWindow {
    public static JFrame mainWindow = new JFrame();
    public static void main(){
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(1000, 1000);
        mainWindow.setResizable(false);
        //baseFrame.setVisible(true);
        mainWindow.setLocation(100, 100);

    }

}
