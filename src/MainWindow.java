import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("Змейка");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(352, 375);
        setLocation(400, 400);
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
    }
}
