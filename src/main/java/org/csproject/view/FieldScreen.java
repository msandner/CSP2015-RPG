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

    public FieldScreen() {
        this.moving = false;
        setUpControlls();
    }

    public void setScene(Field field) {
        setScene(field, null);
    }

    public void setScene(Field field, String startPoint) {

        NavigationPoint start = field.getStart(startPoint);
        getChildren().clear();
        getChildren().add(screenFactory.buildNode(field));

        PlayerActor playerActor = screensController.getPlayerActor();

        // TODO create an avatar using the data from the player actor
        // meanwhile as placeholder, we use a circle shape
//        avatar = new Circle(50.0);
//        getAvatar().setCenterX(start.getX());
//        getAvatar().setCenterY(start.getY());

        avatar = new CharacterImage();
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
    public void move(Direction direction, final EventHandler<ActionEvent> finished) {
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

            if (transition == null) {
                transition = new TranslateTransition(Duration.seconds(Constants.WALK_TIME_PER_TILE), getAvatar());
            }
            transition.setFromX(getAvatar().getPosX());
            transition.setToX(x);
            transition.setFromY(getAvatar().getPosY());
            transition.setToY(y);

            transition.playFromStart();
            transition.setInterpolator(Interpolator.LINEAR);
            final double finalX = x;
            final double finalY = y;
            transition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    moving = false;
                    getAvatar().setPosX(finalX);
                    getAvatar().setPosY(finalY);
                    finished.handle(event);
                }
            });
        }
    }

    private CharacterImage getAvatar() {
        return this.avatar;
    }
}
