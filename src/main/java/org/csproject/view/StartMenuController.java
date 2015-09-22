package org.csproject.view;

/**
 * Created by Brett on 9/21/2015.
 */
public class StartMenuController implements ControlledScreen {

    ScreensController screenController;


    @Override
    public void setScreenParent(ScreensController screenParent) {
        screenController = screenParent;
    }
}
