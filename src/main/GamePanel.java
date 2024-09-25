package main;

import main.entity.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.rmi.Remote;

public class GamePanel extends JPanel implements Runnable{

    //SCREEN SETTINGS
    final int originalTileSize = 16; //16x16 tiles
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; //48
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;

    final int screenWidth = tileSize * maxScreenCol; //768
    final int screenHeight = tileSize * maxScreenRow; //576


    //FPS
    int fps = 60;


    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyH); //this = gamepanelclass Instance of player

    //Set player pos
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);//gamepanel can receive input
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/fps; //allocated time for every loop
        double nextDrawTime = System.nanoTime() + drawInterval; //calculate the time before the next update


        while (gameThread != null) {

            // Update infos and pos
            update();
            //draw the screen
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000; //convert to milli

                if (remainingTime < 0 ) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime); //wait the remaining time

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void update() {
        player.update();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g); //parentClass = Jpanel

        Graphics2D g2 = (Graphics2D)g;//change graphics to graphics 2D class (more functions)

        player.draw(g2);

        g2.dispose();
    }
}
