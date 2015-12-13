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

    /**
     * Maike Keune-Staab
     * creates the dungeonDetails according to the given Type and parameters
     * @param type
     * @param height
     * @param width
     * @param floors
     * @param targetMap
     * @param targetPoint
     * @param sourceMap
     * @param sourcePoint
     * @param worldService
     * @return
     */
    public static DungeonDetails getDungeonDetails(Type type, int height, int width, int floors, String targetMap,
                                                   String targetPoint, String sourceMap,
                                                   String sourcePoint, WorldService worldService) {
        switch (type) {
            case TEST: {
                return getTestDungeonDetails(height, width, floors, targetMap, targetPoint, sourceMap, sourcePoint,
                        worldService);
            }
            case WOOD: {
                return getWoodDungeonDetails(height, width, floors, targetMap, targetPoint, sourceMap, sourcePoint,
                        worldService);
            }
            default: {
                return null;
            }
        }
    }

    /**
     * Maike Keune-Staab
     * creates dungeon details for a test dungeon (looks like a basement)
     * @param height
     * @param width
     * @param floors
     * @param targetMap
     * @param targetPoint
     * @param sourceMap
     * @param sourcePoint
     * @param worldService
     * @return
     */
    private static DungeonDetails getTestDungeonDetails(int height, int width, int floors, String targetMap,
                                                        String targetPoint, String sourceMap, String sourcePoint,
                                                        WorldService worldService) {
        Map<TileInfoFilter, List<Field>> tileMapping = new HashMap<>();
        List<TileInfoFilter> filterOrder = new ArrayList<>();

        Field leftRoom = worldService.getField("test/leftRoom1");
        Field rightRoom = worldService.getField("test/rightRoom1");
        Field topRoom = worldService.getField("test/topRoom1");
        Field bottomRoom = worldService.getField("test/bottomRoom1");

        // set start points
        StartPoint startStart = new StartPoint(4, 4, RANDOM_START);
        TeleportPoint startTeleporter
                = new TeleportPoint(false, 4, 3, RANDOM_START, null, null, null, null, null, null, null, null);

        Collection<Field> startFields = new ArrayList<>();
        Field leftStart = worldService.getField("test/leftRoom1");
        Field rightStart = worldService.getField("test/rightRoom1");
        Field topStart = worldService.getField("test/topRoom1");
        Field bottomStart = worldService.getField("test/bottomRoom1");
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
        Field leftEnd = worldService.getField("test/leftRoom1");
        Field rightEnd = worldService.getField("test/rightRoom1");
        Field topEnd = worldService.getField("test/topRoom1");
        Field bottomEnd = worldService.getField("test/bottomRoom1");

        endFields.add(leftEnd);
        endFields.add(rightEnd);
        endFields.add(topEnd);
        endFields.add(bottomEnd);

        for (Field endField : endFields) {
            endField.getTeleportPoints().add(endTeleporter);
            endField.getStartPoints().add(endStart);
            endField.getDecoTiles()[4][4] = new Tile(12, 10, true, false, CS_OUTSIDE_3);
        }

        TileInfoFilter leftRoomFilter = getLeftFilter(TileInfo.ROOM_POS, 4, leftRoom.getWidth(), leftRoom.getHeight());
        TileInfoFilter rightRoomFilter = getRightFilter(TileInfo.ROOM_POS, 4, rightRoom.getWidth(), rightRoom.getHeight());
        TileInfoFilter topRoomFilter = getTopFilter(TileInfo.ROOM_POS, 4, topRoom.getWidth(), topRoom.getHeight());
        TileInfoFilter bottomRoomFilter = getBottomFilter(TileInfo.ROOM_POS, 4, bottomRoom.getWidth(), bottomRoom.getHeight());

        TileInfoFilter leftStartFilterInfo = getLeftFilter(TileInfo.START_POS, 4, leftRoom.getWidth(), leftRoom.getHeight());
        TileInfoFilter rightStartFilterInfo = getRightFilter(TileInfo.START_POS, 4, rightRoom.getWidth(), rightRoom.getHeight());
        TileInfoFilter topStartFilter = getTopFilter(TileInfo.START_POS, 4, topRoom.getWidth(), topRoom.getHeight());
        TileInfoFilter bottomStartFilter = getBottomFilter(TileInfo.START_POS, 4, bottomRoom.getWidth(), bottomRoom.getHeight());

        TileInfoFilter leftEndFilterInfo = getLeftFilter(TileInfo.END_POS, 4, leftRoom.getWidth(), leftRoom.getHeight());
        TileInfoFilter rightEndFilterInfo = getRightFilter(TileInfo.END_POS, 4, rightRoom.getWidth(), rightRoom.getHeight());
        TileInfoFilter topEndFilter = getTopFilter(TileInfo.END_POS, 4, topRoom.getWidth(), topRoom.getHeight());
        TileInfoFilter bottomEndFilter = getBottomFilter(TileInfo.END_POS, 4, bottomRoom.getWidth(), bottomRoom.getHeight());

        tileMapping.put(leftRoomFilter, Collections.singletonList(leftRoom));
        tileMapping.put(rightRoomFilter, Collections.singletonList(rightRoom));
        tileMapping.put(topRoomFilter, Collections.singletonList(topRoom));
        tileMapping.put(bottomRoomFilter, Collections.singletonList(bottomRoom));

        tileMapping.put(leftStartFilterInfo, Collections.singletonList(leftStart));
        tileMapping.put(rightStartFilterInfo, Collections.singletonList(rightStart));
        tileMapping.put(topStartFilter, Collections.singletonList(topStart));
        tileMapping.put(bottomStartFilter, Collections.singletonList(bottomStart));

        tileMapping.put(leftEndFilterInfo, Collections.singletonList(leftEnd));
        tileMapping.put(rightEndFilterInfo, Collections.singletonList(rightEnd));
        tileMapping.put(topEndFilter, Collections.singletonList(topEnd));
        tileMapping.put(bottomEndFilter, Collections.singletonList(bottomEnd));

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
        TileInfoFilter wideWallFilterInfo = new TileInfoFilter(wideWallFilter, 0, -1);
        TileInfoFilter wallFilterInfo = new TileInfoFilter(wallFilter, 0, -1);
        TileInfoFilter wall2FilterInfo = new TileInfoFilter(wallFilter2, 0, -1);

        tileMapping.put(wideWallFilterInfo,
                Collections.singletonList(new Field(null, wideWall)));
        tileMapping.put(wallFilterInfo, Collections.singletonList(new Field(null, wall)));
        tileMapping.put(wall2FilterInfo, Collections.singletonList(new Field(null, wall)));

        Tile[][] ground = {{new Tile(2, 3, true, CS_INSIDE_1)}};
        TileInfoFilter singleWalkable = new TileInfoFilter(new TileInfo[][]{{TileInfo.WALKABLE}}, 0, 0);
        tileMapping.put(singleWalkable,Collections.singletonList(new Field(ground, null)));


        filterOrder.add(leftRoomFilter);
        filterOrder.add(rightRoomFilter);
        filterOrder.add(topRoomFilter);
        filterOrder.add(bottomRoomFilter);
        filterOrder.add(leftStartFilterInfo);
        filterOrder.add(rightStartFilterInfo);
        filterOrder.add(topStartFilter);
        filterOrder.add(bottomStartFilter);
        filterOrder.add(leftEndFilterInfo);
        filterOrder.add(rightEndFilterInfo);
        filterOrder.add(topEndFilter);
        filterOrder.add(bottomEndFilter);

        filterOrder.add(wideWallFilterInfo);
        filterOrder.add(wallFilterInfo);
        filterOrder.add(wall2FilterInfo);

        filterOrder.add(singleWalkable);

        return new DungeonDetails(height, width, floors, 12, 12, 4, CS_INSIDE_1, CS_INSIDE_1, targetMap, targetPoint, sourceMap,
                sourcePoint, tileMapping, filterOrder);
    }

    /**
     * Maike Keune-Staab
     * creates the dungeon details for a wood type dungeon.
     * @param height
     * @param width
     * @param floors
     * @param targetMap
     * @param targetPoint
     * @param sourceMap
     * @param sourcePoint
     * @param worldService
     * @return
     */
    private static DungeonDetails getWoodDungeonDetails(int height, int width, int floors, String targetMap,
                                                        String targetPoint, String sourceMap, String sourcePoint,
                                                        WorldService worldService) {
        int pathWidth = 6;

        List<TileInfoFilter> filterOrder = new ArrayList<>();
        Map<TileInfoFilter, List<Field>> tileMapping = new HashMap<>();

        Field topStartField = worldService.getField("wood/upperEntrance");
        Field bottomStartField = worldService.getField("wood/downerEntrance");
        Field topEndField = worldService.getField("wood/upperEntrance");
        Field bottomEndField = worldService.getField("wood/downerEntrance");

        int roomWidth = 8;
        int roomHeight = 8;

        setPointNames(topStartField, RANDOM_START);
        setPointNames(bottomStartField, RANDOM_START);
        setPointNames(topEndField, RANDOM_END);
        setPointNames(bottomEndField, RANDOM_END);

        TileInfoFilter topStart = getTopFilter(TileInfo.START_POS, pathWidth, roomWidth, roomHeight);
        TileInfoFilter bottomStart = getBottomFilter(TileInfo.START_POS, pathWidth, roomWidth, roomHeight);
        TileInfoFilter rightStart = getRightFilter(TileInfo.START_POS, pathWidth, roomWidth, roomHeight);
        TileInfoFilter leftStart = getLeftFilter(TileInfo.START_POS, pathWidth, roomWidth, roomHeight);

        TileInfoFilter topEnd = getTopFilter(TileInfo.END_POS, pathWidth, roomWidth, roomHeight);
        TileInfoFilter bottomEnd = getBottomFilter(TileInfo.END_POS, pathWidth, roomWidth, roomHeight);
        TileInfoFilter rightEnd = getRightFilter(TileInfo.END_POS, pathWidth, roomWidth, roomHeight);
        TileInfoFilter leftEnd = getLeftFilter(TileInfo.END_POS, pathWidth, roomWidth, roomHeight);

        tileMapping.put(topStart, Collections.singletonList(topStartField));
        tileMapping.put(bottomStart, Collections.singletonList(bottomStartField));
        tileMapping.put(rightStart, Collections.singletonList(topStartField));
        tileMapping.put(leftStart, Collections.singletonList(topStartField));
        tileMapping.put(topEnd, Collections.singletonList(topEndField));
        tileMapping.put(bottomEnd, Collections.singletonList(bottomEndField));
        tileMapping.put(rightEnd, Collections.singletonList(topEndField));
        tileMapping.put(leftEnd, Collections.singletonList(topEndField));

        filterOrder.addAll(Arrays.asList(topStart, bottomStart, rightStart, leftStart, topEnd, bottomEnd, rightEnd,
                leftEnd));

        // common tiles
        Tile grass = new Tile(0, 0, true, true, CS_OUTSIDE_1);

        // 4x4 filter
        TileInfoFilter blocking4x4Filter = new TileInfoFilter(new TileInfo[][]{
                {null,              TileInfo.BLOCKING, TileInfo.BLOCKING,           null},
                {TileInfo.BLOCKING, TileInfo.BLOCKING, TileInfo.BLOCKING, TileInfo.BLOCKING},
                {TileInfo.BLOCKING, TileInfo.BLOCKING, TileInfo.BLOCKING, TileInfo.BLOCKING},
                {null,              TileInfo.BLOCKING, TileInfo.BLOCKING,           null}
        }, 0, 0);

        Tile[][] fourTreeGround = new Tile[][]{
                {grass, grass},
                {grass, grass}
        };
        Tile[][] fourTrees = new Tile[][]{
                {null, new Tile(0, 14, true, false, CS_OUTSIDE_3), new Tile(1, 14, true, false, CS_OUTSIDE_3), null},
                {new Tile(0, 14, true, false, CS_OUTSIDE_3), new Tile(2, 14, true, false, CS_OUTSIDE_3),
                        new Tile(3, 14, true, false, CS_OUTSIDE_3), new Tile(1, 14, true, false, CS_OUTSIDE_3)},
                {new Tile(0, 15, true, false, CS_OUTSIDE_3), new Tile(2, 15, true, false, CS_OUTSIDE_3),
                        new Tile(3, 15, true, false, CS_OUTSIDE_3), new Tile(1, 15, true, false, CS_OUTSIDE_3)},
                {null, new Tile(0, 15, true, false, CS_OUTSIDE_3), new Tile(1, 15, true, false, CS_OUTSIDE_3), null}
        };
        List<Field> fourTreesField = Collections.singletonList(new Field(fourTreeGround, fourTrees));
        tileMapping.put(blocking4x4Filter, fourTreesField);
        filterOrder.add(blocking4x4Filter);

        // 2x2 filter
        Tile[][] treeGround = new Tile[][]{
                {grass, grass},
                {grass, grass}
        };
        Tile[][] singleTree = new Tile[][]{
                {new Tile(0, 14, true, false, CS_OUTSIDE_3), new Tile(1, 14, false, false, CS_OUTSIDE_3)},
                {new Tile(0, 15, true, false, CS_OUTSIDE_3), new Tile(1, 15, false, false, CS_OUTSIDE_3)}
        };

        List<Field> single2x2TreeField = Collections.singletonList(new Field(treeGround, singleTree));
        TileInfoFilter blocking2x2Filter = new TileInfoFilter(new TileInfo[][]{
                {TileInfo.BLOCKING, TileInfo.BLOCKING},
                {TileInfo.BLOCKING, TileInfo.BLOCKING},
        }, 0, 0);
        tileMapping.put(blocking2x2Filter, single2x2TreeField);
        filterOrder.add(blocking2x2Filter);

        Field singleGrassGround = new Field(new Tile[][]{{new Tile(0, 0, true, false, CS_OUTSIDE_1)}}, null);

        List<Field> groundFields = Collections.singletonList(singleGrassGround);

        TileInfoFilter singleWalkableField = new TileInfoFilter(new TileInfo[][]{{TileInfo.WALKABLE}}, 0, 0);
        TileInfoFilter singleBlockingField = new TileInfoFilter(new TileInfo[][]{{TileInfo.BLOCKING}}, 0, 0);

        tileMapping.put(singleWalkableField, groundFields);
        filterOrder.add(singleWalkableField);
        tileMapping.put(singleBlockingField, groundFields);
        filterOrder.add(singleBlockingField);

        return new DungeonDetails(height, width, floors, 12, 12, pathWidth, CS_INSIDE_1, CS_INSIDE_1, targetMap, targetPoint, sourceMap, sourcePoint, tileMapping,
                filterOrder);
    }

    /**
     * Maike Keune-Staab
     * iterates over all teleportPoints and startPoints of the given fields and renames them as the given name.
     * @param field
     * @param name
     */
    private static void setPointNames(Field field, String name) {
        for (TeleportPoint teleportPoint : field.getTeleportPoints()) {
            teleportPoint.setName(name);
        }
        for (StartPoint startPoint : field.getStartPoints()) {
            startPoint.setName(name);
        }
    }

    /**
     * Maike Keune-Staab
     * returns a linear filter which finds a room that is at the bottom end of a corridor.
     * @param roomPos
     * @param pathWidth
     * @param roomWidth
     * @param roomHeight
     * @return
     */
    private static TileInfoFilter getBottomFilter(TileInfo roomPos, int pathWidth, double roomWidth, double roomHeight) {
        TileInfo[][] tileInfos = new TileInfo[pathWidth / 2 + 2][];

        for (int i = 0; i < tileInfos.length - 1; i++) {
            tileInfos[i] = new TileInfo[]{TileInfo.WALKABLE};
        }

        tileInfos[pathWidth / 2 + 1] = new TileInfo[]{roomPos};

        return new TileInfoFilter(tileInfos, -(int) roomWidth / 2, -(int) roomHeight / 2 + 2);
    }

    /**
     * Maike Keune-Staab
     * returns a linear filter which finds a room that is at the top end of a corridor.
     * @param roomPos
     * @param pathWidth
     * @param roomWidth
     * @param roomHeight
     * @return
     */
    private static TileInfoFilter getTopFilter(TileInfo roomPos, int pathWidth, double roomWidth, double roomHeight) {
        TileInfo[][] tileInfos = new TileInfo[pathWidth / 2 + 2][];

        tileInfos[0] = new TileInfo[]{roomPos};

        for (int i = 1; i < tileInfos.length; i++) {
            tileInfos[i] = new TileInfo[]{TileInfo.WALKABLE};
        }

        return new TileInfoFilter(tileInfos, -(int) roomWidth / 2, -(int) roomHeight / 2 + 2);
    }

    /**
     * Maike Keune-Staab
     * returns a linear filter which finds a room that is at the right end of a corridor.
     * @param roomPos
     * @param pathWidth
     * @param roomWidth
     * @param roomHeight
     * @return
     */
    private static TileInfoFilter getRightFilter(TileInfo roomPos, int pathWidth, double roomWidth, double roomHeight) {
        TileInfo[][] tileInfos = new TileInfo[1][pathWidth / 2 + 2];

        for (int i = 0; i < tileInfos[0].length - 1; i++) {
            tileInfos[0][i] = TileInfo.WALKABLE;
        }
        tileInfos[0][pathWidth / 2 + 1] = roomPos;

        return new TileInfoFilter(tileInfos, -(int) roomWidth / 2 + 2, -(int) roomHeight / 2);
    }

    /**
     * Maike Keune-Staab
     * returns a linear filter which finds a room that is at the left end of a corridor.
     * @param roomPos
     * @param pathWidth
     * @param roomWidth
     * @param roomHeight
     * @return
     */
    private static TileInfoFilter getLeftFilter(TileInfo roomPos, int pathWidth, double roomWidth, double roomHeight) {
        TileInfo[][] tileInfos = new TileInfo[1][pathWidth / 2 + 2];

        tileInfos[0][0] = roomPos;

        for (int i = 1; i < tileInfos[0].length; i++) {
            tileInfos[0][i] = TileInfo.WALKABLE;
        }

        return new TileInfoFilter(tileInfos, -(int) roomWidth / 2, -(int) roomHeight / 2);
    }

    public enum Type {
        TEST, WOOD
    }
}
