package org.csproject.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import org.csproject.model.Constants;
import org.csproject.model.actors.PlayerActor;
import org.csproject.model.bean.Field;
import org.csproject.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.csproject.model.Constants.*;

/**
 * @author Maike Keune-Staab on 12.09.2015.
 */
@Component
public class WorldServiceImpl implements WorldService {

    @Autowired
    private FieldFactory fieldFactory;

    private PlayerActor playerActor = new PlayerActor("Test Player", Constants.CLASS_SWORDSMAN, 1, 5, 8); // todo let this do the menu

    @Autowired
    private Gson gson;

   //creates and returns the walkable Character
    @Override
    public void setPlayerActor(PlayerActor playerActor) {
        this.playerActor = playerActor;
    }

    @Override
    public PlayerActor getPlayerActor() {
        return this.playerActor;
    }

    //returns a list of available classes
    public List<PlayerActor> getAvailableClasses() {

        String json = Utilities.getResource(CHARACTERS);
        PlayerActor[] playerActors = gson.fromJson(json, PlayerActor[].class);
        return Arrays.asList(playerActors);
    }

    //sets a list of available classes
    @Override
    public void setAvailableClasses(List<PlayerActor> playerActors) throws FileNotFoundException {
        String json = gson.toJson(playerActors.toArray(new PlayerActor[playerActors.size()]));
        saveFile(CHARACTERS, json);
    }

    //returns the field of the worldmap
    @Override
    public Field getWorldMap() {
        return getField(WORLD_MAP);
    }

    //creates the field of the worldmap
    public void setWorldMap(Field worldMap) throws FileNotFoundException {
        setField(worldMap, WORLD_MAP);
    }


    @Override
    public void setField(Field field, String name) throws FileNotFoundException {
        String json = gson.toJson(field);
        saveFile(JSON_DIR + name + JSON_POST_FIX, json);
    }

    @Override
    public Field getField(String fieldName) {
        String json = Utilities.getResource(JSON_DIR + fieldName + JSON_POST_FIX);
        return gson.fromJson(json, Field.class);
    }

    @Override
    public Field generateField(String type){
        return fieldFactory.generateNewField(type);
    }


    private void saveFile(String fileName, String json) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File(this.getClass().getResource(fileName).getPath()));
        writer.print(json);
        writer.close();
        System.exit(0);
    }
}
