package org.csproject.controller;

import org.csproject.configuration.SpringConfiguration;
import org.csproject.service.HelloWorld;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Maike Keune-Staab on 12.09.2015.
 */
public class App {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
//        HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
        HelloWorld obj = context.getBean(HelloWorld.class);

        obj.printHelloWorld();
    }
}
