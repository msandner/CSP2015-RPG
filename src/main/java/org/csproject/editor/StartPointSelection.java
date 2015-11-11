package org.csproject.editor;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.csproject.model.bean.StartPoint;
import org.csproject.model.bean.NavigationPoint;

/**
 * @author Maike Keune-Staab on 09.11.2015.
 */
public class StartPointSelection implements NavPointSelection {

    private TextField nameField;

    @Override
    public Pane getPreferencesPanel() {
        VBox vBox = new VBox();

        Label label = new Label("Start-point-name: ");
        nameField = new TextField();
        vBox.getChildren().addAll(label, nameField);

        return vBox;
    }

    @Override
    public NavigationPoint getNavigationPoint(int x, int y) {
        return new StartPoint(x, y, nameField.getText());
    }

    @Override
    public String toString() {
        return "Start point";
    }
}
