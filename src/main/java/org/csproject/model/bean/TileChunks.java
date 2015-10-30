package org.csproject.model.bean;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.Random;

/**
 * Created by Maren on 24.10.2015.
 */
public class TileChunks {

    //creating 4x4x tile matrix, which should look like a bunch of trees out of outside3.png
    public Tile[][] Trees4x4() {

        String strimage = "outside3";
        Tile[][] treetiles = new Tile[4][4];

        //middle up left int the picture of the middle trees
        Tile miuple = new Tile(2, 14, false, strimage);
        //middle up right
        Tile miupri = new Tile(3, 14, false, strimage);
        //middle down left
        Tile midole = new Tile(2, 15, false, strimage);
        //middle down right
        Tile midori = new Tile(3, 15, false, strimage);

        //left down in the picture of the single tree
        Tile ledo = new Tile(0, 15, false, strimage);
        //right down
        Tile rido = new Tile(1, 15, false, strimage);
        //left up
        Tile leup = new Tile(0, 14, false, strimage);
        //right up
        Tile riup = new Tile(1, 14, false, strimage);

        //setting the tiles in the array
        treetiles[0][0] = leup;
        treetiles[0][1] = riup;
        treetiles[0][2] = leup;
        treetiles[0][3] = riup;

        treetiles[1][0] = ledo;
        treetiles[1][1] = midole;
        treetiles[1][2] = midori;
        treetiles[1][3] = rido;

        treetiles[2][0] = leup;
        treetiles[2][1] = miuple;
        treetiles[2][2] = miupri;
        treetiles[2][3] = riup;

        treetiles[3][0] = ledo;
        treetiles[3][1] = rido;
        treetiles[3][2] = ledo;
        treetiles[3][3] = rido;

        return treetiles;
        }

    //creating the trees randomly in the existing array of the field
    public Tile[][] setTree4x4Random(Tile[][] matrix, int boundx, int boundy) {
        Random rn = new Random();
        int i = rn.nextInt(boundx-3); //'-3' because I have to add +3 when calculating the position
        int j = rn.nextInt(boundy-3);
        Tile[][] treetest = Trees4x4();

        if(matrix[i][j] != treetest[0][0]) {
            //calculating the position
            matrix[i][j] = treetest[0][0];
            matrix[i][j + 1] = treetest[0][1];
            matrix[i][j + 2] = treetest[0][2];
            matrix[i][j + 3] = treetest[0][3];

            matrix[i + 1][j] = treetest[1][0];
            matrix[i + 1][j + 1] = treetest[1][1];
            matrix[i + 1][j + 2] = treetest[1][2];
            matrix[i + 1][j + 3] = treetest[1][3];

            matrix[i + 2][j] = treetest[2][0];
            matrix[i + 2][j + 1] = treetest[2][1];
            matrix[i + 2][j + 2] = treetest[2][2];
            matrix[i + 2][j + 3] = treetest[2][3];

            matrix[i + 3][j] = treetest[3][0];
            matrix[i + 3][j + 1] = treetest[3][1];
            matrix[i + 3][j + 2] = treetest[3][2];
            matrix[i + 3][j + 3] = treetest[3][3];
        } else {
            i = rn.nextInt(boundx);
            j = rn.nextInt(boundy);
            //not finished
        }
        //returning the matrix with the added trees
        return matrix;
    }

    }
