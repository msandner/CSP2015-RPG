package org.csproject.controller;

import org.csproject.model.actors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author Maike Keune-Staab on 12.09.2015.
 */
@Component
public class ActorFactory {

    // constants for the actors
    public static final String NPC = "npc";
    public static final String KNIGHT = "knight";
    public static final String MAGE = "mage";
    public static final String THIEF = "thief";

    public Actor createActor(String name, String type) {
        switch (type) {
            case NPC: {
                return new Npc(name);
            }
            case KNIGHT: {
                return new Knight(name);
            }
            case MAGE: {
                return new Mage(name);
            }
            case THIEF: {
                return new Thief(name);
            }
            default:{
                return null;
            }
        }
    }
}
