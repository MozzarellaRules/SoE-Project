import javax.swing.*;
import java.awt.*;

public class Application extends JFrame {

    public Application() {
        initUI();
    }

    private void initUI() {
        add(new Board());

        setSize(350, 350);
        setTitle("My Game");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Application ex = new Application();
            ex.setVisible(true);
        });
    }
}
