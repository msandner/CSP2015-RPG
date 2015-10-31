package org.csproject.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.csproject.model.bean.Field;
import org.csproject.model.bean.Tile;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Fieldfactory is for randomly generating fields such as dungeons and future random fields
 *
 * @author Maike Keune-Staab on 31.10.2015.
 */
@Component
public class FieldFactory {

    public static final int NORM_FACTOR = 2;
    public static final int PATH_WIDTH = 2;
    public static final double DEAD_ZONE = 0.2;

    public static final Tile GRASS = new Tile(0,0, true);
    public static final Tile HOLE = new Tile(10,11, false);

    public Field generateDungeon(String groundImage, String decoImage, int colNum, int rowNum) {
        Field field = new Field();
        field.setGroundImage(groundImage);
        field.setDecoImage(decoImage);

        // Binary Space Partitioning
        Boolean[][] bsp = new Boolean[rowNum][colNum];
        for (int row = 0; row < bsp.length; row++) {
            bsp[row] = new Boolean[colNum];
            for (int col = 0; col < bsp[row].length; col++) {
                bsp[row][col] = false;

            }
        }
        bsp = createBsp(bsp, 4);

        Tile[][] groundTiles = createGround(bsp);

        field.setGroundTiles(groundTiles);

        //ToDo  deco tiles & start points
        return field;
    }

    private Tile[][] createGround(Boolean[][] bsp) {
        int width = bsp[0].length;
        int height = bsp.length;
        Tile[][] tiles = new Tile[height][width];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = new Tile[width];
        }
        for (int row = 0; row < bsp.length; row++) {
            for (int col = 0; col < bsp[row].length; col++) {
                tiles[row][col] = (bsp[row][col]== null || !bsp[row][col])? HOLE:GRASS;
            }
        }
        return tiles;
    }

    private Boolean[][] createBsp(Boolean[][] bsp, int count) {

        if(count == 0){
            return bsp; // todo raum rein schneiden
        }

        int width = bsp[0].length;
        int height = bsp.length;

        // todo
//        if( width < DEAD_ZONE * 2 + 1 || height < DEAD_ZONE * 2 + 1)
//        {
//            return bsp;
//        }

        boolean splitHorizontally = width >= height;

        Boolean[][] block1;
        Boolean[][] block2;

        // calc the random factor
        double rndSum = 0.0;
        for (int i = 0; i < NORM_FACTOR; i++) {

            rndSum += Math.random();
        }
        double rnd = rndSum / NORM_FACTOR;
        if(rnd < DEAD_ZONE)
        {
            rnd = DEAD_ZONE;
        }
        else if(rnd >= 1.0 - DEAD_ZONE)
        {
            rnd = 1.0 - DEAD_ZONE;
        }

        int split = (int) (width*rnd);
        if(splitHorizontally) {
            block1 = new Boolean[height][split];
            block2 = new Boolean[height][width - split];
        }
        else {
            block1 = new Boolean[split][width];
            block2 = new Boolean[height - split][width];
        }

        block1 = createBsp(block1, count-1);
        block2 = createBsp(block2, count-1);

        if(splitHorizontally){
            for(int row = 0; row < height; ++row){
                for(int col = 0; col <width; ++col){
                    //if the current coordinate lies on the path between the first and the second block, its walkable (bsp =true)
                    boolean notAbovePath = row >= (height/2) - PATH_WIDTH / 2;
                    boolean notBelowPath = row < (height/2) + PATH_WIDTH / 2;
                    boolean leftOfPath = col < split / 2;
                    boolean rightOfPath = col >= width - (width - split) / 2;

                    if(notAbovePath && notBelowPath && !leftOfPath && !rightOfPath) {
                        bsp[row][col]=true;
                    }
                    else if (col < split){
                        bsp[row][col]= block1[row][col];
                    }
                    else{
                        bsp[row][col]= block2[row][col-split];
                    }
                }
            }
        }
        else{
            for(int row = 0; row < height; ++row){
                for(int col = 0; col <width; ++col){
                    //if the current coordinate lies on the path between the first and the second block, its walkable (bsp =true)
                    boolean notLeftOfPath = col >= (width/2) - PATH_WIDTH / 2;
                    boolean notRightOfPath = col < (width/2) + PATH_WIDTH / 2;
                    boolean abovePath = row < split / 2;
                    boolean belowPath = row >= height - (height - split) / 2;

                    if(notLeftOfPath && notRightOfPath && !abovePath && !belowPath) {
                        bsp[row][col]=true;
                    }
                    else if (row < split){
                        bsp[row][col]= block1[row][col];
                    }
                    else{
                        bsp[row][col]= block2[row-split][col];
                    }
                }
            }
        }
        return bsp;
    }
}
