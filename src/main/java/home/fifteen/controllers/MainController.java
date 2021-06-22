package home.fifteen.controllers;


import home.fifteen.models.BruteForceModel;
import home.fifteen.models.ModelEquation;
import home.fifteen.views.View;

import java.util.HashSet;
import java.util.regex.Pattern;

public class MainController implements Controller{

    private final ModelEquation defaultModel;
    private final ModelEquation bruteForceModel;

    private final DataTransferObject data = new DataTransferObject();
    private final HashSet<View> viewSet = new HashSet<>();

    private ModelEquation model ;

    public MainController(ModelEquation modelEquation){
        defaultModel = modelEquation;
        bruteForceModel = new BruteForceModel();
    }


    @Override
    public void action(final String input ){

        getProperModel(input);
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
        viewSet.add(view);
    }

    @Override
    public void invokeAllViews() {
        for(View view : viewSet){
            view.init();
            view.actionThread();
        }
    }

    private void getProperModel(String input) {
        defaultModel.init(input);
        String unknown = defaultModel.getEquation().getUnknown();

        String[] patterns = new String[]{
                unknown + "\\s*[\\^]\\s*" ,
                unknown + "\\s*[*]\\s*" + unknown,
                //"=.*" + unknown,
        };

        Pattern pattern = Pattern.compile( String.join( "|" , patterns  ) );


        // factory
        if( pattern.matcher(input).find() ) {
            model = bruteForceModel;
            System.out.println("brute");
        }
        else {
            model = defaultModel;
            System.out.println("Newton");
        }
    }

}

