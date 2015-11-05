package org.csproject.service;

import org.csproject.model.actors.Actor;
import org.csproject.model.actors.PlayerActor;
import org.csproject.model.bean.Town;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by Brett on 10/19/2015.
 */
public interface TownService {
    Actor createActor(String name, String type);

    PlayerActor getPlayerActor();

    Town getTownMap();

    void setTownMap(Town townMap) throws FileNotFoundException;

    void setTown(Town town, String name) throws FileNotFoundException;

    Town getTown(String townName);
}
