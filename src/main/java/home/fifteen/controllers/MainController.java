package home.fifteen.controllers;


import home.fifteen.models.ModelEquation;
import home.fifteen.models.ModelSelector;
import home.fifteen.views.View;

import java.util.HashSet;
import java.util.Set;

public class MainController implements Controller{

    private final DataTransferObject data;
    private final Set<View> additionalViews;

    private ModelEquation model ;

    public MainController(ModelEquation modelEquation){
        model = modelEquation;
        data = new DataTransferObject();
        additionalViews = new HashSet<>();
    }


    @Override
    public void action(String input){

        setProperModel(input);
        model.init(input);
        model.solve();

        data.setInput( input );
        data.setRoots(model.getRoots());
        data.setTextSolution( model.getTextSolution( data.getRoots() ) );

        invokeAllViews();


    }

    @Override
     public DataTransferObject getDTO(){
        return data ;
     }

    @Override
    public void addView(View view) {
        additionalViews.add(view);
    }

    @Override
    public void invokeAllViews() {
        for(View view : additionalViews){
            view.init();
            view.action();
        }
    }

    private void setProperModel(String input) {
        model.init(input);
        ModelSelector selector = new ModelSelector(model);
        model = selector.select().getModel();

    }

}

