package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{

     GamePanel gp;
     KeyHandler keyH;

     public final int screenX;
     public final int screenY;

     public Player(GamePanel gp, KeyHandler keyH){
          this.gp = gp;
          this.keyH = keyH;

          // position of the player on the screen --> center, doesnt change
          screenX = gp.screenWidth/2 - (gp.tileSize/2); // adjustment for sprite size
          screenY = gp.screenHeight/2 - (gp.tileSize/2);

          collisionBox = new Rectangle(10, 16, 32, 32); // 8px to the right and 16 px down from top left of sprite

          setDefaultValues();
          getPlayerImage();
     }

     public void setDefaultValues(){
          // spawnpoint for character
          worldX = gp.tileSize*23;
          worldY = gp.tileSize*21;
          speed = 4;
          direction = "down";
     }

     public void getPlayerImage(){
          try{
               up1 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/boy_up_1.png")));
               up2 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/boy_up_2.png")));
               down1 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/boy_down_1.png")));
               down2 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/boy_down_2.png")));
               left1 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/boy_left_1.png")));
               left2 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/boy_left_2.png")));
               right1 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/boy_right_1.png")));
               right2 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/player/boy_right_2.png")));
          } catch (IOException e){
               e.printStackTrace();
          }
     }

     public void update(){
          boolean moving = false;
    
          if(keyH.upPressed){
               direction = "up";
               moving = true;
          } else if(keyH.downPressed){
               direction = "down";
               moving = true;
          } else if(keyH.leftPressed){
               direction = "left";
               moving = true;
          } else if(keyH.rightPressed){
               direction = "right";
               moving = true;
          }

          // Only move if a key is pressed
          if(moving) {
               // CHECK TILE COLLISION
               collision = false;
               gp.cChecker.checkTile(this);

               // IF COLLISION IS FALSE, PLAYER CAN MOVE
               if(!collision){
                    switch (direction){
                         case "up":
                              worldY -= speed;
                              break;
                         case "down":
                              worldY += speed;
                              break;
                         case "left":
                              worldX -= speed;
                              break;
                         case "right":
                              worldX += speed;
                              break;
                    }
               }
          }

          if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){
               spriteCounter++;
          }
          if(spriteCounter > 15){
               if(spriteNum == 1){
                    spriteNum = 2;
               } else if(spriteNum == 2){
                    spriteNum = 1;
               }
               spriteCounter = 0;
          }
     }

     public void draw(Graphics2D g2d){
          /*g2d.setColor(Color.WHITE);
          g2d.fillRect(x, y, gp.tileSize, gp.tileSize); // good practice to use variables and not fixed numbers*/
          BufferedImage image = null;

          switch (direction){
               case "up":
                    if(spriteNum == 1){
                         image = up1;
                    } else if(spriteNum == 2){
                         image = up2;
                    }
                    break;
               case "down":
                    if(spriteNum == 1){
                         image = down1;
                    } else if(spriteNum == 2){
                         image = down2;
                    }
                    break;
               case "left":
                    if(spriteNum == 1){
                         image = left1;
                    } else if(spriteNum == 2){
                         image = left2;
                    }
                    break;
               case "right":
                    if(spriteNum == 1){
                         image = right1;
                    } else if(spriteNum == 2){
                         image = right2;
                    }
                    break;
          }

          g2d.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null); // null means viewed from center of the screen
     }
}