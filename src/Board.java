import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements Runnable {

    private final int B_WIDTH = 350;
    private final int B_HEIGHT = 350;
    private boolean isRunning = true;

    // The time an iteration must take place -- used in run()
    private final int DELAY = 20;

    private Pirate pirate;
    private Thread animator;

    public Board() {
        initBoard();
    }

    private void initBoard() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        setFocusable(true);

        addKeyListener(new TAdapter());

        pirate = new Pirate();
    }

    @Override
    public void addNotify() {
        super.addNotify();

        animator = new Thread(this);
        animator.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPirate(g);
    }

    private void drawPirate(Graphics g) {
        int width = pirate.getWidthImage() + 12; // Makes the pirate bigger
        int height = pirate.getHeightImage() + 12;
        g.drawImage(pirate.getImage(), pirate.getX(), pirate.getY(), width, height, this);
    }

    private void cycle() {
        if (pirate.isJumping())
            pirate.jump();
        if (pirate.isFalling())
            pirate.fall();
    }

    @Override
    public void run() {
        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis(); // Current time in milliseconds

        while (true) {
            // ******
            cycle();
            repaint();
            // These two methods might take different time at various while cycles
            // ******
            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;
            if (sleep < 0) {
                sleep = 2;
            }
            // Each while cycle should last >>DELAY<< ms
            // To ensure that each while cycle runs at constant time we must sleep the thread
            // ******

            try {
                Thread.sleep(sleep); // Sleep the thread
            } catch (InterruptedException e) {
                // Show an error dialog
                String msg = String.format("Thread interrupted: %s", e.getMessage());
                JOptionPane.showMessageDialog(this, msg, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            beforeTime = System.currentTimeMillis(); // Current time in milliseconds
        }
    }


    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            pirate.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
                if (isRunning) {
                    pirate.keyPressed(e);
                    pirate.move();
                }
            } else if (key == KeyEvent.VK_UP) {
                if (isRunning) {
                    pirate.keyPressed(e);
                    pirate.jump();
                }
            }

            /*
            Possible shot
            if (key == KeyEvent.VK_SPACE) {
                if (isRunning) {
                    if (!pirate.isVisible()) {
                        pira = new Shot(x, y);
                    }
                }
            }
            */
        }
    }
}
