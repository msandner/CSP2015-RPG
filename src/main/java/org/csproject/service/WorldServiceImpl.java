package org.csproject.service;

import org.csproject.model.actors.Actor;
import org.csproject.model.actors.PlayerActor;
import org.csproject.model.bean.Field;
import org.csproject.model.bean.NavigationPoint;
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

    @Override
    public Field getNewWorld() {
        Field field = new Field();

        field.setStartPoint(new NavigationPoint(100, 100));

        return field; // todo create the map (for example: from random world generator)
    }

    /**
     * TODO
     * @return The current player actor
     */
    @Override
    public PlayerActor getPlayerActor() {
        // todo
        return new PlayerActor("Test player", ActorFactory.KNIGHT, 1) {
            @Override
            public int calcMp(int level) {
                return 0;
            }

            @Override
            public int calcHp(int level) {
                return 0;
            }
        };
    }
}
