package oldFinalProject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import basicgraphics.BasicFrame;
import basicgraphics.Sprite;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;
import basicgraphics.sounds.ReusableClip;

public class Shooter extends Sprite {
    private double velX = 0;
    private int lives = 3;
    private double velY = 0;
    static final double SPEED = 1.0;
    static double movementSpeed = 1;
    private List<Life> lifeSprites = new ArrayList<>();

    //final static ReusableClip shooterWallCollide = new ReusableClip("Ship_hitwall.wav");
    
    @Override
    public void processEvent(SpriteCollisionEvent ev) {
        SpriteComponent sc = getSpriteComponent();
        if (ev.xlo){
            setX(sc.getSize().width-getWidth());
            //shooterWallCollide.playOverlapping();
        }
        if (ev.xhi) {
            setX(0);
            //shooterWallCollide.playOverlapping();

        }
        if (ev.ylo) {
            setY(sc.getSize().height - getHeight());
            //shooterWallCollide.playOverlapping();

        }
        if (ev.yhi) {
            setY(0);
            //shooterWallCollide.playOverlapping();

        }
    }
    public Shooter(SpriteComponent sc) {
        super(sc);
        setPicture(createDiamondSprite(30));
        setX(myGame.BOARD_SIZE.width / 2);
        setY(myGame.BOARD_SIZE.height / 2);
    }
    public static Picture createDiamondSprite(int size) {
        BufferedImage image = BasicFrame.createImage(30,30);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        int w = image.getWidth();
        int h = image.getHeight();
        int[] x = {w / 2, w, w / 2, 0};
        int[] y = {0, h / 2, h, h / 2};
        g2.setColor(Color.WHITE);
        g2.fillPolygon(x, y, 4);
        
        int inside = size / 8;
        int[] innerX = {w /2 , w - inside, w / 2, inside};
        int[] innerY = {inside, h / 2, h - inside, h / 2};
        g2.setColor(Color.BLACK);
        g2.fillPolygon(innerX, innerY, 4);
        
        return new Picture(image);
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }
    public double getMovementSpeed() {
        return movementSpeed;
    }
    
    // Call this method to update the position based on velocity
    public void move() {
        double newX = getX() + velX;
        double newY =  getY() + velY;
        if (newX < 0 || newX > getSpriteComponent().getSize().width - getWidth()) {
            newX = getX();
        }
        if (newY < 0 || newY > getSpriteComponent().getSize().height - getHeight()) {
            newY = getY(); 
        }
    
    
        setX(newX * SPEED);
        setY(newY * SPEED); 
    }
    public void lifeLost() {
        lives--;
    }
    public int getLives(){
        return lives;
    }
    
}