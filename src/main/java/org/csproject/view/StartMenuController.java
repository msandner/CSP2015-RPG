package org.csproject.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import org.csproject.service.ScreensController;

import java.io.*;
//import java.util.DoubleSummaryStatistics;

/**
 * Created by Brett on 9/21/2015.
 */
public class StartMenuController implements ControlledScreen {

    private ScreensController screenController;

    public StartMenuController() {

    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        screenController = screenParent;
    }

    public void startNewGame(ActionEvent actionEvent) {
        screenController.setScreen(MasterController.NEW_GAME_ID);
    }

    public void loadSavedGame(ActionEvent actionEvent) throws IOException {
        String path = System.getProperty("user.home") + "\\Local Settings\\Application Data\\FFC_Saves";
        File saveLocation = new File(path);
        String data = "";

        if(saveLocation.exists()){
            FileInputStream fis = null;
            try{
                fis = new FileInputStream(saveLocation + "/saves.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            data = br.readLine();
            br.close();
            fis.close();
        }

        String[] tokens = data.split("\\s+");
        String screen = tokens[0];
        double x = Double.parseDouble(tokens[1]);
        double y = Double.parseDouble(tokens[2]);

        // display new game on field
//        screenController.setUpLoadGame(screen, x, y);

        // add field as game screen
        screenController.addScreen(MasterController.GAME_SCREEN, screenController.getFieldScreen());

        // switch screen to game screen or town screen
        if(screen.equals(MasterController.GAME_SCREEN)) {
            screenController.setScreen(MasterController.GAME_SCREEN);
        } else if(screen.equals(MasterController.TOWN_SCREEN)) {
            screenController.setScreen(MasterController.TOWN_SCREEN);
        } else {
            //Dungeon?
        }
    }

    public void exitGame(ActionEvent actionEvent) {
        Platform.exit();
    }
}
