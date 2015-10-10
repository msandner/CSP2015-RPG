package org.csproject.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.csproject.configuration.SpringConfiguration;
import org.csproject.model.actors.Actor;
import org.csproject.model.actors.PlayerActor;
import org.csproject.model.bean.Field;
import org.csproject.model.bean.NavigationPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author Maike Keune-Staab on 12.09.2015.
 */
@Component
public class WorldServiceImpl implements WorldService {

    public static final String CHARACTERS_JSON = "/characters.json";
    @Autowired
    private ActorFactory actorFactory;

    public Actor createActor(String name, String type) {

        return actorFactory.createActor(name, type);
    }

    @Override
    public Field getNewWorld() {
        Field field = new Field();

        field.setStartPoint(new NavigationPoint(100, 100));

        return field; // todo create the map (for example: from random world generator)
    }

    /**
     * TODO
     * @return The current player actor
     */
    @Override
    public PlayerActor getPlayerActor() {
        // todo
        return new PlayerActor("Test player", ActorFactory.KNIGHT, 1, 5, 8);
    }

    public List<PlayerActor> getAvailableClasses() {

        String json = getFile(CHARACTERS_JSON);
        Gson gson = new GsonBuilder().create();
        PlayerActor[] playerActors = gson.fromJson(json, PlayerActor[].class);
        return Arrays.asList(playerActors);
    }

    @Override
    public void setAvailableClasses(List<PlayerActor> playerActors) throws FileNotFoundException {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(playerActors.toArray(new PlayerActor[playerActors.size()]));
        saveFile(CHARACTERS_JSON, json);
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

    public static void main(String[] args) throws FileNotFoundException {

        // Start the spring application context
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);

        // example to proof the application context
        WorldService world = context.getBean(WorldService.class);

        for (PlayerActor playerActor : world.getAvailableClasses()) {
            System.out.println(playerActor);
        }
    }
}
