package org.csproject.view;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.PGNode;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by Brett on 10/8/2015.
 */
public class CharacterImage extends ImageView {

    Image actorImage;
    ImageView charView;
    Rectangle2D viewPortRect;
    double xPosition, yPosition;

    public CharacterImage() {
        super();

        actorImage = new Image("images/Actor1.png");
        charView = new ImageView(actorImage);

        viewPortRect = new Rectangle2D(0, 0, 33, 31);

        charView.setViewport(viewPortRect);

        xPosition = 20.0; //Position of the image on screen
        yPosition = -20.0;
    }
}
