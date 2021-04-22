package home.fithteen;



public class ControllerGUI implements Controller{

    private final Model model;
    private Thread thread;

    ControllerGUI(Model model){

        this.model = model;
    }
    @Override
    public String action(final String input , Thread thread){

        this.thread = thread;

        model.init(input);
        model.solve();

        notify();



        return model.getTextSolution();
    }
    @Override
    public void notifyAllViews(){

        //this.notify();
    }

    Thread getThread(){return thread;}

}

