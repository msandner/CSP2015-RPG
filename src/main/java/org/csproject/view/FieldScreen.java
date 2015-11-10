package org.csproject.view;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.csproject.model.Constants;
import org.csproject.model.bean.*;
import org.csproject.service.ScreenFactory;
import org.csproject.model.actors.PlayerActor;
import org.csproject.service.KeyController;
import org.csproject.service.ScreensController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Maike Keune-Staab on 04.10.2015.
 */
@Component
public class FieldScreen extends Pane {

    @Autowired
    private ScreensController screensController;

    @Autowired
    private KeyController keyController;

    @Autowired
    private ScreenFactory screenFactory;

    private boolean moving;

    private CharacterImage avatar;

    private TranslateTransition transition;

    private Field field;

    public FieldScreen() {
        this.moving = false;
        setUpControlls();
    }

    public void setScene(Field field) {
        setScene(field, "characterStart", new PlayerActor("Generic Name", Constants.CLASS_SWORDSMAN, 1, 1.0, 1.0));
    }

    public void setScene(Field field, String startPoint, PlayerActor player) {

        this.field = field;
        getChildren().clear();
        getChildren().add(screenFactory.buildNode(field));

        PlayerActor playerActor = screensController.getPlayerActor();

        NavigationPoint start1 = field.getStart(startPoint);
        double charStartX = start1==null?0:start1.getX();
        double charStartY = start1==null?0:start1.getY();

        String type = player.getType();
        String avatarImage = updateCharacterImage(type);

        avatar = new CharacterImage(0, 1, charStartX, charStartY, avatarImage);

        getChildren().add(avatar);
        if (transition == null) {
            transition = new TranslateTransition(Duration.seconds(Constants.WALK_TIME_PER_TILE), getAvatar());
        }
        transition.setFromX(avatar.getPosX());
        transition.setFromY(avatar.getPosY());
        transition.setToX(avatar.getPosX());
        transition.setToY(avatar.getPosY());

        transition.playFromStart();
    }

    private String updateCharacterImage(String type) {
        String avatarImage;
        if(type.equals(Constants.CLASS_SWORDSMAN))
            avatarImage = Constants.IMAGE_SWORDSMAN;
        else if(type.equals(Constants.CLASS_KNIGHT))
            avatarImage = Constants.IMAGE_KNIGHT;
        else if(type.equals(Constants.CLASS_THIEF))
            avatarImage = Constants.IMAGE_THIEF;
        else avatarImage = Constants.IMAGE_MAGE;

        return avatarImage;
    }

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
     * Moves the avatar.
     *
     * @param direction The direction
     * @param finished  Callback function what to do after moving one field
     */
    public void moveAvatar(Direction direction, final EventHandler<ActionEvent> finished) {
        if (!moving) {
            moving = true;

            getAvatar().face(direction);

            double x = getAvatar().getPosX();
            double y = getAvatar().getPosY();

            switch (direction) {
                case UP: {
                    y -= Constants.TILE_SIZE;
                    break;
                }
                case DOWN: {
                    y += Constants.TILE_SIZE;
                    break;
                }
                case LEFT: {
                    x -= Constants.TILE_SIZE;
                    break;
                }
                case RIGHT: {
                    x += Constants.TILE_SIZE;
                    break;
                }
            }
            final double finalX = x;
            final double finalY = y;

            int column = (int)(x/ Constants.TILE_SIZE);
            int row = (int)(y/ Constants.TILE_SIZE);
            Tile t = null;
            try {
                t = field.getGroundTiles()[row][column];
            } catch (Exception e){
                // arrayoutofbounds
            }
            //System.out.println("Tile t: x=" + column + " y=" + row + " | walkable: " + t.isWalkable());
            if(t != null && t.isWalkable() ) // if walkable
            {
                transition.setFromX(getAvatar().getPosX());
                transition.setToX(finalX);
                transition.setFromY(getAvatar().getPosY());
                transition.setToY(finalY);

                transition.playFromStart();
                transition.setInterpolator(Interpolator.LINEAR);

                transition.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        moving = false;
                        getAvatar().setPosX(finalX);
                        getAvatar().setPosY(finalY);

//                        setTranslateX(finalX * -1);     //Remove these lines to
//                        setTranslateY(finalY * -1);     //stop the screen from moving
                        enterTown();
                        finished.handle(event);
                    }
                });
                getAvatar().setWalking(true);
            } else {
                moving = false;
            }
        }
    }

    private CharacterImage getAvatar() {
        return this.avatar;
    }

    public void stopAvatarAnimation() {
        getAvatar().setWalking(false);
    }

    public void enterTown() {
        NavigationPoint townTile = field.getTownTile();
        if (townTile != null) {
            NavigationPoint town = townTile;
            int column = (int)(getAvatar().getPosX()/ Constants.TILE_SIZE);
            int row = (int)(getAvatar().getPosY()/ Constants.TILE_SIZE);
            if (town.getX() == column && town.getY() == row) {
                screensController.setScreen(MasterController.TOWN_SCREEN);
            }
        }
    }
}
