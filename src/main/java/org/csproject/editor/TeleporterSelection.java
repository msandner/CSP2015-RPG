package org.csproject.editor;

import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.csproject.model.bean.NavigationPoint;
import org.csproject.model.bean.TeleportPoint;

/**
 * @author Maike Keune-Staab on 09.11.2015.
 */
public class TeleporterSelection implements NavPointSelection {

    private TextField targetFieldNameTF;
    private TextField targetTeleporterNameTF;

    @Override
    public Pane getPreferencesPanel() {
        VBox vBox = new VBox();

        targetFieldNameTF = new TextField();
        targetTeleporterNameTF = new TextField();
        Label description = new Label("please add a town name or dungeon name into the 'target-field-name' and " +
                "(optional) the name of the target start point into 'target-point-name'.");

        vBox.getChildren().addAll(description, new Separator(), new Label("Target-field-name: "),targetFieldNameTF,
                new Label("Target-point-name: "), targetTeleporterNameTF);

        return vBox;
    }

    @Override
    public NavigationPoint getNavigationPoint(int x, int y) {
        return new TeleportPoint(x, y, targetFieldNameTF.getText(), targetTeleporterNameTF.getText());
    }

    @Override
    public String toString() {
        return "Teleporter";
    }
}
