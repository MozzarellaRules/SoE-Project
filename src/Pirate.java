import javax.swing.*;
import java.awt.event.KeyEvent;

public class Pirate extends Sprite {

    private int START_X = 350/2 - 32; // Middle of the window
    private int START_Y = 350 - 100;
    private int dx;
    private int dy;

    private boolean canJump = true;
    private boolean isJumping = false;
    private boolean isFalling = false;
    private int currentJumpCycle = 0;
    private int maxJumpCycles = 25;

    private int widthImage;
    private int heightImage;

    public Pirate() {
        initPirate();
    }

    private void initPirate() {
        ImageIcon ii = new ImageIcon("resources/pirate/Pirate Captain (Idle) GIF.gif");
        this.image = ii.getImage();
        this.widthImage = ii.getImage().getWidth(null);
        this.heightImage = ii.getImage().getHeight(null);

        this.x = START_X;
        this.y = START_Y;
    }

    public boolean isJumping() { return isJumping; }
    public boolean isFalling() { return isFalling; }
    public int getWidthImage() { return widthImage; }
    public int getHeightImage() { return heightImage; }

    public void move() {
        x += dx;
    }

    public void jump() {
        if (canJump) {
            if (isJumping) {
                currentJumpCycle += 1;
                dy = 4; // Four pixel per thread cycle
                y -= dy; // Set new coordinates

                // Max height reached, now fall
                if (currentJumpCycle == maxJumpCycles) {
                    isJumping = false;
                    isFalling = true;
                }
            }
        }
    }

    public void fall() {
        if (isFalling) {
            currentJumpCycle -= 1;
            dy = 4; // Four pixel per thread cycle
            y += dy; // Set new coordinates

            // Stop falling if currentJumpCycle == 0
            if (currentJumpCycle == 0) {
                dy = 0;
                isFalling = false;
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -4;
        }
        if (key == KeyEvent.VK_RIGHT) {
            dx = 4;
        }
        if (key == KeyEvent.VK_UP) {
            if (!isFalling) // No jump if falling
                isJumping = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }
        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
        if (key == KeyEvent.VK_UP) { }
    }

}
