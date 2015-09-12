package org.csproject.service;

import org.csproject.model.actors.Actor;

/**
 * @author Maike Keune-Staab on 12.09.2015.
 */
public interface WorldService {
    Actor createActor(String name, String type);
}
