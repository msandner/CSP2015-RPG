package org.csproject.service;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.apache.log4j.Logger;
import org.csproject.model.bean.Direction;
import org.csproject.view.FieldScreen;
import org.csproject.view.MasterController;
import org.csproject.view.TownScreen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.io.*;

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

    @Autowired
    private TownScreen townScreen;

    String screenToMove;

    public void onKeyPressed(KeyEvent event, String screen) {
        if (Arrays.asList(MOVEMENT_KEYS).contains(event.getCode())) {
            if (!pressedMovementKeys.contains(event.getCode())) {
                LOG.debug("test-pressed: " + event.getCode());
                pressedMovementKeys.add(event.getCode());
                move(screen);
            }
        } else if (event.getCode() == KeyCode.C){
            try {
                saveGame(screen);
            } catch(IOException e){
                System.out.println("Could not save.");
                e.printStackTrace();
            }
        }
        else {
            // todo other key events here
        }
    }

    public void onKeyReleased(KeyEvent event) {

        if (pressedMovementKeys.contains(event.getCode())) {
            LOG.debug("test-released: " + event.getCode());
            pressedMovementKeys.remove(event.getCode());
        }

        if(pressedMovementKeys.isEmpty())
        {
            fieldScreen.stopAvatarAnimation();
        }
    }

    private void move(String screen) {
        Direction direction = null;
        screenToMove = screen;
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
                    if (screenToMove.equals(MasterController.GAME_SCREEN)) {
                        fieldScreen.moveAvatar(finalDirection, new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                move(screenToMove);
                            }
                        });
                    } else if (screenToMove.equals(MasterController.TOWN_SCREEN)) {
                        townScreen.moveAvatar(finalDirection, new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                move(screenToMove);
                            }
                        });
                    }
                    
                    return null;
                }
            };
            Thread moveThread = new Thread(transitionTask);
            moveThread.setDaemon(true);
            moveThread.start();
        }
    }

    private void saveGame(String screen) throws IOException{
        double x = 0, y = 0;
        if(screen.equals(MasterController.GAME_SCREEN)) {
            x = fieldScreen.getAvatar().getPosX();
            y = fieldScreen.getAvatar().getPosY();
        } else if(screen.equals(MasterController.TOWN_SCREEN)) {
            x = townScreen.getAvatar().getPosX();
            y = fieldScreen.getAvatar().getPosY();
        } else{
            //Dungeon?
        }

        //Here we get the player party

        String data = screen + " " + x + " " + y;
        String path = System.getProperty("user.home") + "\\Documents\\FFC_Saves";

        File saveLocation = new File(path);
        if(!saveLocation.exists()){
            try{
                saveLocation.mkdir();
            } catch (SecurityException s) {
                System.out.println("Save folder could not be created.");
            }
        }

        FileOutputStream fos = new FileOutputStream(saveLocation + "/saves.txt");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        bw.write(data);
        bw.close();
        fos.close();
    }
}
