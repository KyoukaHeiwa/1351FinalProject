package oldFinalProject;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Random;

import basicgraphics.BasicFrame;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;
import java.awt.*;
import oldFinalProject.Enemy; // Add the missing import statement for the Enemy class

public class RhombusEnemy extends Enemy {
    public RhombusEnemy(SpriteComponent sc, Picture sprite) {
        super(sc, sprite);
        setPicture(sprite);
    }

    public static Picture createRhombusSprite(int size) {
        BufferedImage image = BasicFrame.createImage(30,30);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        int w = image.getWidth();
        int h = image.getHeight();
        int[] x = {w / 2, w, w / 2, 0};
        int[] y = {0, h / 2, h, h / 2};
        g2.setColor(Color.CYAN);
        g2.fillPolygon(x, y, 4);
        
        int inside = size / 8;
        int[] innerX = {w /2 , w - inside, w / 2, inside};
        int[] innerY = {inside, h / 2, h - inside, h / 2};
        g2.setColor(Color.BLACK);
        g2.fillPolygon(innerX, innerY, 4);
        
        return new Picture(image);
    }

    public static RhombusEnemy createRhombusEnemy(SpriteComponent sc) {
        Picture rhombusSprite = createRhombusSprite(30); // Ensure this method exists and creates an appropriate sprite
        RhombusEnemy rEnemy = null;
        boolean positionValid = false;
        int attempts = 0;
    
        Random localRand = new Random(); // Local instance to ensure unique randomness
    
        while (!positionValid && attempts < 100) {
            int x = localRand.nextInt(myGame.BOARD_SIZE.width - myGame.SMALL);
            int y = localRand.nextInt(myGame.BOARD_SIZE.height - myGame.SMALL);
            double distance = Math.hypot(x - myGame.shooter.getX(), y - myGame.shooter.getY());
    
            if (distance >= myGame.MIN_DISTANCE_FROM_SHOOTER) {
                if (rEnemy == null) {
                    rEnemy = new RhombusEnemy(sc, rhombusSprite);
                }
                rEnemy.setX(x);
                rEnemy.setY(y);
                //rEnemy.setVelocity();  // Assuming RhombusEnemy has a setVelocity method similar to the other enemies
                positionValid = true;
            }
    
            attempts++;
        }
    
        if (!positionValid) {
            if (rEnemy == null) {
                rEnemy = new RhombusEnemy(sc, rhombusSprite);
            }
            rEnemy.setX(myGame.BOARD_SIZE.width / 2);
            rEnemy.setY(myGame.BOARD_SIZE.height / 2);
            //rEnemy.setVelocity();
        }
    
        return rEnemy;
    }

    @Override
    public void move(Dimension d) {
        super.move(d);
        chaseShooter();
        
    }
    private void chaseShooter() {
        Shooter shooter = myGame.getShooter(); 
        double sX = shooter.getCenterX();
        double sY = shooter.getCenterY();

        double angle = Math.atan2(sY - this.getCenterY(), sX - this.getCenterX());
        double enemySpeed = .65; //Enemey's speed
        
        this.setVel(enemySpeed * Math.cos(angle), enemySpeed * Math.sin(angle));
    }
}