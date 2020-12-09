package cegepst;

import cegepst.engine.controls.Direction;
import cegepst.engine.entities.MovableEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class WalkingAnimator {


    private static final int IDLE_FRAME = 1;
    private final String spriteSheetPath;
    private final MovableEntity entity;
    private final int x;
    private final int y;

    private BufferedImage spriteSheet;

    private Image[] upFrames;
    private Image[] downFrames;
    private Image[] leftFrames;
    private Image[] rightFrames;
    private int currentAnimationFrame = IDLE_FRAME;
    private int animationSpeed = 8;
    private int nextFrame = animationSpeed;

    public WalkingAnimator(MovableEntity entity, String spriteSheetPath, int xInSpriteSheet, int yInSpriteSheet) {
        this.entity = entity;
        this.spriteSheetPath = spriteSheetPath;
        this.x = xInSpriteSheet;
        this.y = yInSpriteSheet;
        loadSpriteSheet();
        loadFrames();
    }

    public void setAnimationSpeed(int speed) {
        animationSpeed = speed;
    }

    public void update() {
        if (entity.hasMoved()) {
            --nextFrame;
            if (nextFrame == 0) {
                ++currentAnimationFrame;
                if (currentAnimationFrame >= leftFrames.length) {
                    currentAnimationFrame = 0;
                }
                nextFrame = animationSpeed;
            }
        } else {
            currentAnimationFrame = IDLE_FRAME; //return to idle frame
        }
    }

    public Image animate(Direction direction) {
        if (direction == Direction.UP) {
            return animateUp();
        } else if (direction == Direction.DOWN) {
            return animateDown();
        } else if (direction == Direction.LEFT) {
            return animateLeft();
        } else if (direction == Direction.RIGHT) {
            return animateRight();
        }
        return animateUp();
    }

    public Image animateUp() {
        return upFrames[currentAnimationFrame];
    }

    public Image animateDown() {
        return downFrames[currentAnimationFrame];
    }

    public Image animateRight() {
        return rightFrames[currentAnimationFrame];
    }

    public Image animateLeft() {
        return leftFrames[currentAnimationFrame];
    }

    private void loadFrames() {
        downFrames = new Image[3];
        downFrames[0] = spriteSheet.getSubimage(x, y, entity.getWidth(), entity.getHeight());
        downFrames[1] = spriteSheet.getSubimage(x + entity.getWidth(),y, entity.getWidth(), entity.getHeight());
        downFrames[2] = spriteSheet.getSubimage(x + entity.getWidth() * 2,y, entity.getWidth(), entity.getHeight());

        leftFrames = new Image[3];
        leftFrames[0] = spriteSheet.getSubimage(x,y + entity.getWidth(), entity.getWidth(), entity.getHeight());
        leftFrames[1] = spriteSheet.getSubimage(x + entity.getWidth(),y + entity.getWidth(), entity.getWidth(), entity.getHeight());
        leftFrames[2] = spriteSheet.getSubimage(x + entity.getWidth() * 2,y + entity.getWidth(), entity.getWidth(), entity.getHeight());

        rightFrames = new Image[3];
        rightFrames[0] = spriteSheet.getSubimage(x,y + entity.getWidth() * 2, entity.getWidth(), entity.getHeight());
        rightFrames[1] = spriteSheet.getSubimage(x + entity.getWidth(),y + entity.getWidth() * 2, entity.getWidth(), entity.getHeight());
        rightFrames[2] = spriteSheet.getSubimage(x + entity.getWidth() * 2,y + entity.getWidth() * 2, entity.getWidth(), entity.getHeight());

        upFrames = new Image[3];
        upFrames[0] = spriteSheet.getSubimage(x,y + entity.getWidth() * 3, entity.getWidth(), entity.getHeight());
        upFrames[1] = spriteSheet.getSubimage(x + entity.getWidth(),y + entity.getWidth() * 3, entity.getWidth(), entity.getHeight());
        upFrames[2] = spriteSheet.getSubimage(x + entity.getWidth() * 2,y + entity.getWidth() * 3, entity.getWidth(), entity.getHeight());
    }

    private void loadSpriteSheet() {
        spriteSheet = GameResources.getInstance().getBufferedImage(spriteSheetPath);
    }
}
