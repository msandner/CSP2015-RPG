package org.csproject.model.bean;

import org.csproject.model.actors.Actor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brett on 11/9/2015.
 *
 * EventPoint is intended to be used as a point placed on the map
 * that, when talked to/used some event triggers.
 */
public class EventPoint extends NavigationPoint {

    List<ChatPair> chatEvent;

    public EventPoint(int x, int y, List<ChatPair> chat) {
        super(x, y);
        chatEvent = new ArrayList<ChatPair>();
        for (int i = 0; i < chat.size(); i++) {
            chatEvent.add(chat.get(i));
        }
    }

    public ChatPair nextChat(int index) {
        return chatEvent.get(index);
    }

    public List getChat() {
        return chatEvent;
    }
}
