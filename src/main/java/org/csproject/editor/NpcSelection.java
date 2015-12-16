package org.csproject.editor;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.csproject.model.Constants;
import org.csproject.model.actors.Npc;
import org.csproject.model.general.NavigationPoint;

import java.util.Arrays;

/**
 * @author Maike Keune-Staab on 16.12.2015.
 */
public class NpcSelection implements NavPointSelection {

    private TextField nameField;
    private ChoiceBox<String> imageField;
    private NumberTextField blockXField;
    private NumberTextField blockYField;
    private TextField messageField;

    /**
     * Maike Keune-Staab
     * returns a panel to define a npc's parameter
     *
     * @return
     */
    @Override
    public Pane getPreferencesPanel() {
        VBox vBox = new VBox(5);
        ObservableList<Node> nodes = vBox.getChildren();
        nodes.add(new Label("Name"));
        nameField = new TextField();
        nodes.add(nameField);
        nodes.add(new Label("image"));
        imageField = new ChoiceBox<>();
        imageField.getItems().addAll(Arrays.asList(
                Constants.IMAGE_PEOPLE_1,
                Constants.IMAGE_PEOPLE_2,
                Constants.IMAGE_PEOPLE_3,
                Constants.IMAGE_PEOPLE_4,
                Constants.IMAGE_PEOPLE_5,
                Constants.IMAGE_PEOPLE_6,
                Constants.IMAGE_PEOPLE_7,
                Constants.IMAGE_PEOPLE_8,
                Constants.IMAGE_ACTOR_1));
        nodes.add(imageField);
        nodes.add(new Label("BlockX"));
        blockXField = new NumberTextField();
        nodes.add(blockXField);
        nodes.add(new Label("BlockY"));
        blockYField = new NumberTextField();
        nodes.add(blockYField);
        nodes.add(new Label("Message"));
        messageField = new TextField();
        nodes.add(messageField);
        return vBox;
    }

    /**
     * Maike Keune-Staab
     * returns a new npc as defined by the getPreferencesPanel-method-
     *
     * @param x
     * @param y
     * @return
     */
    @Override
    public NavigationPoint getNavigationPoint(int x, int y) {
        return new Npc(nameField.getText(), imageField.getValue(), blockXField.getNumber(), blockYField.getNumber(),
                messageField.getText(), x, y);
    }

    /**
     * Maike Keune-Staab
     * @return
     */
    @Override
    public String toString() {
        return "Npc";
    }
}
