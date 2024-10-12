package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    //SCREEN SETTINGS
    final int originalTileSize = 32; //32x32 tiles
    final int scale = 2;

    public final int tileSize = originalTileSize * scale; //64
    public final int maxScreenCol = 12;
    public final int maxScreenRow = 8;

    public final int screenWidth = tileSize * maxScreenCol; //768
    public final int screenHeight = tileSize * maxScreenRow; //576


    //WORLD MAP PARAMETERS
    public final int maxWorldCol = 60;
    public final int maxWorldRow = 32;
    //public final int worldWidth = tileSize * maxWorldCol;
    //public final int worldHeight = tileSize * maxWorldRow; -never used for now

    //FPS
    int fps = 60;

    //Instancier les elements :

    //SYS
    TileManager tileM = new TileManager(this);
    MapGenerator mapGenerator = new MapGenerator();
    KeyHandler keyH = new KeyHandler();
    Sound soundEffects = new Sound();
    Sound music = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    Thread gameThread;

    //ENTITY OBJ
    public Player player = new Player(this, keyH); //this = gamepanelclass Instance of player
    public SuperObject obj[] = new SuperObject[10]; //10 slots of objects = display 10 objects at the same time

    public UI ui = new UI(this, player);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.darkGray);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);//gamepanel can receive input
    }

    public void setupGame() {
        mapGenerator.addProtectedTile(40, 16);
        mapGenerator.generateMap();  // Générer la nouvelle carte
        mapGenerator.saveMapToFile("src/resources/maps/map.txt");  // Sauvegarder la carte générée
        mapGenerator.displayMap();  // Afficher la carte dans la console (facultatif)

        tileM.loadMap("src/resources/maps/map.txt");

        aSetter.setObjects();  // Initialiser les objets dans le jeu
        //playMusic(0);  // Jouer la musique de fond
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

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
        //System.out.println("x = " + player.x);
        //System.out.println("Y = " + player.y);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g); //parentClass = Jpanel

        Graphics2D g2 = (Graphics2D)g;//change graphics to graphics 2D class (more functions)

        //debug
        long drawStart = 0;
        if(keyH.debugMode == true){
            drawStart = System.nanoTime();
        }

        //TILES
        tileM.draw(g2); //first layer

        //objects
        for(int i = 0; i < obj.length; i++) {
            if(obj[i] != null){
                obj[i].draw(g2, this);
            }
        }
        //PLAYER
        player.draw(g2);

        //UI
        ui.draw(g2);

        //DEBUG
        if(keyH.debugMode == true){
            long drawEnd =System.nanoTime();
            long passed = drawEnd - drawStart;
            System.out.println("Draw Time :" + passed);
        }

        g2.dispose();
    }

    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();

    }
    public void stopMusic(){
        music.stop();
    }

    public void playSE(int i){
        soundEffects.setFile(i);
        soundEffects.play();
    }
}
