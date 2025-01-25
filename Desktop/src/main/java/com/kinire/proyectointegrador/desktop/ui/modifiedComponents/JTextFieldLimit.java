package com.kinire.proyectointegrador.desktop.ui.modifiedComponents;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JTextFieldLimit extends PlainDocument {
    private int maxLength;


    public JTextFieldLimit(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if(str == null)
            return;
        if((getLength() + str.length()) > maxLength) {
            return;
        }
        super.insertString(offs, str, a);
    }
}
