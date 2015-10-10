package org.csproject.view;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.csproject.model.Constants;
import org.csproject.model.bean.Direction;

/**
 * Created by Brett on 10/8/2015.
 */
public class CharacterImage extends ImageView {
    private static final int BLOCK_SIZE_X = Constants.TILE_SIZE * 3;
    private static final int BLOCK_SIZE_Y = Constants.TILE_SIZE * 4;

    private double posX, posY;
    private int actorImageBlockX, actorImageBlockY;
    private Image actorImage;
    private int animPhase; // 0, 1 or 2
    private Direction faceDirection;
    private Thread animationThread;
    private boolean walking;

    public CharacterImage(int blockX, int blockY, double posX, double posY, String imageUrl) {
        super();

        walking = false;

        this.posX = posX;
        this.posY = posY;

        faceDirection = Direction.DOWN;

        actorImageBlockX = blockX;
        actorImageBlockY = blockY;

        actorImage = new Image(imageUrl);

        setImage(actorImage);

        updateAnimation();

        animationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        if(walking) {
                            setAnimationPhase(0);
                            Thread.sleep((long) (Constants.WALK_TIME_PER_TILE * 1000 / 2));
                            setAnimationPhase(2);
                        }
                        Thread.sleep((long) (Constants.WALK_TIME_PER_TILE * 1000 / 2));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        animationThread.setDaemon(true);
        animationThread.start();
    }

    public void updateAnimation() {
        setViewport(new Rectangle2D(
                actorImageBlockX * BLOCK_SIZE_X + animPhase * Constants.TILE_SIZE,
                actorImageBlockY * BLOCK_SIZE_Y + faceDirection.ordinal() * Constants.TILE_SIZE,
                Constants.TILE_SIZE,
                Constants.TILE_SIZE));
    }

    public void face(Direction direction)
    {
        faceDirection = direction;
        updateAnimation();
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public void setAnimationPhase(int animationPhase) {
        this.animPhase = animationPhase;
        updateAnimation();
    }

    public void setWalking(boolean walking) {
        this.walking = walking;
        if(!walking) {
            setAnimationPhase(1);
        }
    }
}
