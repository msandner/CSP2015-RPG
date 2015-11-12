package org.csproject.service;

import org.csproject.model.Constants;
import org.csproject.model.bean.Field;
import org.csproject.model.bean.NavigationPoint;
import org.csproject.model.bean.StartPoint;
import org.csproject.model.bean.Tile;
import org.springframework.stereotype.Component;

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
    public static final Tile TREE = new Tile(6, 12, false);
    public static final Tile ROCK = new Tile(7, 12, false);
    public static final Tile STONEFLOOR = new Tile(0, 3,true);

    Boolean[][] bsp;

    //generates a field with a tiled image for the groundtiles and a tiled image for the decorations

    public Field generateNewField(String type) {
        if (type.equals("overworld")) {
            return generateField("Outside", "Outside3", GRASS, TREE);
        } else if (type.equals("dungeon")) {
            return generateField("Dungeon", "Outside3", STONEFLOOR, ROCK);
        } else {
            System.out.println("wrong type");
            return null;
        }
    }


    private Field generateField(String groundImage, String decoImage, Tile tileground, Tile tiledeco) {
        Field field = new Field();

        field.setGroundImage(groundImage);
        field.setDecoImage(decoImage);

        // Binary Space Partitioning
        this.bsp = getEmptyBsp(50, 50);
        this.bsp = createFullBsp(bsp, 5);

        Tile[][] groundTiles = createGround(bsp, tileground);
        Tile[][] decoTiles = createDeco(bsp, tiledeco);

        field.setGroundTiles(groundTiles);
        field.setDecoTiles(decoTiles);


        return field;
    }

    private NavigationPoint setNewStartPoint(Tile[][] ground) {
        NavigationPoint start = new StartPoint(0,0, "start");

        outerloop:
        for(int i = 0; i < ground[0].length; i++) {
            for(int j = 0; j < ground.length; j++) {
                if(ground[i][j].isWalkable()) {
                    start.setX(j);
                    start.setY(i);
                    System.out.println("x = "+ i + ", y = " + j);
                    break outerloop;
                }
            }
        }
        return start;
    }

    private Tile[][] createGround(Boolean[][] bsp, Tile a) {
        int width = bsp[0].length;
        int height = bsp.length;
        Tile[][] tiles = new Tile[height][width];
        for (int i = 0; i < height; i++) {
            tiles[i] = new Tile[width];
        }
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                tiles[row][col] = a;
            }
        }
        return tiles;
    }

    public Tile[][] createDeco(Boolean[][] bsp, Tile a) {
        int width = bsp[0].length;
        int height = bsp.length;
        Tile[][] tiles = new Tile[height][width];
        for (int row = 0; row < bsp.length; row++) {
            for (int col = 0; col < bsp[0].length; col++) {
                if(bsp[row][col] == null) {
                    tiles[row][col] = a;
                    System.out.println(tiles[row][col].isWalkable());
                }
            }
        }
        return tiles;
    }

    public Boolean[][] getEmptyBsp(int row, int col) {
        Boolean[][] bsp = new Boolean[row][col];
        for (int i_row = 0; row < bsp.length; i_row++) {
            //switched the row and column accidentaly, still works
            bsp[row] = new Boolean[col];
            for (int i_col = 0; col < bsp[i_row].length; i_col++) {
                bsp[i_row][i_col] = false;
            }
        }
        return bsp;
    }

    public Boolean[][] createFullBsp(Boolean[][] bsp, int count) {
        if(count == 0){
            return bsp;
        }

        int height = bsp.length;
        int width = bsp[0].length;

        // todo
//        if( width < DEAD_ZONE * 2 + 1 || height < DEAD_ZONE * 2 + 1)
//        {
//            return bsp;
//        }

        boolean splitHorizontally = (width >= height);

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
        else if(rnd >= (1.0 - DEAD_ZONE))
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

        block1 = createFullBsp(block1, count - 1);
        block2 = createFullBsp(block2, count - 1);

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

    public NavigationPoint setCharacterStartpositionInDungeon() {
        NavigationPoint pos = new NavigationPoint(0, 0);
        Boolean[][] matrix = this.bsp;
        //i runs along the rowcount
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0] != null) {
                pos.setX(i);
                pos.setY(0);
                break;
            }
            //j runs along the column count
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[0][j] != null) {
                    pos.setX(0);
                    pos.setY(j);
                    break;
                }
            }
        }
        return pos;
    }

}

