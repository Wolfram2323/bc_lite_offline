package com.bftcom.gui.custom.cell;

import com.bftcom.gui.custom.fields.NumberTextField;

import javafx.scene.control.TableCell;



/**
 * Created by k.nikitin on 24.11.2016.
 */
public class NumberTextFieldTableCell<S> extends TableCell<S,String> {

    private NumberTextField numberTextField;

    public NumberTextFieldTableCell(){
        this.getStyleClass().add("text-field-table-cell");
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createTextField();
            setText(null);
            setGraphic(numberTextField);
            numberTextField.selectAll();
            numberTextField.requestFocus();
        }
    }

    @Override
    public void updateItem(String item, boolean empty)  {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (numberTextField != null) {
                    numberTextField.setText(getString());
                }
                setText(null);
                setGraphic(numberTextField);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItem());
        setGraphic(null);
    }

    private void createTextField() {
        numberTextField = new NumberTextField(getString());
        numberTextField.setMinWidth(this.getWidth() - this.getGraphicTextGap()* 2);
        numberTextField.focusedProperty().addListener((arg0, arg1, arg2) -> {
            if (!arg2) {
                commitEdit(numberTextField.getText());
            }
        });
        numberTextField.setOnAction(event -> {
            this.commitEdit(numberTextField.getText());
            event.consume();
        });
    }

    private String getString() {
        return getItem() == null ? "0.0" : getItem().toString();
    }


}
