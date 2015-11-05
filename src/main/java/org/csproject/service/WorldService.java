package org.csproject.service;

import java.io.FileNotFoundException;
import java.util.List;

import org.csproject.model.actors.Actor;
import org.csproject.model.actors.PlayerActor;
import org.csproject.model.bean.Field;

/**
 * @author Maike Keune-Staab on 12.09.2015.
 */
public interface WorldService {
    Actor createActor(String name, String type);

    void setPlayerActor();

    PlayerActor getPlayerActor();

    List<PlayerActor> getAvailableClasses();

    void setAvailableClasses(List<PlayerActor> playerActors) throws FileNotFoundException;

    Field getWorldMap();

    void setWorldMap(Field worldMap) throws FileNotFoundException;

    void setField(Field field, String name) throws FileNotFoundException;

    Field getField(String fieldName);

    Field generateField(String groundImage, String decoImage);

    Field generateDungeon(String groundImage, String decoImage);
}
