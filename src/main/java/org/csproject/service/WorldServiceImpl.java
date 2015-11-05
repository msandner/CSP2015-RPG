package org.csproject.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import org.csproject.model.actors.Actor;
import org.csproject.model.actors.PlayerActor;
import org.csproject.model.bean.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.csproject.model.Constants.*;

/**
 * @author Maike Keune-Staab on 12.09.2015.
 */
@Component
public class WorldServiceImpl implements WorldService {

  @Autowired
    private ActorFactory actorFactory;

    @Autowired
    private FieldFactory fieldFactory;

    @Autowired
    private Gson gson;

    public Actor createActor(String name, String type) {

        return actorFactory.createActor(name, type);
    }

    /**
     * TODO
     * @return The current player actor
     */
    @Override
    public PlayerActor getPlayerActor() {
        PlayerActor playerActor = new PlayerActor("Test Player", ActorFactory.KNIGHT,1, 5, 8);
        return playerActor;
    }

    public List<PlayerActor> getAvailableClasses() {

        String json = getFile(CHARACTERS);
        PlayerActor[] playerActors = gson.fromJson(json, PlayerActor[].class);
        return Arrays.asList(playerActors);
    }

    @Override
    public void setAvailableClasses(List<PlayerActor> playerActors) throws FileNotFoundException {

        String json = gson.toJson(playerActors.toArray(new PlayerActor[playerActors.size()]));
        saveFile(CHARACTERS, json);
    }

    @Override
    public Field getWorldMap() {

        return getField(WORLD_MAP);
    }

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
        String json = getFile(JSON_DIR + fieldName + JSON_POST_FIX);
        Field field = gson.fromJson(json, Field.class);
        return field;
    }

    @Override
    public Field generateDungeon(String groundImage, String decoImage, int colNum, int rowNum){
        return fieldFactory.generateDungeon(groundImage, decoImage, colNum, rowNum);
    }

    private void saveFile(String fileName, String json) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File(this.getClass().getResource(fileName).getPath()));
        writer.print(json);
        writer.close();
        System.exit(0);
    }

    private String getFile(String fileName) {

        StringBuilder result = new StringBuilder("");

        //Get file from resources folder
        File file = new File(this.getClass().getResource(fileName).getFile());

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}
