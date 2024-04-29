package oldFinalProject;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import basicgraphics.BasicFrame;
import basicgraphics.CollisionEventType;
import basicgraphics.Sprite;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;
import basicgraphics.sounds.ReusableClip;
import java.awt.geom.AffineTransform;

import java.awt.*;

public class WeaverEnemy extends Enemy {
    private static final int DODGE_DISTANCE = 50; // The distance at which the enemy starts to dodge bullets

    public WeaverEnemy(SpriteComponent sc, Picture sprite) {
        super(sc, sprite);
        setPicture(sprite);
    }

    
    // public static Picture createWeaverSprite(int size) {
    //     BufferedImage image = BasicFrame.createImage(30, 30);
    //     Graphics2D g2 = (Graphics2D) image.getGraphics();


    //     g2.setColor(Color.BLUE);
    //     g2.fillRect(0, 0, size, size);

    //     g2.setColor(Color.GREEN);
    //     g2.fillRect(0, 0, size, size);

    //     int halfSize = 30 / 2;
    //     int quarterSize = 30 / 4;
    //     int threeQuarterSize = 3 * 30 / 4;

    //     g2.setColor(Color.BLACK);
    //     int[] xPoints = {halfSize, quarterSize, halfSize, threeQuarterSize};
    //     int[] yPoints = {quarterSize, halfSize, threeQuarterSize, halfSize};
    //     g2.fillPolygon(xPoints, yPoints, 4);

    //     return new Picture(image);
    // }
    public static Picture createWeaverSprite(int size) {
        BufferedImage image = BasicFrame.createImage(size, size);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
    
        g2.setColor(Color.GREEN);
        g2.fillRect(0, 0, size, size); // Fill the entire image with green
    
        int outerSize = (int) Math.round(size / Math.sqrt(2)); // Calculate the side length of the outer box
        int outerOffset = (size - outerSize) / 2; // Calculate the offset to center the outer box
    
        g2.setColor(Color.BLACK);
        g2.fillRect(outerOffset, outerOffset, outerSize, outerSize); // Draw the outer box
    
        int middleSize = (int) Math.round(Math.sqrt(2) * outerSize / 2); // Calculate the diagonal of the outer box
        int middleOffset = (size - middleSize) / 2; // Calculate the offset to center the middle box
    
        g2.setColor(Color.GREEN);
        g2.rotate(Math.toRadians(45), size / 2, size / 2); // Rotate the graphics context by 45 degrees around the center of the image
        g2.fillRect(middleOffset, middleOffset, middleSize, middleSize); // Draw the middle box
    
        int innerSize = (int) Math.round(Math.sqrt(2) * middleSize / 2); // Calculate the diagonal of the middle box
        int innerOffset = (size - innerSize) / 2; // Calculate the offset to center the inner box
    
        g2.setColor(Color.BLACK);
        g2.fillRect(innerOffset, innerOffset, innerSize, innerSize); // Draw the inner box
    
        return new Picture(image);
    }
    

    
    // public static WeaverEnemy createWeaverEnemy(SpriteComponent sc) {
    //     Picture weaverSprite = createWeaverSprite(30);
    //     WeaverEnemy wEnemy;
    //     do {
    //         wEnemy = new WeaverEnemy(sc, weaverSprite);
    //         wEnemy.setX(myGame.RAND.nextInt(myGame.BOARD_SIZE.width)-myGame.SMALL);
    //         wEnemy.setY(myGame.RAND.nextInt(myGame.BOARD_SIZE.height)-myGame.SMALL);
    //     } while (Math.hypot(wEnemy.getX() - myGame.shooter.getX(), wEnemy.getY() - myGame.shooter.getY()) < myGame.MIN_DISTANCE_FROM_SHOOTER);
    //     return wEnemy;
    // }
    public static WeaverEnemy createWeaverEnemy(SpriteComponent sc) {
        Picture weaverSprite = createWeaverSprite(25);
        WeaverEnemy wEnemy = null;
        boolean positionValid = false;
        int attempts = 0;
    
        Random localRand = new Random(); // Local instance to ensure unique randomness
    
        while (!positionValid && attempts < 100) {
            int x = localRand.nextInt(myGame.BOARD_SIZE.width - myGame.SMALL);
            int y = localRand.nextInt(myGame.BOARD_SIZE.height - myGame.SMALL);
            double distance = Math.hypot(x - myGame.shooter.getX(), y - myGame.shooter.getY());
    
            if (distance >= myGame.MIN_DISTANCE_FROM_SHOOTER) {
                if (wEnemy == null) {
                    wEnemy = new WeaverEnemy(sc, weaverSprite);
                }
                wEnemy.setX(x);
                wEnemy.setY(y);
                //wEnemy.setVelocity();  // Assuming WeaverEnemy has a setVelocity method
                positionValid = true;
            }
    
            attempts++;
        }
    
        if (!positionValid) {
            if (wEnemy == null) {
                wEnemy = new WeaverEnemy(sc, weaverSprite);
            }
            wEnemy.setX(myGame.BOARD_SIZE.width / 2);
            wEnemy.setY(myGame.BOARD_SIZE.height / 2);
            //wEnemy.setVelocity();
        }
    
        return wEnemy;
    }
    @Override
    public void move(Dimension d) {
        super.move(d);
        chaseShooter();
        dodgeBullets();
    }
    private void chaseShooter() {
        Shooter shooter = myGame.getShooter();
        double sX = shooter.getCenterX();
        double sY = shooter.getCenterY();

        double angle = Math.atan2(sY - this.getCenterY(), sX - this.getCenterX());
        double enemySpeed = .8; //This is the speed of the enemy

        this.setVel(enemySpeed * Math.cos(angle), enemySpeed * Math.sin(angle));
    }

    /**
     * This method makes the enemy dodge bullets by detecting if the bullet is within a certain distance.
     */
    private void dodgeBullets() {
        List<Bullet> bullets = new ArrayList<>(myGame.getBullets());
        for (Bullet bullet : bullets) {
            if (bullet != null) {
                double distance = Math.hypot(this.getX() - bullet.getX(), this.getY() - bullet.getY());
                if (distance < DODGE_DISTANCE) {
                    double dodgeX = this.getX() - bullet.getX();
                    double dodgeY = this.getY() - bullet.getY();
                    this.setX(this.getX() + dodgeX / distance);
                    this.setY(this.getY() + dodgeY / distance);   
                }
            }
        }
    }
}

