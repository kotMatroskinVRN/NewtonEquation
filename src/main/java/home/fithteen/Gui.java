package home.fithteen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static java.lang.Math.*;

class Gui extends  JFrame{


    private final String HEADER  = "Решение Уравнений";


    //private final JTextField    task = new JTextField("" , 24);
    private final JTextField    task = new JTextField("");
    //private final JTextArea textArea = new JTextArea(   HEADER + " :\n\n" , 25 , 29 );
    private final JTextArea textArea = new JTextArea(   HEADER + " :\n\n"  );

    private boolean round = true ;

    JSplitPane topPanel;


    Gui() {
        super();
        setTitle(HEADER);
        double RATIO = 0.5625;
        int y = 480;
        setBounds( 50 , 50 , (int)(y * RATIO)  , y);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textArea.setEditable(false);

        JScrollPane jsp = new JScrollPane(textArea);
        jsp.setHorizontalScrollBar(null);

        JButton button = new JButton("Решить");
        topPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT ,  task ,  button);
        topPanel.setDividerSize(0);




        JPanel west  = new JPanel();
        JPanel east  = new JPanel();
        JPanel south = new JPanel();

        add(topPanel , BorderLayout.NORTH);
        add(jsp, BorderLayout.CENTER);
        add(west  , BorderLayout.WEST);
        add(east  , BorderLayout.EAST);
        add(south , BorderLayout.SOUTH);

        ActionListener actionListener = ae -> action();


        button.addActionListener( actionListener );
        task.addActionListener  ( actionListener );

        setVisible(true);
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
        if(input.equals("0")) input = "1/x = 0";

        final LinearEquation linearEquation = new LinearEquation(input);

        String format ;
        double solution ;
        String result;

        solution = linearEquation.solution();

        if( linearEquation.isMaxCount() ) {
            result = String.format("\n%s\n" , input  );
            result += "Решений нет!!!\n";

        }else{
            if(round){
                format ="\n%s\n%s = %.0f \n";
                solution = round(solution);
            }
            else{
                int level = (int) abs(log10(LinearEquation.getSIGMA()) );
                format ="\n%s\n%s = %." + level + "f \n";

            }
            result =
                    String.format( format , input , linearEquation.getUnknown() , solution );
        }



        textArea.setText( textArea.getText() + result );


        task.setText("");

    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        topPanel.setDividerLocation( getWidth()-100);
        //topPanel.repaint();
    }
}
