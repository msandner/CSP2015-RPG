package org.csproject.service;

import java.util.*;

import org.csproject.model.field.DungeonDetails;
import org.csproject.model.field.Field;
import org.csproject.model.field.StartPoint;
import org.csproject.model.field.TeleportPoint;
import org.csproject.model.field.Tile;
import org.csproject.model.field.TileInfo;
import org.csproject.model.field.TileInfoFilter;

import static org.csproject.model.Constants.*;

/**
 * @author Maike Keune-Staab on 26.11.2015.
 */
public class DungeonHelper {

    public static String RANDOM_START = "randomStart";
    public static String RANDOM_END = "randomEnd";

    public static DungeonDetails getDungeonDetails(Type type, int height, int width, int floors, String targetMap,
                                                   String targetPoint, String sourceMap,
                                                   String sourcePoint, WorldService worldService) {
        switch (type) {
            case TEST: {
                return getTestDungeonDetails(height, width, floors, targetMap, targetPoint, sourceMap, sourcePoint,
                        worldService);
            }
            default: {
                return null;
            }
        }
    }

    private static DungeonDetails getTestDungeonDetails(int height, int width, int floors, String targetMap,
                                                        String targetPoint, String sourceMap, String sourcePoint,
                                                        WorldService worldService) {
        Map<TileInfoFilter, List<Field>> tileMapping = new HashMap<>();

        Tile[][] ground = {{new Tile(2, 3, true, CS_INSIDE_1)}};
        tileMapping.put(new TileInfoFilter(new TileInfo[][]{{TileInfo.WALKABLE}}, 0, 0),
                Collections.singletonList(new Field(ground, null)));

        Field leftRoom = worldService.getField("leftRoom1");
        Field rightRoom = worldService.getField("rightRoom1");
        Field topRoom = worldService.getField("topRoom1");
        Field bottomRoom = worldService.getField("bottomRoom1");

        // set start points
        StartPoint startStart = new StartPoint(4, 4, RANDOM_START);
        TeleportPoint startTeleporter
                = new TeleportPoint(false, 4, 3, RANDOM_START, null, null, null, null, null, null, null, null);

        Collection<Field> startFields = new ArrayList<>();
        Field leftStart = worldService.getField("leftRoom1");
        Field rightStart = worldService.getField("rightRoom1");
        Field topStart = worldService.getField("topRoom1");
        Field bottomStart = worldService.getField("bottomRoom1");
        startFields.add(leftStart);
        startFields.add(rightStart);
        startFields.add(topStart);
        startFields.add(bottomStart);

        for (Field startField : startFields) {
            startField.getStartPoints().add(startStart);
            startField.getTeleportPoints().add(startTeleporter);
            startField.getDecoTiles()[3][4] = new Tile(12, 11, true, false, CS_OUTSIDE_3);
        }

        // set end points
        StartPoint endStart = new StartPoint(4, 5, RANDOM_END);
        TeleportPoint endTeleporter
                = new TeleportPoint(false, 4, 4, RANDOM_END, null, null, null, null, null, null, null, null);

        Collection<Field> endFields = new ArrayList<>();
        Field leftEnd = worldService.getField("leftRoom1");
        Field rightEnd = worldService.getField("rightRoom1");
        Field topEnd = worldService.getField("topRoom1");
        Field bottomEnd = worldService.getField("bottomRoom1");

        endFields.add(leftEnd);
        endFields.add(rightEnd);
        endFields.add(topEnd);
        endFields.add(bottomEnd);

        for (Field endField : endFields) {
            endField.getTeleportPoints().add(endTeleporter);
            endField.getStartPoints().add(endStart);
            endField.getDecoTiles()[4][4] = new Tile(12, 10, true, false, CS_OUTSIDE_3);
        }


        TileInfo[][] leftRoomFilter = getLeftFilter(TileInfo.ROOM_POS);
        TileInfo[][] RightRoomFilter = getRightFilter(TileInfo.ROOM_POS);
        TileInfo[][] topRoomFilter = getTopFilter(TileInfo.ROOM_POS);
        TileInfo[][] bottomRoomFilter = getBottomFilter(TileInfo.ROOM_POS);

        TileInfo[][] leftStartFilter = getLeftFilter(TileInfo.START_POS);
        TileInfo[][] RightStartFilter = getRightFilter(TileInfo.START_POS);
        TileInfo[][] topStartFilter = getTopFilter(TileInfo.START_POS);
        TileInfo[][] bottomStartFilter = getBottomFilter(TileInfo.START_POS);

        TileInfo[][] leftEndFilter = getLeftFilter(TileInfo.END_POS);
        TileInfo[][] RightEndFilter = getRightFilter(TileInfo.END_POS);
        TileInfo[][] topEndFilter = getTopFilter(TileInfo.END_POS);
        TileInfo[][] bottomEndFilter = getBottomFilter(TileInfo.END_POS);

        tileMapping.put(new TileInfoFilter(leftRoomFilter, -(int) leftRoom.getWidth() / 2,
                -(int) leftRoom.getHeight() / 2), Collections.singletonList(leftRoom));
        tileMapping.put(new TileInfoFilter(RightRoomFilter, -(int) rightRoom.getWidth() / 2 + 2,
                -(int) rightRoom.getHeight() / 2), Collections.singletonList(rightRoom));
        tileMapping.put(new TileInfoFilter(topRoomFilter, -(int) topRoom.getWidth() / 2,
                -(int) topRoom.getHeight() / 2 + 2), Collections.singletonList(topRoom));
        tileMapping.put(new TileInfoFilter(bottomRoomFilter, -(int) bottomRoom.getWidth() / 2,
                -(int) bottomRoom.getHeight() / 2 + 2), Collections.singletonList(bottomRoom));

        tileMapping.put(new TileInfoFilter(leftStartFilter, -(int) leftStart.getWidth() / 2,
                -(int) leftStart.getHeight() / 2), Collections.singletonList(leftStart));
        tileMapping.put(new TileInfoFilter(RightStartFilter, -(int) rightStart.getWidth() / 2 + 2,
                -(int) rightStart.getHeight() / 2), Collections.singletonList(rightStart));
        tileMapping.put(new TileInfoFilter(topStartFilter, -(int) topStart.getWidth() / 2,
                -(int) topStart.getHeight() / 2 + 2), Collections.singletonList(topStart));
        tileMapping.put(new TileInfoFilter(bottomStartFilter, -(int) bottomStart.getWidth() / 2,
                -(int) bottomStart.getHeight() / 2 + 2), Collections.singletonList(bottomStart));

        tileMapping.put(new TileInfoFilter(leftEndFilter, -(int) leftEnd.getWidth() / 2,
                -(int) leftEnd.getHeight() / 2), Collections.singletonList(leftEnd));
        tileMapping.put(new TileInfoFilter(RightEndFilter, -(int) rightEnd.getWidth() / 2 + 2,
                -(int) rightEnd.getHeight() / 2), Collections.singletonList(rightEnd));
        tileMapping.put(new TileInfoFilter(topEndFilter, -(int) topEnd.getWidth() / 2,
                -(int) topEnd.getHeight() / 2 + 2), Collections.singletonList(topEnd));
        tileMapping.put(new TileInfoFilter(bottomEndFilter, -(int) bottomEnd.getWidth() / 2,
                -(int) bottomEnd.getHeight() / 2 + 2), Collections.singletonList(bottomEnd));

        TileInfo[][] wideWallFilter = new TileInfo[][]{
                {TileInfo.BLOCKING, TileInfo.BLOCKING},
                {TileInfo.WALKABLE, TileInfo.WALKABLE}
        };
        Tile[][] wideWall = new Tile[][]{
                {new Tile(0, 3, false, CS_INSIDE_2), new Tile(1, 3, false, CS_INSIDE_2)},
                {new Tile(0, 4, false, CS_INSIDE_2), new Tile(1, 4, false, CS_INSIDE_2)}
        };
        TileInfo[][] wallFilter = new TileInfo[][]{
                {TileInfo.BLOCKING, TileInfo.BLOCKING},
                {TileInfo.WALKABLE, TileInfo.BLOCKING}
        };
        TileInfo[][] wallFilter2 = new TileInfo[][]{
                {TileInfo.BLOCKING, TileInfo.WALKABLE},
                {TileInfo.WALKABLE, TileInfo.WALKABLE}
        };
        Tile[][] wall = new Tile[][]{
                {new Tile(1, 3, false, CS_INSIDE_2)},
                {new Tile(1, 4, false, CS_INSIDE_2)}
        };
        tileMapping.put(new TileInfoFilter(wideWallFilter, 0, -1),
                Collections.singletonList(new Field(null, wideWall)));
        tileMapping.put(new TileInfoFilter(wallFilter, 0, -1), Collections.singletonList(new Field(null, wall)));
        tileMapping.put(new TileInfoFilter(wallFilter2, 0, -1), Collections.singletonList(new Field(null, wall)));

        return new DungeonDetails(height, width, floors, 12, 12, 4, CS_INSIDE_1, CS_INSIDE_1, targetMap, targetPoint, sourceMap,
                sourcePoint, tileMapping);
    }

    private static TileInfo[][] getBottomFilter(TileInfo roomPos) {
        return new TileInfo[][]{
                {TileInfo.WALKABLE},
                {TileInfo.WALKABLE},
                {TileInfo.WALKABLE},
                {roomPos}
        };
    }

    private static TileInfo[][] getTopFilter(TileInfo roomPos) {
        return new TileInfo[][]{
                {roomPos},
                {TileInfo.WALKABLE},
                {TileInfo.WALKABLE},
                {TileInfo.WALKABLE}
        };
    }

    private static TileInfo[][] getRightFilter(TileInfo roomPos) {
        return new TileInfo[][]{
                {TileInfo.WALKABLE, TileInfo.WALKABLE, TileInfo.WALKABLE, roomPos}
        };
    }

    private static TileInfo[][] getLeftFilter(TileInfo roomPos) {
        return new TileInfo[][]{
                {roomPos, TileInfo.WALKABLE, TileInfo.WALKABLE, TileInfo.WALKABLE}
        };
    }

    public enum Type {
        TEST
    }
}
