package home.fifteen.views.guiswing;

import javax.swing.*;

class ScrollPane extends JScrollPane {

    ScrollPane(JTextArea textArea){
        super(textArea);
    }

    void init(){
        setHorizontalScrollBar(null);
    }

}
