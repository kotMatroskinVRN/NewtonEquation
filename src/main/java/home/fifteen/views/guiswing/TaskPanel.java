package home.fifteen.views.guiswing;

import home.fifteen.views.View;
import home.fifteen.views.guiswing.history.History;
import home.fifteen.views.guiswing.history.HistoryLinkedList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class TaskPanel extends JPanel {

    private final JTextField task;
    private final JButton button;
    private final View view;
    private final History equationHistory ;

    private boolean enable;

    TaskPanel(View view){
        this.view = view;
        task = new JTextField();
        button = new JButton("Решить");
//        equationHistory = new EquationHistory();
        equationHistory = new HistoryLinkedList();

    }

    void init(){
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setHgap(10);
        setLayout( borderLayout );
        add(task , BorderLayout.CENTER);
        add(button , BorderLayout.EAST);

        button.addActionListener( e -> view.actionThread() );
        task.addActionListener  ( e -> view.actionThread() );
        task.addKeyListener( new HistoryListener() );
    }

    boolean isTaskEmpty(){
        return task.getText().isEmpty();
    }

    void setEnable(boolean enable){
        button.setEnabled(enable);
        task.setEnabled(enable);

        this.enable = enable;
    }

    void update(){
        button.setEnabled(enable);
        task.setEnabled(enable);

        if (enable) {
            task.setText("");
            task.requestFocusInWindow();
        }
    }

    String getTask(){
        String input = task.getText().trim();

        // test samples
        if(input.equals("1")) input = "6/(x+2)+10=12";
        if(input.equals("2")) input = "140-(x/7+29)*4=12";
        if(input.equals("3")) input = "720 / (5x - 12) - 56 = 34";
        if(input.equals("4")) input = "(270/x-2)*30 = 7*120";
        if(input.equals("5")) input = "x^2-7x+10 = 0";
        if(input.equals("6")) input = "12 = 140-(x/7+29)*4";
        if(input.equals("7")) input = "200 /x = 1";

        if(input.equals("0")) input = "1/x = 0";

        equationHistory.addEquation(input);

        return input;
    }

    private class HistoryListener implements KeyListener{


        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            int c = e.getKeyCode();
            if(c==KeyEvent.VK_DOWN){
                equationHistory.moveDown();
                task.setText(equationHistory.getEquation());
            }
            if(c==KeyEvent.VK_UP){
                equationHistory.moveUp();
                task.setText(equationHistory.getEquation());
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}
    }


}
