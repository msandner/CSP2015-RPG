package org.csproject.editor;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.csproject.model.bean.NavigationPoint;
import org.csproject.model.field.StartPoint;

/**
 * @author Maike Keune-Staab on 09.11.2015.
 */
public class StartPointSelection implements NavPointSelection {

    private TextField nameField;

    /**
     * Maike Keune-Staab
     * returns a panel to define a startPoint's parameter
     *
     * @return
     */
    @Override
    public Pane getPreferencesPanel() {
        VBox vBox = new VBox();

        Label label = new Label("Start-point-name: ");
        nameField = new TextField();
        vBox.getChildren().addAll(label, nameField);

        return vBox;
    }

    /**
     * Maike Keune-Staab
     * returns a new startPoint as defined by the getPreferencesPanel-method-
     *
     * @param x
     * @param y
     * @return
     */
    @Override
    public NavigationPoint getNavigationPoint(int x, int y) {
        return new StartPoint(x, y, nameField.getText());
    }

    /**
     * Maike Keune-Staab
     * @return
     */
    @Override
    public String toString() {
        return "Start point";
    }
}
