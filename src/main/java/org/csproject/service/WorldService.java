package org.csproject.service;

import org.csproject.model.actors.Actor;
import org.csproject.model.actors.PlayerActor;
import org.csproject.model.bean.Field;

/**
 * @author Maike Keune-Staab on 12.09.2015.
 */
public interface WorldService {
    Actor createActor(String name, String type);

    Field getNewWorld();

    PlayerActor getPlayerActor();
}
