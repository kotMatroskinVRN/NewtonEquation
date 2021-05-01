package home.fithteen;



public class ControllerGUI implements Controller{

    private final Model model;
    private String input;
    //private Thread thread;

    ControllerGUI(Model model){

        this.model = model;
    }
    @Override
    public String action(final String input ){

        //this.thread = thread;

        model.init(input);
        model.solve();
        this.input = input;

        //notify();



        return model.getTextSolution();
    }
//    @Override
//    public void notifyAllViews(){
//
//        //this.notify();
//    }

    //Thread getThread(){return thread;}

     public String getInput(){
         return input;

     }
     public String getSolution(){
         return model.getTextSolution();
     }

}

