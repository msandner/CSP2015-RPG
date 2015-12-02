package org.csproject.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.csproject.model.Constants;

/**
 * Created by Brett on 11/30/2015.
 */
public class MonsterImage extends ImageView {
    String file;

    /**
     * Create the MonsterImage. The name parameter must match the file's prefix.
     * For example, for the Bat.png image, name would equal "Bat"
     * So not "bat" and not "Bat.png"
     * @param name - Name of the monster whose image is being gotten.
     */
    public MonsterImage(String name) {
        super();
        file = name + Constants.CS_POST_FIX;
        Image monsterImage = new Image("images/enemies/" + file);
        setImage(monsterImage);
    }
}
