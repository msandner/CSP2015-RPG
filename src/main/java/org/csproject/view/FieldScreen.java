package org.csproject.view;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.csproject.model.Constants;
import org.csproject.model.bean.Direction;
import org.csproject.model.bean.Tile;
import org.csproject.service.ScreenFactory;
import org.csproject.model.actors.PlayerActor;
import org.csproject.model.bean.Field;
import org.csproject.model.bean.NavigationPoint;
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
        setScene(field, "characterStart");
    }

    public void setScene(Field field, String startPoint) {

        this.field = field;
        NavigationPoint start = this.field.getStart(startPoint);
        getChildren().clear();
        getChildren().add(screenFactory.buildNode(field));

        PlayerActor playerActor = screensController.getPlayerActor();

        double charStartX = field.getStart(startPoint).getX();
        double charStartY = field.getStart(startPoint).getY();

        avatar = new CharacterImage(0, 1, charStartX, charStartY, "images/actors/Evil.png");

        getChildren().add(avatar);
    }

    private void setUpControlls() {
        setFocusTraversable(true);
        requestFocus();
        setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                keyController.onKeyPressed(event);
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

            // TODO for maren: check the tile on position x/TILE_SIZE, y/TILE_SIZE (from field variable), if walkable
            int column = (int)(x/Tile.TILE_SIZE);
            int row = (int)(y/Tile.TILE_SIZE);
            Tile t = null;
            try {
                t = field.getTiles()[row][column];
            } catch (Exception e){
                // arrayoutofbounds
            }
            //System.out.println("Tile t: x=" + column + " y=" + row + " | walkable: " + t.isWalkable());
            if(t != null && t.isWalkable() ) // if walkable
            {
                if (transition == null) {
                    transition = new TranslateTransition(Duration.seconds(Constants.WALK_TIME_PER_TILE), getAvatar());
                }
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

                        setTranslateX(finalX * -1);     //Remove these lines to
                        setTranslateY(finalY * -1);     //stop the screen from moving
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
}
