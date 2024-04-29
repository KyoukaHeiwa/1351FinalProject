// package oldFinalProject;

// import basicgraphics.*;
// import basicgraphics.images.Picture;

// import javax.swing.*;

// import java.awt.BasicStroke;
// import java.awt.Color;
// import java.awt.Graphics2D;
// import java.awt.image.BufferedImage;

// public class EnemyCreator {
//     public static Picture createRhombusSprite(int size) {
//         BufferedImage image = BasicFrame.createImage(30,30);
//         Graphics2D g2 = (Graphics2D) image.getGraphics();
//         int w = image.getWidth();
//         int h = image.getHeight();
//         int[] x = {w / 2, w, w / 2, 0};
//         int[] y = {0, h / 2, h, h / 2};
//         g2.setColor(Color.CYAN);
//         g2.fillPolygon(x, y, 4);
        
//         int inside = size / 8;
//         int[] innerX = {w /2 , w - inside, w / 2, inside};
//         int[] innerY = {inside, h / 2, h - inside, h / 2};
//         g2.setColor(Color.WHITE);
//         g2.fillPolygon(innerX, innerY, 4);
        
//         return new Picture(image);
//     }

//     public static Enemy createRhombusEnemy(SpriteComponent sc) {
//         Picture rhombusSprite = createRhombusSprite(40);
//         return new Enemy(sc, rhombusSprite);
//     }

//     public static Picture createPinwheelSprite(int size) {
//         BufferedImage image = BasicFrame.createImage(30, 30);
//         Graphics2D g2 = (Graphics2D) image.getGraphics();
//         int w = image.getWidth();
//         int h = image.getHeight();

//         int[] x = new int[3];
//         int[] y = new int[3];
//         int centerX = w / 2;
//         int centerY = h / 2;
//         int length = w / 2;
        
//         g2.setColor(new Color(210, 145, 255));
//         g2.setStroke(new BasicStroke(3));
        
//         for (int i = 0; i < 4; i++) {  
//             double angle = Math.PI / 4 + i * (Math.PI / 2); 
//             int endX = centerX + (int) (length * Math.cos(angle));
//             int endY = centerY + (int) (length * Math.sin(angle));
//             g2.drawLine(centerX, centerY, endX, endY);
//         }
        
       
//         return new Picture(image);
//     }

//     public static Enemy createPinwheelEnemy(SpriteComponent sc) {
//         Picture pinwheelSprite = createPinwheelSprite(30);
//         return new Enemy(sc, pinwheelSprite);
//     }
// }
