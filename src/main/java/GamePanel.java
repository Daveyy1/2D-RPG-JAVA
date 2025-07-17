import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    // SCREEN SETTINGS
    final int ORIGINALTILESIZE = 16; // 16x16 pixel tiles
    final int SCALE = 3; // scales the tiles by 3 bc 16x16 is very small on modern screens

    final int tileSize = ORIGINALTILESIZE * SCALE; // 48x48 tiles
    final int maxScreenCol = 40; // 16 tiles wide
    final int maxScreenRow = 22; // 12 tiles high, 16:9 ratio on screen
    final int screenWidth = tileSize * maxScreenCol; // 1920 px
    final int screenHeight = tileSize * maxScreenRow; // 1080 px

    Thread gameThread; // main gameThread to keep the program running, needed for drawing the screen at a certain fps


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);  // all drawing from this component (Panel) will be done offscreen in a painting buffer -> improves performance
    }

    public void startGameThread(){
        gameThread = new Thread(this); // passes this game panel into the method
        gameThread.start();
    }

    @Override
    public void run() {

    }
}
