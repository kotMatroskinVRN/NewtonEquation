package home.fifteen.views.guiswing.mainpanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainPanel extends JPanel implements ViewPanel{

    public MainPanel(){
        super();
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setVgap(10);
        setLayout(borderLayout);
        setBorder(new EmptyBorder(10,10,10,10));
    }

    @Override
    public void setTaskArea(JComponent component) {
        add(component , BorderLayout.NORTH);
    }

    @Override
    public void setSolvingArea(JComponent component) {
        add(component , BorderLayout.CENTER);
    }
}
