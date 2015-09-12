package org.csproject.service.impl;

import org.csproject.service.HelloWorld;
import org.springframework.stereotype.Component;

/**
 * @author Maike Keune-Staab on 12.09.2015.
 */
@Component
public class HelloWorldImpl implements HelloWorld {
    public void printHelloWorld() {
        System.out.println("Qui quam!");
    }
}
