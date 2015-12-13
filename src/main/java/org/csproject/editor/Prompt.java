package org.csproject.editor;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Maike Keune-Staab on 26.11.2015.
 */
public class Prompt {

    /**
     * Maike Keune-Staab
     * this method creates a prompt window with given title and message.
     * Depending on the selected button the given callbacks on "yes" or "no" will be executed
     * @param title
     * @param message
     * @param callback
     */
    public static void getPrompt(String title, String message, final Callback callback) {
        final Stage promptStage = new Stage();

        Label messageLabel = new Label(message);

        Button confirm = new Button("Yes");
        confirm.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                callback.onYes();
                promptStage.hide();
            }
        });

        Button cancel = new Button("No");
        cancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                callback.onNo();
                promptStage.hide();
            }
        });

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(confirm, cancel);

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(8));
        vBox.getChildren().addAll(messageLabel, hBox);

        promptStage.setTitle(title);
        promptStage.setScene(new Scene(vBox));
        promptStage.show();
    }

    public static abstract class Callback {
        public abstract void onYes();

        public void onNo() {

        }
    }
}
