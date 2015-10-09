package org.csproject.service;

import org.csproject.model.actors.Actor;
import org.csproject.model.actors.PlayerActor;
import org.csproject.model.bean.Field;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * @author Maike Keune-Staab on 12.09.2015.
 */
public interface WorldService {
    Actor createActor(String name, String type);

    Field getNewWorld();

    PlayerActor getPlayerActor();

    List<PlayerActor> getAvailableClasses();

    void setAvailableClasses(List<PlayerActor> playerActors) throws FileNotFoundException;
}
