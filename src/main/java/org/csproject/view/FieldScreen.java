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
import org.csproject.model.bean.Field;
import org.csproject.model.bean.NavigationPoint;
import org.csproject.model.bean.TeleportPoint;
import org.csproject.model.bean.Tile;
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

    public static final PlayerActor TEST_PLAYER = new PlayerActor("Generic Name", Constants.CLASS_SWORDSMAN, 1, 1.0, 1.0);
    @Autowired
    private ScreensController screensController;

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

    public FieldScreen() {
        this.moving = false;
        setUpControlls();

        setScaleX(SCALE);
        setScaleY(SCALE);
    }

    public void setScene(Field field) {
        setScene(field, "characterStart");
    }

    public void setScene(Field field, String startPoint) {

        this.field = field;
        getChildren().clear();
        getChildren().add(screenFactory.buildNode(field));

        PlayerActor playerActor = screensController.getPlayerActor();

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
                        if(handleNavigationPoints((int)((avatar.getX()) / TILE_SIZE),
                                (int)((avatar.getY()) / TILE_SIZE))){
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

  private void moveScreen(double startX, double startY, double moveX, double moveY, double duration)
  {
    if (screenTransition == null) {
        screenTransition = new TranslateTransition(Duration.seconds(duration), this);
    }
    screenTransition.setDuration(Duration.seconds(duration));

    double currentTransX = (startX * -1) + SCREEN_WIDTH / 2;
    double currentTransY = (startY * -1) + SCREEN_HEIGHT / 2;

    double transToX = currentTransX - moveX * SCALE;
    screenTransition.setFromX(getTranslateX());
    screenTransition.setToX(getTranslateX());
    if (transToX > 0) {
      screenTransition.setToX(0);
    }
    else if(transToX <= SCREEN_WIDTH - field.getWidth()) {
      screenTransition.setToX(SCREEN_WIDTH - field.getWidth());
    }
    else {
        screenTransition.setToX(transToX);
    }

    screenTransition.setFromY(getTranslateY());
    screenTransition.setToY(getTranslateY());
    double transToY = currentTransY - moveY * SCALE;
    if (transToY > 0){
      screenTransition.setToY(0);
    }
    else if(transToY <= SCREEN_HEIGHT - field.getHeight()) {
      screenTransition.setToY(SCREEN_HEIGHT - field.getHeight());
    }
    else {
        screenTransition.setToY(transToY);
    }

    screenTransition.setInterpolator(Interpolator.LINEAR);

    if(duration > 0)
    {
      screenTransition.playFromStart();
    }
    else
    {
      setTranslateX(screenTransition.getToX());
      setTranslateY(screenTransition.getToY());
    }
  }

  private CharacterImage getAvatar() {
        return this.avatar;
    }

    public void stopAvatarAnimation() {
        getAvatar().setWalking(false);
    }

    public boolean handleNavigationPoints(int x, int y) {

        for (TeleportPoint teleportPoint : field.getTeleportPoints()) {
            if(teleportPoint.getX() == x && teleportPoint.getY() == y){

                transition = null;
                screenTransition = null;
                setScene(worldService.getField(teleportPoint.getTargetField()), teleportPoint.getTargetPoint());

                return false;
            }
        }
        return true;
    }
}
