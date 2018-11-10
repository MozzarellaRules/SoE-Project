import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements Runnable {
    // Board size
    private final int B_WIDTH = 350;
    private final int B_HEIGHT = 350;

    // The time an iteration must take place -- used in run()
    private final int DELAY = 25;

    private Pirate pirate;
    private Thread animator;

    private boolean running = true;

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
        g.drawImage(pirate.getImage(), pirate.getX(), pirate.getY(), this);

        //Toolkit.getDefaultToolkit().sync(); // Da vedere meglio
    }

    private void cycle() {

    }

    @Override
    public void run() {
        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (true) {
            cycle();
            repaint();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                String msg = String.format("Thread interrupted: %s", e.getMessage());
                JOptionPane.showMessageDialog(this, msg, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            beforeTime = System.currentTimeMillis();
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
                if (running) {

                    pirate.keyPressed(e);
                    pirate.move();
                }
            } else if (key == KeyEvent.VK_UP) {

            }

            /*
            Possible shot
            int x = pirate.getX();
            int y = pirate.getY();
            if (key == KeyEvent.VK_SPACE) {
                if (running) {
                    if (!pirate.isVisible()) {
                        pira = new Shot(x, y);
                    }
                }
            }
            */
        }
    }
}
