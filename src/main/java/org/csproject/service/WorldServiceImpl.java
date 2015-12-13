package org.csproject.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

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

    private Map<String, Field> tempFields = new HashMap<>();
    private Field worldMap;


    /**
     * Maike Keune-Staab
     *
     * @return a list of available character classes
     */
    public List<PlayerActor> getAvailableClasses() {
        String json = Utilities.getResource(CHARACTERS);
        PlayerActor[] playerActors = gson.fromJson(json, PlayerActor[].class);
        return Arrays.asList(playerActors);
    }

    /**
     * Maike Keune-Staab
     * <p>
     * sets a list of available character classes
     *
     * @param playerActors
     * @throws FileNotFoundException
     */
    @Override
    public void setAvailableClasses(List<PlayerActor> playerActors) throws FileNotFoundException {
        String json = gson.toJson(playerActors.toArray(new PlayerActor[playerActors.size()]));
        saveFile(CHARACTERS, json);
    }

    /**
     * Maike Keune-Staab
     * @return the worldmap field
     */
    @Override
    public Field getWorldMap() {
        worldMap = fieldFactory.generateWorldMap();
        tempFields.put(WORLD_MAP, worldMap);
        return worldMap;
    }

    /**
     * Maike Keune-Staab
     * Saves the given field with the given name
     * @param field
     * @param name
     * @throws FileNotFoundException
     */
    @Override
    public void saveField(Field field, String name) throws FileNotFoundException {
        String json = gson.toJson(field);
        saveFile(JSON_DIR + name + JSON_POST_FIX, json);
    }

    /**
     * Maike Keune-Staab
     * returns the field with the given name. the field either comes from the temp fields map or is loaded from the disk.
     * @param fieldName
     * @return
     */
    @Override
    public Field getField(String fieldName) {
        if (tempFields.get(fieldName) != null) {
            return tempFields.get(fieldName);
        }
        String json = Utilities.getResource(JSON_DIR + Constants.FIELDS_DIR + fieldName + JSON_POST_FIX);
        return gson.fromJson(json, Field.class);
    }

    /**
     * Maike Keune-Staab
     * generates a random dungeon according to the given parameters and returns the first floor.
     * @param type
     * @param height
     * @param width
     * @param floors
     * @param targetMap
     * @param targetPoint
     * @param sourceMap
     * @param sourcePoint
     * @return
     */
    @Override
    public Field generateDungeon(DungeonHelper.Type type, int height, int width, int floors, String targetMap,
                                 String targetPoint, String sourceMap, String sourcePoint) {
        // clear old random maps, except for the worldMap field
        Set<String> tempFieldNames = tempFields.keySet();
        for (String fieldKey : tempFieldNames) {
            if (!WORLD_MAP.equals(fieldKey)) {
                tempFields.remove(fieldKey);
            }
        }

        DungeonDetails dungeonDetails = DungeonHelper.getDungeonDetails(type, height, width, floors, targetMap,
                targetPoint, sourceMap, sourcePoint, this);

        return fieldFactory.generateDungeon(dungeonDetails);
    }

    /**
     * Maike Keune-Staab
     * @param field
     * @param tempFieldName
     */
    @Override
    public void setTempField(Field field, String tempFieldName) {
        tempFields.put(tempFieldName, field);
    }

    /**
     * Maike Keune-Staab
     * saves a .json file on the harddrive.
     * @param fileName
     * @param json
     * @throws FileNotFoundException
     */
    private void saveFile(String fileName, String json) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File(this.getClass().getResource(fileName).getPath()));
        writer.print(json);
        writer.close();
        System.exit(0);
    }
}
