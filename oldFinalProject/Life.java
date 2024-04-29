package oldFinalProject;

import basicgraphics.Sprite;
import basicgraphics.SpriteComponent;
import basicgraphics.examples.BasicGraphics;
import basicgraphics.images.Picture;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import basicgraphics.BasicFrame;

public class Life extends Sprite {
    public Life(SpriteComponent sc) {
        super(sc);
        setPicture(Shooter.createDiamondSprite(30));
    }
}