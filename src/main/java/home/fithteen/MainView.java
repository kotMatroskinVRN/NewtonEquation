package home.fithteen;

import javax.swing.*;
import java.awt.*;

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
class MainView extends  JFrame implements View{

    private final Controller controller ;
    private final View[] views ;

    private final String HEADER  = "Решение Уравнений";
    private final JTextField    task = new JTextField("");
    private final JTextArea textArea = new JTextArea(   HEADER + " :\n\n"  );
    private final JButton button = new JButton("Решить");



    /**
     * Main constructor
     *
     * setup GUI
     */
    MainView(Controller controller) {


        this.controller = controller;

        views = new View[]{
                new LogConsole(controller)

        };

    }


    @Override
    public void init(){


        // main window settings
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

        button.addActionListener( e -> actionThread() );
        task.addActionListener  ( e -> actionThread() );

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

        showFields(false);


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

            controller.action( input );

            appendTextArea( controller.getDTO().getSolution() );

            invokeAllViews();

        }

            showFields(true);



    }

    private void appendTextArea( String text){
        String newText = textArea.getText() + text ;
        SwingUtilities.invokeLater( () -> textArea.setText(newText) );

    }

    private void showFields(boolean enable){

        SwingUtilities.invokeLater( () -> {
            button.setEnabled(enable);
            task.setEnabled(enable);

            if (enable) {
                task.setText("");
                task.requestFocusInWindow();
            }
        });
    }


    private void invokeAllViews(){

        for( View view : views ){
            view.init();
            view.action();
        }

    }


}
