package org.csproject.view;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.csproject.model.Constants;
import org.csproject.model.actors.PlayerActor;
import org.csproject.model.bean.Direction;
import org.csproject.model.bean.NavigationPoint;
import org.csproject.model.field.Field;
import org.csproject.model.field.TeleportPoint;
import org.csproject.model.field.Tile;
import org.csproject.service.KeyController;
import org.csproject.service.ScreenFactory;
import org.csproject.service.ScreensController;
import org.csproject.service.WorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.csproject.model.Constants.*;

/**
 * @author Maike Keune-Staab on 04.10.2015.
 */
@Component
public class FieldScreen extends Pane {
    @Autowired
    private KeyController keyController;

    @Autowired
    private ScreenFactory screenFactory;

    @Autowired
    private WorldService worldService;

    private boolean moving;

    private CharacterImage avatar;

    private TranslateTransition transition;
    private TranslateTransition screenTransition;

    private Field field;

    public PlayerActor playerActor;

    public FieldScreen() {
        this.moving = false;
        setUpControlls();

        setScaleX(SCALE);
        setScaleY(SCALE);
    }

    public void setStartPlayer(PlayerActor p) {
        playerActor = p;
    }

    public void setScene(Field field) {
        setScene(field, "characterStart");
    }

    /**
     * Maike Keune-Staab
     * sets the current field and lets the avatar start at a startPoint with the given name.
     *
     * @param field
     * @param startPoint
     */
    public void setScene(Field field, String startPoint) {

        this.field = field;
        getChildren().clear();
        getChildren().add(screenFactory.buildNode(field));

        NavigationPoint start1 = field.getStart(startPoint);
        final double charStartX = start1 == null ? 0 : start1.getX() * TILE_SIZE;
        final double charStartY = start1 == null ? 0 : start1.getY() * TILE_SIZE;

        String type = playerActor.getType();
        final String avatarImage = updateCharacterImage(type);

        avatar = new CharacterImage(0, 1, charStartX, charStartY, avatarImage);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getChildren().add(avatar);
                moveScreen(avatar.getX(), avatar.getY(), 0, 0, 0);
            }
        });
    }

    private String updateCharacterImage(String type) {
        String avatarImage;
        if (type.equals(Constants.CLASS_KNIGHT))
            avatarImage = Constants.IMAGE_KNIGHT;
        else if (type.equals(Constants.CLASS_THIEF))
            avatarImage = Constants.IMAGE_THIEF;
        else avatarImage = Constants.IMAGE_MAGE;

        return avatarImage;
    }

    /**
     * Maike Keune-Staab
     * requests the focus and adds a key press handler
     */
    private void setUpControlls() {
        setFocusTraversable(true);
        requestFocus();
        setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                keyController.onKeyPressed(event, MasterController.GAME_SCREEN);
            }
        });
        setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                keyController.onKeyReleased(event);
            }
        });
    }

    /**
     * Maike Keune-Staab
     * creates a transition to move the character one tile into the given direction. After the transition "finished"
     * will be executed.
     *
     * @param direction The direction
     * @param finished  Callback function what to do after moving one field
     */
    public void moveAvatar(Direction direction, final EventHandler<ActionEvent> finished) {
        if (!moving) {
            moving = true;

            getAvatar().face(direction);

            double x = 0;
            double y = 0;

            switch (direction) {
                case UP: {
                    y -= TILE_SIZE;
                    break;
                }
                case DOWN: {
                    y += TILE_SIZE;
                    break;
                }
                case LEFT: {
                    x -= TILE_SIZE;
                    break;
                }
                case RIGHT: {
                    x += TILE_SIZE;
                    break;
                }
            }
            final double finalX = x;
            final double finalY = y;

            int column = (int) ((avatar.getX() + x) / TILE_SIZE);
            int row = (int) ((avatar.getY() + y) / TILE_SIZE);
            Tile groundTile = null;
            Tile decoTile = null;
            try {
                groundTile = field.getGroundTiles()[row][column];
                decoTile = field.getDecoTiles()[row][column];
            } catch (Exception e) {
                // arrayoutofbounds
            }
            //System.out.println("Tile groundTile: x=" + column + " y=" + row + " | walkable: " + groundTile.isWalkable());
            if (groundTile != null && groundTile.isWalkable() && (decoTile == null || decoTile.isWalkable())) // if walkable
            {
                if (transition == null) {
                    transition = new TranslateTransition(Duration.seconds(Constants.WALK_TIME_PER_TILE), getAvatar());
                }
                transition.setFromX(0);
                transition.setToX(finalX);
                transition.setFromY(0);
                transition.setToY(finalY);

                transition.playFromStart();
                transition.setInterpolator(Interpolator.LINEAR);

                transition.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        moving = false;
                        avatar.setX(avatar.getX() + finalX);
                        avatar.setY(avatar.getY() + finalY);
                        avatar.setTranslateX(0);
                        avatar.setTranslateY(0);
                        if (handleNavigationPoints((int) ((avatar.getX()) / TILE_SIZE),
                                (int) ((avatar.getY()) / TILE_SIZE))) {
                            finished.handle(event);
                        }
                    }
                });

                moveScreen(avatar.getX(), avatar.getY(), finalX, finalY, WALK_TIME_PER_TILE);

                getAvatar().setWalking(true);
            } else {
                moving = false;
            }
        }
    }

    /**
     * Maike Keune-Staab
     * moves the screen from a given start position to a given move position over the given duration.
     * @param startX
     * @param startY
     * @param moveX
     * @param moveY
     * @param duration
     */
    private void moveScreen(double startX, double startY, double moveX, double moveY, double duration) {
        if (screenTransition == null) {
            screenTransition = new TranslateTransition(Duration.seconds(duration), this);
        }
        screenTransition.setDuration(Duration.seconds(duration));

        double currentTransX = (startX * -1) + SCREEN_WIDTH / 2;
        double currentTransY = (startY * -1) + SCREEN_HEIGHT / 2;

        double tilesOnScreenWidth = SCREEN_WIDTH / TILE_SIZE;
        double tilesOnScreenHeight = SCREEN_HEIGHT / TILE_SIZE;

        if (field.getWidth() < tilesOnScreenWidth) {
            screenTransition.setToX(((tilesOnScreenWidth - field.getWidth()) / 2) * TILE_SIZE);
        } else {
            double transToX = currentTransX - moveX * SCALE;
            screenTransition.setFromX(getTranslateX());
            screenTransition.setToX(getTranslateX());
            if (transToX > 0) {
                screenTransition.setToX(0);
            } else if (transToX <= SCREEN_WIDTH - field.getWidth() * Constants.TILE_SIZE) {
                screenTransition.setToX(SCREEN_WIDTH - field.getWidth() * Constants.TILE_SIZE);
            } else {
                screenTransition.setToX(transToX);
            }
        }

        if (field.getHeight() < tilesOnScreenHeight) {
            screenTransition.setToY(((tilesOnScreenHeight - field.getHeight()) / 2) * TILE_SIZE);
        } else {
            double transToY = currentTransY - moveY * SCALE;
            screenTransition.setFromY(getTranslateY());
            screenTransition.setToY(getTranslateY());
            if (transToY > 0) {
                screenTransition.setToY(0);
            } else if (transToY <= SCREEN_HEIGHT - field.getHeight() * Constants.TILE_SIZE) {
                screenTransition.setToY(SCREEN_HEIGHT - field.getHeight() * Constants.TILE_SIZE);
            } else {
                screenTransition.setToY(transToY);
            }
        }

        screenTransition.setInterpolator(Interpolator.LINEAR);

        if (duration > 0) {
            screenTransition.playFromStart();
        } else {
            setTranslateX(screenTransition.getToX());
            setTranslateY(screenTransition.getToY());
        }
    }

    public CharacterImage getAvatar() {
        return this.avatar;
    }

    public void stopAvatarAnimation() {
        getAvatar().setWalking(false);
    }

    /**
     * Maike Keune-Staab
     * is always called after the avatar moved one tile. This method checks all points of interest on the current tile.
     * @param x
     * @param y
     * @return
     */
    public boolean handleNavigationPoints(int x, int y) {

        for (TeleportPoint teleportPoint : field.getTeleportPoints()) {
            if (teleportPoint.getX() == x && teleportPoint.getY() == y) {

                transition = null;
                screenTransition = null;

                if (teleportPoint.isRandom()) {
                    setScene(worldService.generateDungeon(teleportPoint.getRandomType(),
                            teleportPoint.getRandomHeight(), teleportPoint.getRandomWidth(),
                            teleportPoint.getRandomFloors(), teleportPoint.getTargetField(),
                            teleportPoint.getTargetPoint(), teleportPoint.getSourceField(),
                            teleportPoint.getSourcePoint()));
                } else {
                    setScene(worldService.getField(teleportPoint.getTargetField()), teleportPoint.getTargetPoint());
                }

                return false;
            }
        }
        return true;
    }
}
