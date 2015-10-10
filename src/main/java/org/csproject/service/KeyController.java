package org.csproject.service;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.apache.log4j.Logger;
import org.csproject.model.bean.Direction;
import org.csproject.view.FieldScreen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author Maike Keune-Staab on 04.10.2015.
 */
@Component
public class KeyController {

    private final static Logger LOG = Logger.getLogger(KeyController.class);

    public static final KeyCode[] MOVEMENT_KEYS = new KeyCode[]{KeyCode.W, KeyCode.A, KeyCode.S, KeyCode.D};

    final Set<KeyCode> pressedMovementKeys = new HashSet<>();

    @Autowired
    private FieldScreen fieldScreen;

    public void onKeyPressed(KeyEvent event) {
        if (Arrays.asList(MOVEMENT_KEYS).contains(event.getCode())) {
            if (!pressedMovementKeys.contains(event.getCode())) {
                LOG.debug("test-pressed: " + event.getCode());
                pressedMovementKeys.add(event.getCode());
                move();
            }
        } else {
            // todo other key events here
        }
    }

    public void onKeyReleased(KeyEvent event) {

        if (pressedMovementKeys.contains(event.getCode())) {
            LOG.debug("test-released: " + event.getCode());
            pressedMovementKeys.remove(event.getCode());
        }
    }

    private void move() {
        Direction direction = null;
        if (pressedMovementKeys.contains(KeyCode.W)) {
            direction = Direction.UP;
        } else if (pressedMovementKeys.contains(KeyCode.A)) {
            direction = Direction.LEFT;
        } else if (pressedMovementKeys.contains(KeyCode.S)) {
            direction = Direction.DOWN;
        } else if (pressedMovementKeys.contains(KeyCode.D)) {
            direction = Direction.RIGHT;
        }

        if(direction != null)
        {
            final Direction finalDirection = direction;
            Task transitionTask = new Task() {
                @Override
                protected Object call() throws Exception {
                    fieldScreen.move(finalDirection, new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            move();
                        }
                    });
                    return null;
                }
            };
            new Thread(transitionTask).start();
        }
    }
}
