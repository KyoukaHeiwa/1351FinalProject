package oldFinalProject;

import basicgraphics.BasicFrame;
import basicgraphics.Sprite;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Stars extends Sprite {
    Picture star;
    public Random rand = new Random();
    
    public Color getRandomColor(){
        int[] rgb = new int[3];
        rgb[0] = 255; // Full intensity
        rgb[1] = rand.nextInt(128); // Half intensity or less
        rgb[2] = rand.nextInt(128); // Half intensity or less
    
        // Randomly shuffle the RGB components
        for (int i = 0; i < 3; i++) {
            int j = rand.nextInt(3);
            int tmp = rgb[i];
            rgb[i] = rgb[j];
            rgb[j] = tmp;
        }
    
        return new Color(rgb[0], rgb[1], rgb[2]);
    }
    public Stars(SpriteComponent sc) {
        super(sc);
        BufferedImage image = BasicFrame.createImage(30, 30);
        Graphics2D g = (Graphics2D) image.getGraphics();
        int w = image.getWidth();
        int h = image.getHeight();

        int centerX = w / 2;
        int centerY = h / 2;
        int length = w / 2;
        
        Color randColor = getRandomColor();
        g.setColor(randColor);
        g.setStroke(new BasicStroke(3));
        
        for (int i = 0; i < 8; i++) {  
            double angle = Math.PI / 4 * i; 
            int endX = centerX + (int) (length * Math.cos(angle));
            int endY = centerY + (int) (length * Math.sin(angle));
            g.drawLine(centerX, centerY, endX, endY); 
        }
        star = new Picture(image);
        setPicture(star);
        setX(rand.nextInt(myGame.BOARD_SIZE.width) - 30);
        setY(rand.nextInt(myGame.BOARD_SIZE.height) - 30);
        setVelocity();
    }

    // public static Stars createStars(SpriteComponent sc) {
    //     Picture pinwheelSprite = createPinwheelSprite(40);
    //     Stars pEnemey;
    //     do {
    //         pEnemey = new Stars(sc, pinwheelSprite);
    //     } while (Math.hypot(pEnemey.getX() - myGame.shooter.getX(), pEnemey.getY() - myGame.shooter.getY()) < myGame.MIN_DISTANCE_FROM_SHOOTER);
    //     return pEnemey;
    // }
    
    private void setVelocity() {
        double angle = 2 * Math.PI * myGame.RAND.nextDouble(); // Random angle in radians
        double speed = 1; // Constant speed
        double vx = speed * Math.cos(angle);
        double vy = speed * Math.sin(angle);
        setVel(vx, vy);
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