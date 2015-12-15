package org.csproject.model.bean;

import org.csproject.model.actors.Actor;

/**
 * Created by Brett on 11/9/2015.
 * Unimplemented as of 12/16/2015
 */
public class ChatPair {
    String actorType;
    String chat;

    /**
    * A ChatPair is used for the display of a character image next to its
    * text when you are talking to an NPC or some object that issues a chat
    * For reference: something like this image:
    * http://vignette3.wikia.nocookie.net/finalfantasy/images/2/2f/Krile_talking_to_Boko.jpg/revision/latest?cb=20130727185456
    */
    public ChatPair(Actor a, String s) {
        actorType = a.getType();
        chat = s;
    }

    public String getActorType() {
        return actorType;
    }

    public String getChat() {
        return chat;
    }
}
