package org.csproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import org.csproject.configuration.SpringConfiguration;
import org.csproject.model.bean.Field;
import org.csproject.model.bean.NavigationPoint;
import org.csproject.model.bean.Tile;
import org.csproject.model.bean.TileChunks;
import org.csproject.service.WorldService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.csproject.model.Constants.*;

/**
 * @author Maike Keune-Staab on 30.10.2015.
 */
public class JsonCreationUtil
{
  /**
   * TODO insert your stuff here. Doesn't matter which class, or whether it's from a tool with a fancy GUI, or simply hardcoded here. Just create it and this
   * util will save it as a json with your desired name
   * @return
   */
    public static Object getStuff() {

    // TODO delete this code and add your own here!

    // as an example, I created the worldmap here:
    Field field = new Field();
    field.setGroundImage("images/tiles/Outside.png");
    field.setDecoImage("images/tiles/World.png");

    field.setStartPoint("tileStart", new NavigationPoint(0, 0));
    Tile[][] ground = new Tile[20][40];

    for(int i = 0; i < 20; ++i){
        for(int j = 0; j < 40; ++j){
            ground[i][j] = new Tile(0,1, true);
        }
    }
    for(int i = 0; i < 20; ++i){
        ground[i][0] = new Tile(0,3, true);
    }
    for(int i = 0; i < 10; ++i){
        ground[i][1] = new Tile(13,6, false);
    }

    Tile[][] decoration = new Tile[20][40];

    decoration[7][7] = new Tile(7, 7, true);
    ground[7][7].setTownTile(); // todo

    TileChunks tchunk = new TileChunks();

    /*testing purposes*/
    tchunk.setTree4x4Random(decoration, 20, 40);
    tchunk.setTree4x4Random(decoration, 20, 40);

    //setFieldBorders(matrix);
    field.setGroundTiles(ground);
    field.setDecoTiles(decoration);

    field.setStartPoint("characterStart",
            new NavigationPoint(((field.getGroundTiles()[0].length -1)/2) * (int) TILE_SIZE,
                    ((field.getGroundTiles().length - 1 ) / 2) * (int) TILE_SIZE));

    return field;
}


    public static void main(String[] args) throws FileNotFoundException {

        // Start the spring application context
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);

        // application context
        WorldService world = context.getBean(WorldService.class);
        Gson gson = context.getBean(Gson.class);

        Object stuff = getStuff();
        String stuffAsJson = gson.toJson(stuff);

        saveFile(stuffAsJson, JSON_DIR + "filename" + JSON_POST_FIX); // todo: insert your json file name here without the extension
    }

  private static void saveFile(String fileName, String json) throws FileNotFoundException {
    PrintWriter writer = new PrintWriter(new File(JsonCreationUtil.class.getResource(fileName).getPath()));
    writer.print(json);
    writer.close();
    System.exit(0);
  }
}
