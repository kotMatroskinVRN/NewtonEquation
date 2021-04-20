package home.fithteen;

public class ControllerGUI implements Controller{

    private Model model;

    ControllerGUI(Model model){

        this.model = model;
    }
    @Override
    public String action(final String input){

        model.init(input);
        model.solve();

        return model.getTextSolution();
    }

}
