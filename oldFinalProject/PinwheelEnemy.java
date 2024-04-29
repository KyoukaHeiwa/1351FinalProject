package oldFinalProject;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Random;

import basicgraphics.BasicFrame;
import basicgraphics.CollisionEventType;
import basicgraphics.Sprite;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;
import basicgraphics.sounds.ReusableClip;

import java.awt.*;

public class PinwheelEnemy extends Enemy {
    public PinwheelEnemy(SpriteComponent sc, Picture sprite) {
        super(sc, sprite);
        setPicture(sprite);
        //setVelocity();
    }

    public static PinwheelEnemy createPinwheelEnemy(SpriteComponent sc) {
        Picture pinwheelSprite = createPinwheelSprite(30);
        PinwheelEnemy pEnemy = null;
        boolean positionValid = false;
        int attempts = 0;
    
        // Initialize Random inside the method to avoid issues with improper reseeding or shared instances
        Random localRand = new Random();
    
        while (!positionValid && attempts < 100) {
            int x = localRand.nextInt(myGame.BOARD_SIZE.width - myGame.SMALL);
            int y = localRand.nextInt(myGame.BOARD_SIZE.height - myGame.SMALL);
            double distance = Math.hypot(x - myGame.shooter.getX(), y - myGame.shooter.getY());
    
            if (distance >= myGame.MIN_DISTANCE_FROM_SHOOTER) {
                if (pEnemy == null) {
                    pEnemy = new PinwheelEnemy(sc, pinwheelSprite);
                }
                pEnemy.setX(x);
                pEnemy.setY(y);
                pEnemy.setVelocity();
                positionValid = true;
            }
    
            attempts++;
        }
    
        // Fallback to center if no valid position is found
        if (!positionValid) {
            if (pEnemy == null) {
                pEnemy = new PinwheelEnemy(sc, pinwheelSprite);
            }
            pEnemy.setX(myGame.BOARD_SIZE.width / 2);
            pEnemy.setY(myGame.BOARD_SIZE.height / 2);
            pEnemy.setVelocity();
        }
    
        return pEnemy;
    }
    
    private void setVelocity() {
        // Ensure a fresh random object or a properly randomized instance
        Random localRand = new Random();
    
        // Generate random velocity components, ensuring they vary adequately
        double speedFactor = 0.8; // Adjust the base speed factor if needed
        double vx = speedFactor * (localRand.nextDouble() * 2 - 1); // Random speed between -0.8 and 0.8
        double vy = speedFactor * (localRand.nextDouble() * 2 - 1); // Random speed between -0.8 and 0.8
    
        setVel(vx, vy);
    }

    public static Picture createPinwheelSprite(int size) {
        BufferedImage image = BasicFrame.createImage(size, size);
        Graphics2D g = (Graphics2D) image.getGraphics();
        int w = image.getWidth();
        int h = image.getHeight();
    
        int centerX = w / 2;
        int centerY = h / 2;
        int length = w / 2;
    
        g.setColor(new Color(210, 145, 255));
        g.setStroke(new BasicStroke(2));
    
        // Arrays to store endpoints of the main straight lines
        int[] endXs = new int[8];
        int[] endYs = new int[8];
    
        // Draw the initial set of straight lines (main lines)
        for (int i = 0; i < 8; i++) {  
            double angle = i * (Math.PI / 4); // 0, 45, 90, ... 315 degrees
            endXs[i] = centerX + (int) (length * Math.cos(angle));
            endYs[i] = centerY + (int) (length * Math.sin(angle));
            g.drawLine(centerX, centerY, endXs[i], endYs[i]);
        }
    
        // Draw additional diagonal lines connecting each main line to the next
        for (int i = 0; i < 8; i++) {
            // Calculate indices for connecting lines
            int nextIndex = (i + 1) % 8; // Circular index to wrap around
            if (i % 2 == 1) {  // Only add extensions at 45, 135, 225, 315 degrees
                g.drawLine(endXs[i], endYs[i], endXs[nextIndex], endYs[nextIndex]);
            }
        }
    
        return new Picture(image);
    }
    
    public void update() {

        // Check for collision with left or right boundary
        if (getX() < 0 || getX() + getWidth() > myGame.BOARD_SIZE.width) {
            setVel(-getVelX(), getVelY()); // Reverse x velocity
        }

        // Check for collision with top or bottom boundary
        if (getY() < 0 || getY() + getHeight() > myGame.BOARD_SIZE.height) {
            setVel(getVelX(),-getVelY()); // Reverse y velocity
        }
    }

    @Override
    public void processEvent(SpriteCollisionEvent ev) {
        SpriteComponent sc = getSpriteComponent();
        if (ev.xlo){
            // setX(sc.getSize().width-getWidth());
            // //shooterWallCollide.playOverlapping();
            // setVel(vx, vy);
            update();
            
        }
        if (ev.xhi) {
            // setX(0);
            // //shooterWallCollide.playOverlapping();
            // setVel(-vx, vy);
            update();

        }
        if (ev.ylo) {
            // setY(sc.getSize().height - getHeight());
            // //shooterWallCollide.playOverlapping();
            // setVel(vx, vy);
            update();

        }
        if (ev.yhi) {
            // setY(0);
            // //shooterWallCollide.playOverlapping();
            // setVel(vx, -vy);
            update();
        }
    }
}