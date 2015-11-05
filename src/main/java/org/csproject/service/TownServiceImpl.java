package org.csproject.service;

import java.io.FileNotFoundException;

import com.google.gson.Gson;
import org.csproject.model.actors.Actor;
import org.csproject.model.actors.PlayerActor;
import org.csproject.model.bean.Town;
import org.csproject.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.csproject.model.Constants.*;

/**
 * Created by Brett on 10/19/2015.
 */
@Component
public class TownServiceImpl implements TownService {

    @Autowired
    private ActorFactory actorFactory;

    @Autowired
    private Gson gson;

    @Override
    public Actor createActor(String name, String type) {
        return actorFactory.createActor(name, type);
    }

    @Override
    public PlayerActor getPlayerActor() {
        PlayerActor playerActor = new PlayerActor("Character Name", ActorFactory.KNIGHT, 1, 17, 18);
        return playerActor;
    }

    @Override
    public Town getTownMap() {
        return getTown(TOWN_1);
    }

    @Override
    public void setTownMap(Town townMap) throws FileNotFoundException {
        setTown(townMap, TOWN_1);
    }

    @Override
    public void setTown(Town town, String name) throws FileNotFoundException {
        String json = gson.toJson(town);
        Utilities.saveFile(JSON_DIR + name + JSON_POST_FIX, json);
    }

    @Override
    public Town getTown(String townName) {
        String json = Utilities.getFile(JSON_DIR + townName + JSON_POST_FIX);
        Town town = gson.fromJson(json, Town.class);
        return town;
    }
}
