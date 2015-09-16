package org.csproject.model.actors;

import org.csproject.controller.ActorFactory;

/**
 * @author Maike Keune-Staab on 12.09.2015.
 */
public class Npc extends Actor {

    private String message;

    public Npc(String name) {
        super(name, ActorFactory.NPC);

        this.message = "Hi, I'm " + name + "."; // todo standard message or message generator
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

/*Npc is just a random actor, with random sentences. For example an arms dealer*/