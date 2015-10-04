package org.csproject.service;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.csproject.model.bean.Field;
import org.springframework.stereotype.Component;

import java.awt.*;

/**
 * @author Maike Keune-Staab on 04.10.2015.
 */
@Component
public class ScreenFactory {
    // todo read field and build Jafa FX node according to the field
    public Node buildNode(Field field) {

        // empty test graph:
        Group group = new Group();

        Rectangle testField = new Rectangle(1280, 720);

        testField.setFill(new Color(0, 0.8, 0.1, 1.0));

        group.getChildren().add(testField);

        return group;
    }
}
