package org.csproject.model.actors;

import org.csproject.service.ActorFactory;

/**
 * Created by Twigglon on 16.09.2015.
 */
public class Mage extends PlayerActor {
    public Mage(String name) {
        super(name, ActorFactory.MAGE, 1);
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
