package org.csproject.service;

import org.csproject.model.actors.Actor;
import org.csproject.model.bean.Town;

/**
 * Created by Brett on 10/19/2015.
 */
public interface TownService {

    Town getNewTown();

    Actor createNPC(String name);
}
