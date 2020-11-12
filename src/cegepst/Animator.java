package cegepst;

import cegepst.engine.entities.MovableEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Animator {

    private static final int IDLE_FRAME = 0;
    private final String spriteSheetPath;
    private final MovableEntity entity;
    private final int x = 0;
    private final int y = 0;

    private BufferedImage spriteSheet;

    private Image[] frames;
    private final int nbAnimationFrames;
    private int currentAnimationFrame = IDLE_FRAME;
    private int animationSpeed = 8;
    private int nextFrame = animationSpeed;

    public Animator(MovableEntity entity, String spriteSheetPath, int nbAnimationFrames) {
        this.entity = entity;
        this.spriteSheetPath = spriteSheetPath;
        this.nbAnimationFrames = nbAnimationFrames;
        loadSpriteSheet();
        loadFrames();
    }

    public void update() {
        if (entity.hasMoved()) {
            --nextFrame;
            if (nextFrame == 0) {
                ++currentAnimationFrame;
                if (currentAnimationFrame >= frames.length) {
                    currentAnimationFrame = 0;
                }
                nextFrame = animationSpeed;
            }
        } else {
            currentAnimationFrame = IDLE_FRAME; //return to idle frame
        }
    }

    public Image animate() {
        return frames[currentAnimationFrame];
    }

    public void setAnimationSpeed(int speed) {
        animationSpeed = speed;
    }

    private void loadFrames() {
        frames = new Image[nbAnimationFrames];
        for (int i = 0; i < nbAnimationFrames; i++) {
            frames[i] = spriteSheet.getSubimage( x + (i * 32), y, entity.getWidth(), entity.getHeight());
        }
    }

    private void loadSpriteSheet() {
        try {
            spriteSheet = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(spriteSheetPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
