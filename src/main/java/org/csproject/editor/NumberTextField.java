package org.csproject.editor;

import javafx.scene.control.TextField;

/**
 * @author Maike Keune-Staab on 26.11.2015.
 */
class NumberTextField
        extends TextField {
    @Override
    public void replaceText(int start, int end, String text) {
        if (text.matches("[0-9]") || text == "") {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String text) {
        if (text.matches("[0-9]") || text == "") {
            super.replaceSelection(text);
        }
    }
}
