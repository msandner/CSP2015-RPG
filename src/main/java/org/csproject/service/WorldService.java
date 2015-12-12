package org.csproject.service;

import java.io.FileNotFoundException;
import java.util.List;

import org.csproject.model.actors.PlayerActor;
import org.csproject.model.field.Field;

/**
 * @author Maike Keune-Staab on 12.09.2015.
 */
public interface WorldService {

    void setAvailableClasses(List<PlayerActor> playerActors) throws FileNotFoundException;

    Field getWorldMap();

    void setWorldMap(Field worldMap) throws FileNotFoundException;

    void saveField(Field field, String name) throws FileNotFoundException;

    Field getField(String fieldName);

    Field generateDungeon(DungeonHelper.Type type, int height, int width, int floors, String targetMap, String targetPoint, String sourceMap,
                          String sourcePoint);

    void setTempField(Field field, String tempFieldName);
}
