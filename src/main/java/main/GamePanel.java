package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    // SCREEN SETTINGS
    final int ORIGINALTILESIZE = 16; // 16x16 pixel tiles
    final int SCALE = 3; // scales the tiles by 3 bc 16x16 is very small on modern screens

    public final int tileSize = ORIGINALTILESIZE * SCALE; // 48x48 tiles
    public final int maxScreenCol = 40; // 16 tiles wide
    public final int maxScreenRow = 22; // 12 tiles high, 16:9 ratio on screen
    public final int screenWidth = tileSize * maxScreenCol; // 1920 px
    public final int screenHeight = tileSize * maxScreenRow; // 1080 px

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // FPS
    int fps = 60;

    TileManager tm = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread; // main gameThread to keep the program running, needed for drawing the screen at a certain fps
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Player player = new Player(this, keyH);



    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);  // all drawing from this component (Panel) will be done offscreen in a painting buffer -> improves performance
        this.addKeyListener(keyH);
        this.setFocusable(true); // when running, focuses on this window
    }

    public void startGameThread(){
        gameThread = new Thread(this); // passes this game panel into the method
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / fps; // the large number is nanoseconds which equal 1 second, so 1s / 60
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null){

            // using Delta game loop as it is more unified and consistent over time, especially when you start adding
            // more complex functions that may take longer to render, Thread.sleep() is not completely accurate in comparison

            currentTime = System.nanoTime(); // could use milliseconds but nano is more precise

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                // 1. Update character information
                update();
                // 2. repaint the frame with new info
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update(){
        player.update();
    }

    // Graphics is a class used to draw things on the screen
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        tm.draw(g2d); // first tiles, then player because of layering. If vice versa, player would be hidden

        player.draw(g2d);

        g2d.dispose(); // good practise to save memory after usage
    }
}
