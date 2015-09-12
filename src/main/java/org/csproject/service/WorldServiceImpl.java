package org.csproject.service;

import org.csproject.controller.ActorFactory;
import org.csproject.model.actors.Actor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Maike Keune-Staab on 12.09.2015.
 */
@Component
public class WorldServiceImpl implements WorldService {

    @Autowired
    private ActorFactory actorFactory;

    public Actor createActor(String name, String type) {

        return actorFactory.createActor(name, type);
    }
}
