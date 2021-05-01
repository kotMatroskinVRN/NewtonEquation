package home.fithteen;

public class LogConsole implements View {
    private String  solution;
    private Controller controller ;


    LogConsole(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void action() {
        System.out.println( this );
    }

    @Override
    public String toString(){
        return "Log console : \n" + solution;
    }

    public void init(){
        this.solution = controller.getSolution();
    }
}
