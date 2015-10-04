package org.csproject.service;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.apache.log4j.Logger;
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

    Timer timer;

    @Autowired
    private FieldScreen fieldScreen;
    private MoveTask moveTask;

    public KeyController() {
        timer = new Timer();
    }

    public void onKeyPressed(KeyEvent event) {
        if(Arrays.asList(MOVEMENT_KEYS).contains(event.getCode())) {
            if (!pressedMovementKeys.contains(event.getCode())) {
                LOG.debug("test-pressed: " + event.getCode());
                pressedMovementKeys.add(event.getCode());

                if (moveTask != null) {
                    moveTask.cancel();
                }
                moveTask = new MoveTask();
                timer.scheduleAtFixedRate(moveTask, 0, 100);
            }
        }
        else
        {
            // todo other key events here
        }
    }

    public void onKeyReleased(KeyEvent event) {

        if(pressedMovementKeys.contains(event.getCode()))
        {
            LOG.debug("test-released: " + event.getCode());
            pressedMovementKeys.remove(event.getCode());
        }
    }

    private class MoveTask extends TimerTask {
        @Override
        public void run() {
            if(pressedMovementKeys.contains(KeyCode.W))
            {
                fieldScreen.moveUp();
            }
            else if(pressedMovementKeys.contains(KeyCode.A))
            {
                fieldScreen.moveLeft();
            }
            else if(pressedMovementKeys.contains(KeyCode.S))
            {
                fieldScreen.moveDown();
            }
            else if(pressedMovementKeys.contains(KeyCode.D))
            {
                fieldScreen.moveRight();
            }
        }
    }
}
