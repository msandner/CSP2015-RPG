package org.csproject.editor;

import javafx.scene.layout.Pane;
import org.csproject.model.general.NavigationPoint;

/**
 * @author Maike Keune-Staab on 09.11.2015.
 */
public interface NavPointSelection {
    Pane getPreferencesPanel();

    NavigationPoint getNavigationPoint(int x, int y);
}
