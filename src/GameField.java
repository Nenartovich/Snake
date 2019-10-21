import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 4000000;
    private Image dot;
    private Image apple;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;
    private int score = 0;
    private boolean madeStep = false;

    public GameField() {
        setBackground(Color.black);
        loadImages();
        InitGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void InitGame() {
        dots = 3*16;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i;
            y[i] = 48;
        }
        timer = new Timer(10, this);
        timer.start();
        createApple();
    }

    public void createApple() {
        boolean fl = false;
        while (fl == false) {
            fl = true;
            appleX = new Random().nextInt(20) * DOT_SIZE;
            appleY = new Random().nextInt(20) * DOT_SIZE;
            for (int i = 0; i < dots; i++) {
                if (x[i] == appleX && y[i] == appleY) {
                    fl = false;
                }
            }
        }
    }

    public void loadImages() {
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iib = new ImageIcon("dot.png");
        dot = iib.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
            String str = "Score: " + score;
            g.setColor(Color.white);
            g.drawString(str, 10, 10);
        } else {
            String str = "Game Over!";
            g.setColor(Color.white);
            g.drawString(str, 150, SIZE/2);
            g.drawString("Score: "+ score,160, SIZE / 2 + 20);

        }
    }

    public void move() {
        if (left) {
            x[0] --;
        } else if (right) {
            x[0]++;
        } else if (up) {
            y[0]--;
        } else if (down) {
            y[0]++;
        }

        for (int i = dots; i > 0; i--) {
          x[i] = x[i-1];
          y[i] = y[i-1];
        }


        madeStep = true;
    }

    public void checkApple() {
        if ((x[0] <= appleX+8) && (x[0] + 16 >= appleX+8) && (y[0] <= appleY+8) && (y[0]+16 >= appleY+8)) {
            dots += 16;
            for (int i = dots - 16; i <= dots; i++) {
                x[i] = x[i-1];
                y[i] = y[i-1];
            }
            score++;
            createApple();
        }
    }

    public void checkCollisions() {
        if (x[0] > SIZE) {
            x[0] = 0;
        } else if (x[0] < 0) {
            x[0] = SIZE;
        } else if (y[0] > SIZE) {
            y[0] = 0;
        } else if (y[0] < 0) {
            y[0] = SIZE;
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (inGame) {
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !right && madeStep) {
                left = true;
                up = false;
                down = false;
                madeStep = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left && madeStep) {
                right = true;
                up = false;
                down = false;
                madeStep = false;
            }
            if (key == KeyEvent.VK_UP && !down && madeStep) {
                up = true;
                left = false;
                right = false;
                madeStep = false;
            }
            if (key == KeyEvent.VK_DOWN && !up && madeStep) {
                down = true;
                left = false;
                right = false;
                madeStep = false;
            }
        }
    }

}
