package org.csproject.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.csproject.configuration.SpringConfiguration;
import org.csproject.model.Constants;
import org.csproject.model.actors.Actor;
import org.csproject.model.actors.PlayerActor;
import org.csproject.model.bean.Field;
import org.csproject.model.bean.NavigationPoint;
import org.csproject.model.bean.Tile;
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

    Field field;
    String outside, world;

    public static final String CHARACTERS_JSON = "/characters.json";
    @Autowired
    private ActorFactory actorFactory;

    public Actor createActor(String name, String type) {

        return actorFactory.createActor(name, type);
    }

    @Override
    //creates a static Field
    public Field getNewWorld() {
        outside = "outside";
        world = "world";
        field = new Field();

        field.setStartPoint("tileStart", new NavigationPoint(0, 0));
        Tile[][] matrix = new Tile[20][40];

        for(int i = 0; i < 20; ++i){
            for(int j = 0; j < 40; ++j){
                matrix[i][j] = new Tile(0,1, true, outside);
            }
        }
        for(int i = 0; i < 20; ++i){
            matrix[i][0] = new Tile(0,3, true, outside);
        }
        for(int i = 0; i < 10; ++i){
            matrix[i][1] = new Tile(13,6, false, outside);
        }

        matrix[7][7] = new Tile(7, 7, true, world);
        matrix[7][7].setTownTile();
        //setFieldBorders(matrix);
        field.setTiles(matrix);

        field.setStartPoint("characterStart",
                new NavigationPoint(((field.getTiles()[0].length -1)/2) * Constants.TILE_SIZE,
                        ((field.getTiles().length - 1 ) / 2) * Constants.TILE_SIZE));
        return field; // todo create the map (for example: from random world generator)
    }

    private void setFieldBorders(Tile[][] matrix) {
        for(int j = 0; j<40; ++j) {
            matrix[0][j] = new Tile( 11, 7, false, outside);
        }
    }

    /**
     * TODO
     * @return The current player actor
     */
    @Override
    public PlayerActor getPlayerActor() {
        // todo
        PlayerActor playerActor = new PlayerActor("Test Player", ActorFactory.KNIGHT,1, 5, 8, 100);
        return playerActor;
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

    @Override
    public Group getNode(Tile[][] matrix) {
        Group root = new Group();
        Image image = new Image("image/tiles/Outside.png");
        for(int rowIndex = 0; rowIndex < matrix.length ; rowIndex++) //vertikal durchs bild
        {
            Tile[] row = matrix[rowIndex];
            for(int colIndex = 0; colIndex < row.length; colIndex++) //horizontal durchs bild
            {
                Tile currentTile = row[colIndex];
                ImageView imageView = new ImageView(image);
                Rectangle2D viewPort = new Rectangle2D(currentTile.getX()* Tile.TILE_SIZE, currentTile.getY()*Tile.TILE_SIZE,Tile.TILE_SIZE, Tile.TILE_SIZE);
                imageView.setViewport(viewPort);
                imageView.setTranslateX(colIndex * Tile.TILE_SIZE);
                imageView.setTranslateY(rowIndex*Tile.TILE_SIZE);
                root.getChildren().add(imageView);

            }
        }
        return root;
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
