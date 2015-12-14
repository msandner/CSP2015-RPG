package org.csproject.model.field;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maike Keune-Staab on 14.12.2015.
 */
public class FieldSelection {
    private List<Field> fields = new ArrayList<>();
    private List<Field> singletonFields = new ArrayList<>();

    public FieldSelection() {

    }

    public FieldSelection(List<Field> fields) {
        this.fields = fields;
    }

    public List<Field> getFields() {
        return fields;
    }

    public List<Field> getSingletonFields() {
        return singletonFields;
    }
}
