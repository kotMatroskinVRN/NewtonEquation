package home.fithteen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
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
class Gui extends  JFrame implements View{

    private final Controller controller;

    private final String HEADER  = "Решение Уравнений";
    private final JTextField    task = new JTextField("");
    private final JTextArea textArea = new JTextArea(   HEADER + " :\n\n"  );
    private final JButton button;



    /**
     * Main constructor
     *
     * setup GUI
     */
    Gui(Controller controller) {

        // main window settings
        setTitle(HEADER);
        double RATIO = 0.5625;
        int x = 400;
        setBounds( 50 , 50 , x , (int)(x / RATIO)  );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.controller = controller;

        // prevent editiong of result text field
        textArea.setEditable(false);

        // set result text field scrollable
        JScrollPane jsp = new JScrollPane(textArea);
        jsp.setHorizontalScrollBar(null);

        // create button "Решить"
        button = new JButton("Решить");

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
        // action must be performed in new Thread , not in EventQueue
        ActionListener actionListener = new ButtonAction();

        button.addActionListener( actionListener );
        task.addActionListener  ( actionListener );

        // make main frame visible
        setVisible(true);
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
    @Override
    public void action() {

        SwingUtilities.invokeLater( () -> showFields(false));


        // get text from task field
        String input = task.getText().trim();

        // test samples
        if(input.equals("1")) input = "6/(x+2)+10=12";
        if(input.equals("2")) input = "140-(x/7+29)*4=12";
        if(input.equals("3")) input = "720 / (5x - 12) - 56 = 34";
        if(input.equals("4")) input = "(270/y-2)*30 = 7*120";
        if(input.equals("0")) input = "1/x = 0";


        // append text area
        if( !task.getText().isEmpty() ) {
            String result = textArea.getText() + controller.action(input);

            SwingUtilities.invokeLater( () -> textArea.setText(result) );
        }

        SwingUtilities.invokeLater( () -> {
            showFields(true);
            task.setText("");
            task.requestFocusInWindow();
        });


    }


    private void showFields(boolean enable){

        if(enable){
            button.setEnabled(true);
            task.setEnabled(true);
        }
        else {
            button.setEnabled(false);
            task.setEnabled(false);
        }
    }


    class ButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
                new Thread( new ActionThread() ).start();
        }
    }

    class ActionThread implements Runnable{
        @Override
        public void run() {
            action();
        }
    }


}
