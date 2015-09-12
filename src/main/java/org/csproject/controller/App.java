package org.csproject.controller;

import org.apache.log4j.Logger;
import org.csproject.configuration.SpringConfiguration;
import org.csproject.model.actors.Npc;
import org.csproject.service.WorldService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Maike Keune-Staab on 12.09.2015.
 */
public class App {
    private final static Logger LOG = Logger.getLogger(App.class);

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);

        // example to proof the application context
        WorldService world = context.getBean(WorldService.class);

        Npc harry = (Npc) world.createActor("Harry", ActorFactory.NPC);

        LOG.info(harry.getMessage());
    }
}
