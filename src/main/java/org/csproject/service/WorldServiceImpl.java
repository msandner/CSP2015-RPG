package org.csproject.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import org.csproject.model.Constants;
import org.csproject.model.actors.PlayerActor;
import org.csproject.model.field.DungeonDetails;
import org.csproject.model.field.Field;
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

    @Autowired
    private Gson gson;

    private PlayerActor playerActor = new PlayerActor("Test Player", Constants.CLASS_SWORDSMAN, 1, 5, 8); // todo let this do the menu
    private Map<String, Field> tempFields = new HashMap<>();
    private Field worldMap;

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
        worldMap = fieldFactory.generateWorldMap();
        tempFields.put(WORLD_MAP, worldMap);
        return worldMap;
    }

    //creates the field of the worldmap
    public void setWorldMap(Field worldMap) throws FileNotFoundException {
        saveField(worldMap, WORLD_MAP);
    }


    @Override
    public void saveField(Field field, String name) throws FileNotFoundException {
        String json = gson.toJson(field);
        saveFile(JSON_DIR + name + JSON_POST_FIX, json);
    }

    @Override
    public Field getField(String fieldName) {
        if (tempFields.get(fieldName) != null) {
            return tempFields.get(fieldName);
        }
        String json = Utilities.getResource(JSON_DIR + fieldName + JSON_POST_FIX);
        return gson.fromJson(json, Field.class);
    }

    @Override
    public Field generateDungeon(DungeonHelper.Type type, int height, int width, int floors, String targetMap, String targetPoint, String sourceMap,
                                 String sourcePoint) {
        DungeonDetails dungeonDetails = DungeonHelper.getDungeonDetails(type, height, width, floors, targetMap, targetPoint, sourceMap, sourcePoint, this);

        return fieldFactory.generateDungeon(dungeonDetails);
    }

    @Override
    public void setTempField(Field field, String tempFieldName) {
        tempFields.put(tempFieldName, field);
    }

    private void saveFile(String fileName, String json) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File(this.getClass().getResource(fileName).getPath()));
        writer.print(json);
        writer.close();
        System.exit(0);
    }
}
