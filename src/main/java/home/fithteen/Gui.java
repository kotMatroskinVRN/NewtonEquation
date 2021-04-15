package home.fithteen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * @author Manankov Andrey
 * @version 1.0.0
 *
 * GUI for solving equation program
 *
 * Task field
 * Solve button
 * Result area
 * Margins
 */
class Gui extends  JFrame{


    private final String HEADER  = "Решение Уравнений";
    private final JTextField    task = new JTextField("");
    private final JTextArea textArea = new JTextArea(   HEADER + " :\n\n"  );

    private boolean round = true ;


    /**
     * Main constructor
     *
     * setup GUI
     */
    Gui() {

        // main window settings
        super();
        setTitle(HEADER);
        double RATIO = 0.5625;
        int x = 400;
        setBounds( 50 , 50 , x , (int)(x / RATIO)  );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // prevent editiong of result text field
        textArea.setEditable(false);

        // set result text field scrollable
        JScrollPane jsp = new JScrollPane(textArea);
        jsp.setHorizontalScrollBar(null);

        // create button "Решить"
        JButton button = new JButton("Решить");

        // create margins using empty panels in all sides except center
        JPanel north = new JPanel();
        JPanel west  = new JPanel();
        JPanel east  = new JPanel();
        JPanel south = new JPanel();

        // create panel for task and button
        JPanel innerNorth = new JPanel();

        // put task and button to inner pane
        innerNorth.setLayout( new BorderLayout() );
        innerNorth.add(task , BorderLayout.CENTER);
        innerNorth.add(button , BorderLayout.EAST);

        // fill north pane with inner pane and empty panes for margins
        north.setLayout( new BorderLayout() );
        north.add(innerNorth);
        north.add(new JPanel() , BorderLayout.WEST);
        north.add(new JPanel() , BorderLayout.EAST);
        north.add(new JPanel() , BorderLayout.NORTH);

        // put all in main frame
        add(north , BorderLayout.NORTH);
        add(jsp, BorderLayout.CENTER);
        add(west  , BorderLayout.WEST);
        add(east  , BorderLayout.EAST);
        add(south , BorderLayout.SOUTH);


        // create Action Listener and set it to button and task field key ENTER
        ActionListener actionListener = ae -> action();
        button.addActionListener( actionListener );
        task.addActionListener  ( actionListener );

        // make main frame visible
        setVisible(true);
    }

    /**
     * set round to false
     * make result integer number
     */
    void falseRound() {
        this.round = false;
    }

    /**
     * Main action
     *
     * gets text from task field
     * creates equation instance
     * appends text area
     * clears task field
     *
     * contains some examples : 1 2 3 4 0
     * type just number and press ENTER
     */
    private void action() {

        // get text from task field
        String input = task.getText().trim();

        // test samples
        if(input.equals("1")) input = "6/(x+2)+10=12";
        if(input.equals("2")) input = "140-(x/7+29)*4=12";
        if(input.equals("3")) input = "720 / (5x - 12) - 56 = 34";
        if(input.equals("4")) input = "(270/y-2)*30 = 7*120";
        if(input.equals("0")) input = "1/x = 0";

        // create equation instance
        final LinearEquation linearEquation = new LinearEquation(input);

        // solve the equation
        linearEquation.solution();

        // append text area
        textArea.setText( textArea.getText() + linearEquation.getTextSolution( round ) );

        // clear task field
        task.setText("");

    }


}
