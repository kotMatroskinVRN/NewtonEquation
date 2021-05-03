package home.fithteen;



public class ControllerGUI implements Controller{

    private final ModelEquation modelEquation;
    private final DataTransferObject data = new DataTransferObject();

    ControllerGUI(ModelEquation modelEquation){

        this.modelEquation = modelEquation;
    }


    @Override
    public void action(final String input ){

        modelEquation.init(input);
        modelEquation.solve();

        data.setSolution( modelEquation.getTextSolution() );
        data.setInput( input );

    }
    @Override
     public DataTransferObject getDTO(){
         return data ;
     }

}

