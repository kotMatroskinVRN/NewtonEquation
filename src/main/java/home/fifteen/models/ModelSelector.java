package home.fifteen.models;

import java.util.regex.Pattern;

public class ModelSelector {

    private final String REPLACER = "REPLACE_UNKNOWN" ;
    private final String[] PATTERN_TEMPLATES = new String[]{
            REPLACER + "\\s*[\\^]\\s*" ,
            REPLACER + "\\s*[*]\\s*" + REPLACER,
    };

    private final ModelEquation model;

    private String regexp;

    public ModelSelector( ModelEquation model ){
        this.model = model;
    }

    public ModelFactory select(){
        ModelFactory modelFactory ;

        init();

        Pattern pattern = Pattern.compile( regexp );
        String input = model.getEquation().getInput();
        if( pattern.matcher(input).find() ) {
            modelFactory = ModelFactory.BRUTEFORCE;
        }
        else {
            modelFactory = ModelFactory.NEWTON;
        }
        System.out.println(modelFactory);

        return modelFactory;
    }

    private void init(){
        String unknown = model.getEquation().getUnknown();
        regexp = String.join( "|" , PATTERN_TEMPLATES );
        regexp = regexp.replaceAll(REPLACER , unknown);

    }


}
