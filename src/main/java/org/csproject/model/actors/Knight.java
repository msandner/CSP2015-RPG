package org.csproject.model.actors;

import org.csproject.service.ActorFactory;

/**
 * @author Maike Keune-Staab on 12.09.2015.
 */
public class Knight extends PlayerActor {
    public Knight(String name) {
        super(name, ActorFactory.KNIGHT, 1);
    }

    @Override
    public int calcHp(int level) {
        return 100 + level;
    }

    @Override
    public int calcMp(int level) {
        return (int) (10 + level * 0.5);
    }
}
