package home.fifteen.views.guiswing;

import home.fifteen.controllers.Controller;
import home.fifteen.views.View;
import home.fifteen.views.guiswing.mainpanel.MainPanel;
import home.fifteen.views.guiswing.mainpanel.ViewPanel;

import javax.swing.*;

/**
 * @author Manankov Andrey
 * @version 1.0.0
  * GUI for solving equation program
  * Task field
 * Solve button
 * Result area
 * Margins
 */
public class MainView extends  JFrame implements View {

    private final Controller controller ;
    private final String HEADER  = "Решение Уравнений";
    private final SolvingArea textArea;
    private final TaskPanel taskPanel;



    /**
     * Main constructor
     * setup GUI
     */
    public MainView(Controller controller) {
        this.controller = controller;
        textArea = new SolvingArea(   HEADER + " :\n\n"  );
        taskPanel = new TaskPanel(this);

    }


    @Override
    public void init(){
        initWindowSettings();

        ViewPanel mainPanel = new MainPanel();
        ScrollPane scrollPane = new ScrollPane(textArea);

        textArea.init();
        scrollPane.init();
        taskPanel.init();

        mainPanel.setTaskArea(taskPanel);
        mainPanel.setSolvingArea(scrollPane);
        add( (JPanel) mainPanel);

        setVisible(true);

    }

    /**
     * Main action
     * gets text from task field
     * creates equation instance
     * appends text area
     * clears task field
     * contains some examples : 1 2 3 4 0
     * type just number and press ENTER
     */
    @Override
    public void action() {
        if( !taskPanel.isTaskEmpty() ) {
            String input = taskPanel.getTask();
            runSwingWorker(input);
        }
    }

    private void runSwingWorker(String input){
        SwingWorker< Boolean , Object> worker = new SwingWorker< Boolean , Object>() {
            @Override
            protected Boolean doInBackground() {
                taskPanel.setEnable(false);
                controller.action( input );
                return true;
            }
            @Override
            protected void done() {
                String result = controller.getDTO().getTextSolution();
                textArea.appendTextArea( result );
                taskPanel.setEnable(true);
                taskPanel.clear();
            }
        };
        worker.execute();

    }


    private void initWindowSettings(){
        setTitle(HEADER);
        double RATIO = 0.5625;
        int x = 400;
        setBounds( 350 , 50 , x , (int)(x / RATIO)  );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }



}
