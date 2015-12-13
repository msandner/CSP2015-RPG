package org.csproject.model.field;

/**
 * @author Maike Keune-Staab on 23.11.2015.
 */
public class TileInfoFilter {
    private boolean overwriteGround;
    private boolean overwriteDeco;

    private int pivotX;
    private int pivotY;

    private TileInfo[][] filter;

    public TileInfoFilter(TileInfo[][] filter, int pivotX, int pivotY) {
        this.overwriteGround = false;
        this.overwriteDeco = false;
        this.pivotX = pivotX;
        this.pivotY = pivotY;
        this.filter = filter;
    }

    /**
     * Maike Keune-Staab
     * returns true, if the given position within the given bsp matches this filter at the pivot x/y position.
     * is used to find the place where certain tiles, rooms, tileChunks should be set.
     * @param bsp
     * @param posX
     * @param posY
     * @return
     */
    public boolean matches(TileInfo[][] bsp, int posX, int posY) {

        if (bsp == null) {
            return false;
        }

        for (int row = 0; row < filter.length; row++) {
            for (int col = 0; col < filter[0].length; col++) {
                if (row + posY - pivotY < 0 || col + posX - pivotX < 0 || row + posY - pivotY >= bsp.length
                        || col + posX - pivotX >= bsp[0].length) {
                    return false;
                }

                if (filter[row][col] != null &&
                        !filter[row][col].equals(bsp[row + posY - pivotY][col + posX - pivotX])) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Maike Keune-Staab
     * @param ground
     * @param row
     * @param col
     * @return
     */
    public boolean overwriteGround(Tile[][] ground, int row, int col) {
        if (overwriteGround) {
            return true;
        }

        return overwrite(ground, row, col);
    }

    /**
     * Maike Keune-Staab
     * @param deco
     * @param row
     * @param col
     * @return
     */
    public boolean overwriteDeco(Tile[][] deco, int row, int col) {
        if (overwriteDeco) {
            return true;
        }

        return overwrite(deco, row, col);
    }

    /**
     * Maike Keune-Staab
     * checks the entire filter area within the given tiles. If all tiles are null the method returns true.
     * @param tiles
     * @param row
     * @param col
     * @return
     */
    private boolean overwrite(Tile[][] tiles, int row, int col) {
        int startRow = row - pivotY;
        int startCol = col - pivotX;
        for (int posY = startRow; posY <= startRow + filter.length - 1; posY++) {
            if (posY >= 0 && posY < tiles.length) {
                Tile[] currentRow = tiles[posY];
                for (int posX = startCol; posX <= startCol + filter[posY - startRow].length - 1; posX++) {
                    if (posX >= 0 && posX < currentRow.length) {
                        if (currentRow[posX] != null) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
