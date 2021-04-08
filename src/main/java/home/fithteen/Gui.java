package home.fithteen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static java.lang.Math.*;

class Gui {


    private final String HEADER  = "Решение Уравнений";


    private final JTextField task = new JTextField("" , 24);
    private       JTextArea      ta = new JTextArea(   HEADER + " :\n\n" , 25 , 29 );

    private boolean round = true ;


    Gui() {
        JFrame frm = new JFrame(HEADER);
        double RATIO = 0.5625;
        int y = 480;
        frm.setBounds( 50 , 50 , (int)(y * RATIO) + 100 , y);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ta.setEditable(false);

        JScrollPane jsp = new JScrollPane(ta);
        jsp.setHorizontalScrollBar(null);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(task);
        JButton button = new JButton("Решить");
        topPanel.add(button);

        JPanel west  = new JPanel();
        JPanel east  = new JPanel();
        JPanel south = new JPanel();

        frm.add(topPanel , BorderLayout.NORTH);
        frm.add(jsp, BorderLayout.CENTER);
        frm.add(west  , BorderLayout.WEST);
        frm.add(east  , BorderLayout.EAST);
        frm.add(south , BorderLayout.SOUTH);

        ActionListener actionListener = ae -> action();


        button.addActionListener( actionListener );
        task.addActionListener  ( actionListener );

        frm.setVisible(true);
    }

    void falseRound() {
        this.round = false;
    }

    private void action() {

        String input = task.getText().trim();
        if(input.equals("1")) input = "6/(x+2)+10=12";
        if(input.equals("2")) input = "140-(x/7+29)*4=12";
        if(input.equals("3")) input = "720 / (5x - 12) - 56 = 34";
        if(input.equals("4")) input = "(270/y-2)*30 = 7*120";

        final LinearEquation linearEquation = new LinearEquation(input);

        String format ;
        double solution ;
        if(round){
            format ="\n%s\n%s = %.0f \n";
            solution = round(linearEquation.solution());
        }
        else{
            int level = (int) abs(log10(LinearEquation.getSIGMA()) );
            format ="\n%s\n%s = %." + level + "f \n";
            solution = linearEquation.solution();
        }

        final String result =
                String.format( format , task.getText() , linearEquation.getUnknown() , solution );

        ta.setText( ta.getText() + result );


        task.setText("");

    }



}
