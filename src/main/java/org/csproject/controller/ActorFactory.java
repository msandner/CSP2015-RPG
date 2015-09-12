package org.csproject.controller;

import org.csproject.model.actors.Actor;
import org.csproject.model.actors.Knight;
import org.csproject.model.actors.Npc;
import org.springframework.stereotype.Component;

/**
 * @author Maike Keune-Staab on 12.09.2015.
 */
@Component
public class ActorFactory {

    // constants for the actors
    public static final String NPC = "npc";
    public static final String KNIGHT = "knight";

    public Actor createActor(String name, String type) {
        switch (type) {
            case NPC: {
                return new Npc(name);
            }
            case KNIGHT: {
                return new Knight(name);
            }
            default:{
                return null;
            }
        }
    }
}
