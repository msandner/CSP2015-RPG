package org.csproject.model.actors;

import org.csproject.controller.ActorFactory;

/**
 * Created by Twigglon on 16.09.2015.
 */
public class Thief extends PlayerActors {
    public Thief(String name) {
        super(name, ActorFactory.THIEF, 1);
    }

    @Override
    public int calcMp(int level) {
        return 0;
    }

    @Override
    public int calcHp(int level) {
        return 0;
    }
}
