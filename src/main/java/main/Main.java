package main;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args){

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("David's 2D Adventure");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        // Get all available monitors
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = ge.getScreenDevices();
        
        // Choose which monitor to use (0 = first monitor, 1 = second monitor, etc.)
        int monitorIndex = 0; // Change this to 0 for first monitor, 1 for second, etc.
        
        if (monitorIndex < devices.length) {
            GraphicsDevice targetDevice = devices[monitorIndex];
            
            // Get the bounds of the target monitor
            Rectangle bounds = targetDevice.getDefaultConfiguration().getBounds();
            
            // Position the window on the target monitor
            window.setLocation(bounds.x, bounds.y);
            window.pack();
            
            // Set to fullscreen on the target monitor
            window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {
            // Fallback if monitor index is invalid
            window.setLocationRelativeTo(null);
            window.pack();
            window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        
        window.setVisible(true);
        gamePanel.startGameThread();
    }
}