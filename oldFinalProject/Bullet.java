/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oldFinalProject;

import basicgraphics.Sprite;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author sbrandt
 */
class Bullet extends Sprite {
    Bullet(SpriteComponent sc, Sprite sp, int direction) {
        super(sc);
        double angle = 0; // Default angle facing right
        if (direction == KeyEvent.VK_DOWN)
            angle = Math.PI / 2; // 90 degrees
        else if (direction == KeyEvent.VK_UP)
            angle = -Math.PI / 2; // -90 degrees
        else if (direction == KeyEvent.VK_LEFT)
            angle = Math.PI; // 180 degrees
        
        setPicture(makeTriangleBullet(angle));
        setCenterX(sp.centerX());
        setCenterY(sp.centerY());
        double velocity = 4.0 * myGame.bulletSpeedMultiplier;
        setVel(velocity * Math.cos(angle), velocity * Math.sin(angle));
    }

    Bullet(SpriteComponent sc, Shooter sp, int x, int y) {
        super(sc);
        double delx = x - sp.getX() - sp.getWidth() / 2;
        double dely = y - sp.getY() - sp.getHeight() / 2;
        double angle = Math.atan2(dely, delx);
        setPicture(makeTriangleBullet(angle));
        setX(sp.getX() + (myGame.BIG - myGame.SMALL) / 2);
        setY(sp.getY() + (myGame.BIG - myGame.SMALL) / 2);
        double dist = Math.sqrt(delx * delx + dely * dely);
        setVel(4 * delx / dist * myGame.bulletSpeedMultiplier, 4 * dely / dist * myGame.bulletSpeedMultiplier);
    }
    
    public boolean isOutOfGameArea() {
        // Replace GAME_AREA_WIDTH and GAME_AREA_HEIGHT with the actual dimensions of your game area
        return this.getX() < 0 || this.getX() > myGame.BOARD_SIZE.getWidth() || this.getY() < 0 || this.getY() > myGame.BOARD_SIZE.getHeight();
    }
    private Picture makeTriangleBullet(double angle) {
        int size = 20; // Size of the bullet
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
    
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
        // Define the coordinates for an isosceles triangle
        int[] xPoints = {size / 2, 5, size - 5};
        int[] yPoints = {5, size - 5, size - 5};
    
        // Create a new transform for rotation around the center of the image
        AffineTransform transform = new AffineTransform();
        transform.rotate(angle, size / 2, size / 2);
        g.setTransform(transform);
    
        // Draw the outer triangle (yellow)
        g.setColor(Color.YELLOW);
        g.fillPolygon(xPoints, yPoints, 3);
    
        // Draw the inner triangle (black) for visual effect
        int shrink = 3; // margin from the base of the triangle
        int[] xInnerPoints = {size / 2, 5 + shrink, size - 5 - shrink};
        int[] yInnerPoints = {5 + shrink, size - 5, size - 5};
        g.setColor(Color.BLACK);
        g.fillPolygon(xInnerPoints, yInnerPoints, 3);
    
        return new Picture(image);
    }
    
    
}
