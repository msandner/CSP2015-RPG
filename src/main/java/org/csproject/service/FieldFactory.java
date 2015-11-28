package org.csproject.service;

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
    public static final int ENEMY_ENCOUNTER_PERCENTAGE = 10;

    public static int BSP_WIDTH = 50;
    public static int BSP_HEIGHT = 50;

    public static final Tile GRASS = new Tile(0,0, true);
    public static final Tile TREE = new Tile(6, 12, false);
    public static final Tile ROCK = new Tile(7, 11, false);
    public static final Tile STONEFLOOR = new Tile(0, 0,true);

    Boolean[][] bsp;

    //generates a field with a tiled image for the groundtiles and a tiled image for the decorations

    public Field generateNewField(String type) {
        if (type.equals("overworld")) {
            return generateField("Outside", "Outside3", GRASS, TREE);
        } else if (type.equals("dungeon")) {
            return generateField("Outside2", "Outside3", STONEFLOOR, ROCK);
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
        this.bsp = getEmptyBsp(BSP_WIDTH, BSP_HEIGHT);
        this.bsp = createFullBsp(bsp, 6);

        Tile[][] groundTiles = createGround(bsp, tileground);
        Tile[][] decoTiles = createDeco(bsp, tiledeco);

        field.setGroundTiles(groundTiles);
        field.setDecoTiles(decoTiles);

        field.getStartPoints().add((StartPoint) setNewStartPoint(decoTiles, groundTiles));

        return field;
    }


    private NavigationPoint setNewStartPoint(Tile[][] deco, Tile[][] ground) {
        NavigationPoint start = new StartPoint(0,0, "start");

        outerloop:
        for(int i = 0; i < deco.length; i++) {
            for(int j = 0; j < deco[0].length; j++) {
                if(deco[i][j] == null && ground[i][j] != null) {
                    start = new StartPoint(j, i, "start");
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
                }
            }
        }
        return tiles;
    }

    public Boolean[][] getEmptyBsp(int row, int col) {
        Boolean[][] bsp = new Boolean[row][col];
        for (int i_row = 0; i_row < bsp.length; i_row++) {
            //switched the row and column accidentaly, still works
            bsp[i_row] = new Boolean[col];
            for (int i_col = 0; i_col < bsp[i_row].length; i_col++) {
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

        boolean splitHorizontally = (width >= height);

        Boolean[][] block1;
        Boolean[][] block2;

        // calc the random factor
        double rndSum = 0.0;
        for (int i = 0; i < NORM_FACTOR; i++) {
            rndSum += Math.random();
        }

        double rnd = rndSum / NORM_FACTOR;
        if(rnd < DEAD_ZONE) {
            rnd = DEAD_ZONE;
        } else if(rnd >= (1.0 - DEAD_ZONE)) {
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
                for(int col = 0; col < width; ++col){
                    //if the current coordinate lies on the path between the first and the second block, its walkable (bsp =true)
                    boolean notAbovePath = row >= (height/2) - PATH_WIDTH / 2;
                    boolean notBelowPath = row < (height/2) + PATH_WIDTH / 2;
                    boolean leftOfPath = col < split / 2;
                    boolean rightOfPath = col >= width - (width - split) / 2;

                    if(notAbovePath && notBelowPath && !leftOfPath && !rightOfPath) {
                       // System.out.println("if -> first if: " + row + " " + col + " split: " + split);
                        bsp[row][col]=true;

                    }
                    else if (col < split){
                       // System.out.println("if -> second if: " + row + " "  + col + " split: " + split);
                        bsp[row][col]= block1[row][col];

                    }
                    else{
                        //System.out.println("if -> else: " + row + " " + col + " split: " + split);
                        bsp[row][col]= block2[row][col-split];
                    }
                }
            }
        } else {
            for(int row = 0; row < height; ++row){
                for(int col = 0; col <width; ++col){
                    //if the current coordinate lies on the path between the first and the second block, its walkable (bsp =true)
                    boolean notLeftOfPath = col >= (width/2) - PATH_WIDTH / 2;
                    boolean notRightOfPath = col < (width/2) + PATH_WIDTH / 2;
                    boolean abovePath = row < split / 2;
                    boolean belowPath = row >= height - (height - split) / 2;

                    if(notLeftOfPath && notRightOfPath && !abovePath && !belowPath) {
                        //System.out.println("else -> first if: " + row + " " + col + " split: " + split);
                        bsp[row][col]=true;
                    }
                    else if (row < split){
                        //System.out.println("else -> second if: " + row + " "  + col + " split: " + split);
                        bsp[row][col]= block1[row][col];

                    } else{
                       // System.out.println("else -> else: " + row + " " + col + " split: " + split);
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

