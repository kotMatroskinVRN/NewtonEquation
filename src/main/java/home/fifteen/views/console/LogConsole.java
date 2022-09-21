package home.fifteen.views.console;


import home.fifteen.controllers.Controller;
import home.fifteen.views.View;

public class LogConsole implements View {
    private String  solution;
    private final Controller controller ;


    public LogConsole(Controller controller) {
        this.controller = controller;
        controller.addView(this);
    }

    @Override
    public void action() {
        System.out.println( this );
    }

    @Override
    public String toString(){
        return "Log console : \n" + solution;
    }

    @Override
    public void init(){
        this.solution = controller.getDTO().getTextSolution();
    }
}
