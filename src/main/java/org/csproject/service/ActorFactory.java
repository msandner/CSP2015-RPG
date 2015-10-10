package org.csproject.service;

import org.csproject.model.actors.*;
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
                return new Npc(name); //todo
            }
            case MAGE: {
                return new Npc(name); //todo
            }
            case THIEF: {
                return new Npc(name); //todo
            }
            default:{
                return null;
            }
        }
    }
}
