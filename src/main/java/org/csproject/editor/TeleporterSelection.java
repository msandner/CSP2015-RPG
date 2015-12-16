package org.csproject.editor;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.csproject.model.general.NavigationPoint;
import org.csproject.model.field.TeleportPoint;
import org.csproject.service.DungeonHelper;

/**
 * @author Maike Keune-Staab on 09.11.2015.
 */
public class TeleporterSelection implements NavPointSelection {
    private boolean randomDungeonTarget = false;

    private ComboBox<DungeonHelper.Type> typeComboBox;

    private TextField nameTF;
    private TextField targetFieldNameTF;
    private TextField targetTeleporterNameTF;
    private TextField sourceFieldNameTF;
    private TextField sourceTeleporterNameTF;

    private NumberTextField heightTF;
    private NumberTextField widthTF;
    private NumberTextField floorsTF;

    /**
     * Maike Keune-Staab
     * returns a panel to define a teleportPoint's parameter
     * @return
     */
    @Override
    public Pane getPreferencesPanel() {
        VBox vBox = new VBox();

        final RadioButton yes = new RadioButton("Yes");
        final RadioButton no = new RadioButton("No");

        typeComboBox = new ComboBox<>();
        nameTF = new TextField();
        targetFieldNameTF = new TextField();
        targetTeleporterNameTF = new TextField();
        sourceFieldNameTF = new TextField();
        sourceTeleporterNameTF = new TextField();
        heightTF = new NumberTextField();
        widthTF = new NumberTextField();
        floorsTF = new NumberTextField();

        ToggleGroup tg = new ToggleGroup();
        tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oldValue, Toggle value) {
                randomDungeonTarget = yes.equals(value);
                sourceFieldNameTF.setDisable(!randomDungeonTarget);
                sourceTeleporterNameTF.setDisable(!randomDungeonTarget);
                heightTF.setDisable(!randomDungeonTarget);
                widthTF.setDisable(!randomDungeonTarget);
                floorsTF.setDisable(!randomDungeonTarget);
            }
        });
        yes.setToggleGroup(tg);

        no.setToggleGroup(tg);
        no.setSelected(true);

        typeComboBox.getItems().addAll(DungeonHelper.Type.values());

        Label description = new Label("please add a town name or dungeon name into the 'target-field-name' and " +
                "(optional) the name of the target start point into 'target-point-name'.");

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vBox.getChildren().addAll(
                new Label("Link to random map before target: "),
                yes, no,
                new Separator(),
                description,
                new Separator(),
                new Label("Name: "),
                nameTF,
                new Label("Target-field-name: "),
                targetFieldNameTF,
                new Label("Target-point-name: "),
                targetTeleporterNameTF,
                new Label("Source-field-name: "),
                sourceFieldNameTF,
                new Label("Source-point-name: "),
                sourceTeleporterNameTF,
                new Label("Radon dungeon type: "),
                typeComboBox,
                new Label("Random dungeon height: "),
                heightTF,
                new Label("Random dungeon width: "),
                widthTF,
                new Label("Random dungeon floors: "),
                floorsTF);

        return vBox;
    }

    /**
     * Maike Keune-Staab
     * returns a new teleportPoint as defined by the getPreferencesPanel-method-
     *
     * @param x
     * @param y
     * @return
     */
    @Override
    public NavigationPoint getNavigationPoint(int x, int y) {
        return new TeleportPoint(randomDungeonTarget, x, y, nameTF.getText(), targetFieldNameTF.getText(),
                targetTeleporterNameTF.getText(), sourceFieldNameTF.getText(), sourceTeleporterNameTF.getText(),
                heightTF.getText() != null && !heightTF.getText().isEmpty() ? Integer.valueOf(heightTF.getText()) : null,
                widthTF.getText() != null && !widthTF.getText().isEmpty() ? Integer.valueOf(widthTF.getText()) : null,
                floorsTF.getText() != null && !floorsTF.getText().isEmpty() ? Integer.valueOf(floorsTF.getText()) : null,
                typeComboBox.getSelectionModel().getSelectedItem());
    }

    @Override
    public String toString() {
        return "Teleporter";
    }
}
