package org.csproject.editor;

import javafx.scene.control.TextField;

/**
 * @author Maike Keune-Staab on 26.11.2015.
 */
class NumberTextField
        extends TextField {
    /**
     * Maike Keune-Staab
     * overrides the replaceTest method so it only accepts numbers
     * @param start
     * @param end
     * @param text
     */
    @Override
    public void replaceText(int start, int end, String text) {
        if (text.matches("[0-9]") || text == "") {
            super.replaceText(start, end, text);
        }
    }

    /**
     * Maike Keune-Staab
     * overrides replaceSelection method to only accept numbers
     * @param text
     */

    @Override
    public void replaceSelection(String text) {
        if (text.matches("[0-9]") || text == "") {
            super.replaceSelection(text);
        }
    }

    public int getNumber(){
        if(getText() == null || getText().isEmpty()){
            return 0;
        }
        return Integer.parseInt(getText());
    }
}
