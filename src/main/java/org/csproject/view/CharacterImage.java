package org.csproject.view;

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

    public CharacterImage(int blockX, int blockY, double posX, double posY, String imageUrl) {
        super();

        this.posX = posX;
        this.posY = posY;

        faceDirection = Direction.DOWN;

        actorImageBlockX = blockX;
        actorImageBlockY = blockY;

        actorImage = new Image(imageUrl);

        setImage(actorImage);

        updateAnimation();
    }

    private void updateAnimation() {
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
}
