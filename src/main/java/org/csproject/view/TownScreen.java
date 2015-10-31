package org.csproject.view;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.csproject.model.Constants;
import org.csproject.model.actors.PlayerActor;
import org.csproject.model.bean.*;
import org.csproject.service.KeyController;
import org.csproject.service.ScreenFactory;
import org.csproject.service.ScreensController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Brett on 10/26/2015.
 *
 * TODO Redundant: A town is a field.
 */
@Component
public class TownScreen extends Pane {

    @Autowired
    private ScreensController screensController;

    @Autowired
    private KeyController keyController;

    @Autowired
    private ScreenFactory screenFactory;

    private Town town;

    private boolean moving;

    private CharacterImage avatar;

    private TranslateTransition transition;


    public TownScreen() {
        this.moving = false;
        setUpControlls();
    }

    public void setScene(Town town) {
        setScene(town, null);
    }

    public void setScene(Town town, String startPoint) {

        this.town = town;
        NavigationPoint start = this.town.getTownEntrance();
        getChildren().clear();
        getChildren().add(screenFactory.buildNode(town));

        PlayerActor playerActor = screensController.getPlayerActor();

        double charStartX = start.getX();
        double charStartY = start.getY();

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
     * @param finished  Callback function what to do after moving one town
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
                t = town.getTiles()[row][column];
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
