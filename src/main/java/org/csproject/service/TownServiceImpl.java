package org.csproject.service;

import org.csproject.model.actors.Actor;
import org.csproject.model.bean.Tile;
import org.csproject.model.bean.Town;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Brett on 10/19/2015.
 */
@Component
public class TownServiceImpl implements TownService {

    Town town;
    Tile[][] matrix;

    String inside, inside2, outside, outside2, house;
    //Static Town
    @Override
    public Town getNewTown() {
        town = new Town();

        inside = "inside";
        inside2 = "inside2";
        outside = "outside";
        outside2 = "outside2";
        house = "house";

        matrix = new Tile[20][40];

        //Shop floors
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++) {
                //Inside.png
                matrix[i][j] = new Tile(0, 7, true, inside);
            }
        }

        createTownWalkway();
        createTownWalls();
        createGrass();
        createStairs();
        createHouseTiles();

        town.setTiles(matrix);
        return town;
    }

    @Override
    public Actor createNPC(String name) {
        return null;
    }

    private void createTownWalkway() {
        //Top large horizontal walkway
        for(int i = 7; i < 9; i++) {
            for(int j = 0; j < 29; j++) {
                //Inside.png
                matrix[i][j] = new Tile(0, 10, true, inside);
            }
        }

        //Horizontal walkway at end of top Horizontal Walkway
        for(int i = 3; i < 7; i++) {
            for(int j = 27; j < 29; j++) {
                //Inside.png
                matrix[i][j] = new Tile(0, 10, true, inside);
            }
        }

        //Top small Horizontal Walkway
        for(int i = 3; i < 5; i++) {
            for(int j = 29; j < matrix[i].length; j++) {
                //Inside.png
                matrix[i][j] = new Tile(0, 10, true, inside);
            }
        }

        //Bottom Horizontal Walkway
        for(int i = 14; i < 16; i++) {
            for(int j = 0; j < matrix[i].length; j++) {
                //Inside.png
                matrix[i][j] = new Tile(0, 10, true, inside);
            }
        }

        //Main Vertical Walkway
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 15; j < 19; j++) {
                //Inside.png
                matrix[i][j] = new Tile(0, 10, true, inside);
            }
        }
    }

    private void createTownWalls() {
        //Top left Castle Wall
        for(int i = 4; i < 7; i++) {
            for(int j = 0; j < 14; j++) {
                //Inside2.png
                matrix[i][j] = new Tile(10, 3, false, inside2);
            }
        }

        //Right half Castle Wall
        for(int i = 4; i < 7; i++) {
            for(int j = 20; j < 27; j++) {
                //Inside2.png
                matrix[i][j] = new Tile(10, 3, false, inside2);
            }
        }

        //Vertical Strip of Castle Wall
        for(int i = 0; i < 4; i++) {
            //Inside2.png
            matrix[i][26] = new Tile(10, 3, false, inside2);
        }

        //Top right Castle Wall
        for(int i = 0; i < 3; i++) {
            for(int j = 27; j < matrix[i].length; j++) {
                //Inside2.png
                matrix[i][j] = new Tile(10, 3, false, inside2);
            }
        }

        //inbetween shops
        for(int i = 10; i < 13; i++) {
            //Inside2.png
            matrix[i][7] = new Tile(10, 3, false, inside2);
            for(int j = 26; j < 29; j++) {
                //Inside2.png
                matrix[i][j] = new Tile(10, 3, false, inside2);
            }
        }

        //Bottom town Walls
        for(int i = 18; i < 20; i++) {
            for(int j = 5; j < 7; j++) {
                //Inside2.png
                matrix[i][j] = new Tile(10, 3, false, inside2);
            }
            for(int j = 29; j < 32; j++) {
                //Inside2.png
                matrix[i][j] = new Tile(10, 3, false, inside2);
            }
        }
    }

    private void createGrass() {
        Tile grassTile = new Tile(0, 0, true, outside);
        //Top left area
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 15; j++) {
                //Outside.png
                matrix[i][j] = grassTile;
            }
            for(int j = 19; j < 26; j++) {
                matrix[i][j] = grassTile;
            }
        }

        //Outside.png
        matrix[5][19] = grassTile;
        matrix[5][14] = grassTile;

        //In front of Shops
        int y = 13;
        matrix[y][0] = grassTile;
        matrix[y][1] = grassTile;
        matrix[y][5] = grassTile;
        matrix[y][6] = grassTile;
        matrix[y][8] = grassTile;
        matrix[y][9] = grassTile;
        matrix[y][13] = grassTile;
        matrix[y][14] = grassTile;
        matrix[y][19] = grassTile;
        matrix[y][20] = grassTile;
        matrix[y][24] = grassTile;
        matrix[y][25] = grassTile;
    }

    private void createStairs() {
        //Stairs in the middle of town
        //Outside2.png
        matrix[13][15] = new Tile(0, 7, true, outside2);
        matrix[13][16] = new Tile(5, 7, true, outside2);
        matrix[13][17] = new Tile(6, 7, true, outside2);
    }

    /**
     * House tiles are twice the size of the normal tiles for the rest of the
     * images. Use of the modulo operator was used to accommodate for this.
     */
    private void createHouseTiles() {
        //House Line 1
        for (int y = 9; y < 13; y++) {
            //House 1
            for (int x = 0; x < 7; x++) {
                //Roof
                if (x % 2 == 0 && y == 9) {
                    matrix[y][x] = new Tile(4, 0, false, house);
                } else if (y == 9) {
                    matrix[y][x] = new Tile(5, 0, false, house);
                }
                //End Roof
                else {
                    if (x % 2 == 0) {
                        matrix[y][x] = new Tile(2, 2, false, house);
                    } else {
                        matrix[y][x] = new Tile(3, 2, false, house);
                    }
                }
            }
            //House 2
            for (int x = 8; x < 15; x++) {
                //Roof
                if (x % 2 == 0 && y == 9) {
                    matrix[y][x] = new Tile(12, 0, false, house);
                } else if (y == 9) {
                    matrix[y][x] = new Tile(13, 0, false, house);
                }
                //End Roof
                else {
                    if (x % 2 == 0) {
                        matrix[y][x] = new Tile(12, 2, false, house);
                    } else {
                        matrix[y][x] = new Tile(13, 2, false, house);
                    }
                }
            }
            //House 3
            for (int x = 19; x < 26; x++) {
                //Roof
                if (x % 2 == 0 && y == 9) {
                    matrix[y][x] = new Tile(0, 0, false, house);
                } else if (y == 9) {
                    matrix[y][x] = new Tile(1, 0, false, house);
                }
                //End Roof
                else {
                    if (x % 2 == 0) {
                        matrix[y][x] = new Tile(2, 2, false, house);
                    } else {
                        matrix[y][x] = new Tile(3, 2, false, house);
                    }
                }
            }
            //House 4
            for (int x = 34; x < 40; x++) {
                //Roof
                if (x % 2 == 0 && y == 9) {
                    matrix[y][x] = new Tile(12, 0, false, house);
                } else if (y == 9) {
                    matrix[y][x] = new Tile(13, 0, false, house);
                }
                //End Roof
                else {
                    if (x % 2 == 0) {
                        matrix[y][x] = new Tile(2, 2, false, house);
                    } else {
                        matrix[y][x] = new Tile(3, 2, false, house);
                    }
                }

            }
        }

        //House Line 2
        for (int y = 16; y < 20; y++) {
            //House 1
            for (int x = 0; x < 5; x++) {
                //Roof
                if (x % 2 == 0 && y == 16) {
                    matrix[y][x] = new Tile(8, 0, false, house);
                } else if (y == 16) {
                    matrix[y][x] = new Tile(9, 0, false, house);
                }
                //End Roof
                else {
                    if (x % 2 == 0) {
                        matrix[y][x] = new Tile(8, 2, false, house);
                    } else {
                        matrix[y][x] = new Tile(9, 2, false, house);
                    }
                }
            }
            //House 2
            for (int x = 7; x < 15; x++) {
                //Roof
                if (x % 2 == 0 && y == 16) {
                    matrix[y][x] = new Tile(10, 0, false, house);
                } else if (y == 16) {
                    matrix[y][x] = new Tile(11, 0, false, house);
                }
                //End Roof
                else {
                    if (x % 2 == 0) {
                        matrix[y][x] = new Tile(8, 2, false, house);
                    } else {
                        matrix[y][x] = new Tile(9, 2, false, house);
                    }
                }
            }
            //House 3
            for (int x = 19; x < 25; x++) {
                //Roof
                if (x % 2 == 0 && y == 16) {
                    matrix[y][x] = new Tile(6, 0, false, house);
                } else if (y == 16) {
                    matrix[y][x] = new Tile(7, 0, false, house);
                }
                //End Roof
                else {
                    if (x % 2 == 0) {
                        matrix[y][x] = new Tile(6, 2, false, house);
                    } else {
                        matrix[y][x] = new Tile(7, 2, false, house);
                    }
                }
            }
            //House 4
            for (int x = 25; x < 29; x++) {
                //Roof
                if (x % 2 == 0 && y == 16) {
                    matrix[y][x] = new Tile(2, 0, false, house);
                } else if (y == 16) {
                    matrix[y][x] = new Tile(3, 0, false, house);
                }
                //End Roof
                else {
                    if (x % 2 == 0) {
                        matrix[y][x] = new Tile(8, 2, false, house);
                    } else {
                        matrix[y][x] = new Tile(9, 2, false, house);
                    }
                }
            }

            //House 5
            for (int x = 32; x < 40; x++) {
                //Roof
                if (x % 2 == 0 && y == 16) {
                    matrix[y][x] = new Tile(10, 0, false, house);
                } else if (y == 16) {
                    matrix[y][x] = new Tile(11, 0, false, house);
                }
                //End Roof
                else {
                    if (x % 2 == 0) {
                        matrix[y][x] = new Tile(4, 2, false, house);
                    } else {
                        matrix[y][x] = new Tile(5, 2, false, house);
                    }
                }
            }
        }
        //Shop entrances
        //TODO: Maybe longer entrances
        matrix[12][3] = new Tile(11, 11, true, outside);
        matrix[12][11] = new Tile(11, 11, true, outside);
        matrix[12][22] = new Tile(11, 11, true, outside);
    }
}
