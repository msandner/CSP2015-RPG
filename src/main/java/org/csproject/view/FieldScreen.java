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
        setScene(field, null);
    }

    public void setScene(Field field, String startPoint) {

        this.field = field;
        NavigationPoint start = this.field.getStart(startPoint);
        getChildren().clear();
        getChildren().add(screenFactory.buildNode(field));

        PlayerActor playerActor = screensController.getPlayerActor();

        avatar = new CharacterImage(1, 1, 0.0, 0.0, "images/Actor1.png");
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

            if(true) // if walkable
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
                        finished.handle(event);
                    }
                });
                getAvatar().setWalking(true);
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
