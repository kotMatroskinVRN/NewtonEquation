package home.fifteen.views.guiswing;

import javax.swing.*;
import java.awt.*;

class SolvingArea extends JTextArea {

    private final Insets MARGIN = new Insets(5,5,5,5);

    SolvingArea(String text){
        super(text);
    }

    void init(){
        setEditable(false);
        setMargin(MARGIN);

    }

    void appendTextArea( String text){
        String newText = getText() + text ;
        SwingUtilities.invokeLater( () -> setText(newText) );
    }
}
