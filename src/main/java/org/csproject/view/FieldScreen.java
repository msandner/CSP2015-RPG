package org.csproject.view;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
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

    private Node avatar;

    public FieldScreen() {
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
//        getAvatarCircle().setCenterX(start.getX());
//        getAvatarCircle().setCenterY(start.getY());

        avatar = new  CharacterImage();
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

    //TODO:  add sprite animations, maybe later transition animation instead of jumping 10 pixels
    public void moveUp() {
        getAvatarCircle().setCenterY(getAvatarCircle().getCenterY() - 10);
    }

    public void moveLeft() {
        getAvatarCircle().setCenterX(getAvatarCircle().getCenterX() - 10);
    }

    public void moveDown() {
        getAvatarCircle().setCenterY(getAvatarCircle().getCenterY() + 10);
    }

    public void moveRight() {
        getAvatarCircle().setCenterX(getAvatarCircle().getCenterX() + 10);
    }

    /**
     * TODO: Remove this method as soon as the avatar is no circle anymore
     * @return
     */
    private Circle getAvatarCircle() {
        return (Circle) this.avatar;
    }
}
