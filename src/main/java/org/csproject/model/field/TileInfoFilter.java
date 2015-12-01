package org.csproject.model.field;

/**
 * @author Maike Keune-Staab on 23.11.2015.
 */
public class TileInfoFilter {
    private TileInfo[][] filter;
    private int pivotX;
    private int pivotY;

    public TileInfoFilter(TileInfo[][] filter, int pivotX, int pivotY) {
        this.filter = filter;
        this.pivotX = pivotX;
        this.pivotY = pivotY;
    }

    public boolean matches(TileInfo[][] bsp, int posX, int posY) {

        if (bsp == null) {
            return false;
        }

        for (int row = 0; row < filter.length; row++) {
            for (int col = 0; col < filter[0].length; col++) {
                if(row+posY-pivotY < 0 || col+posX-pivotX < 0 || row+posY-pivotY >= bsp.length
                        || col+posX-pivotX >= bsp[0].length) {
                    return false;
                }

                if(filter[row][col] == null) {
                    if (bsp[row+posY-pivotY][col+posX-pivotX] != null) {
                        return false;
                    }
                }

                if(!filter[row][col].equals(bsp[row+posY-pivotY][col+posX-pivotX])) {
                    return false;
                }
            }
        }

        return true;
    }
}
