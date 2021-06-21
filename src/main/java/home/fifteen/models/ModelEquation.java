package home.fifteen.models;

import java.text.DecimalFormat;
import java.util.Set;

public interface ModelEquation {


    int LIMIT = 1000000 ;
    DecimalFormat DECIMAL_FORMAT = new DecimalFormat(".0000");
    String DEBUG_FORMAT = "%-10s = %s\n";
    String NOT_FOUND = "NOT_FOUND";
    double SIGMA =  0.01   ;

    void init(final String input);
    void solve();
    Set<Double> getRoots();
    boolean ifCantSolve();
    Equation getEquation();

    default String getTextSolution( final Set<Double> roots ) {
        StringBuilder result = new StringBuilder();

        result.append( "\n").append(getEquation().getInput()).append("\n");

        if(roots.size()>0) {
            int i =0;
            for (Double root : roots) {

                result.append(String.format(
                        "%s%s = %s\n",
                        getEquation().getUnknown(),
                        i++,
                        root
                ));

            }
        }
        else {
            result.append( "Решений нет!!!"  ).append("\n");
        }

        return result.toString();
    }

    default double decimalCorrection(double input){

        return Double.parseDouble( DECIMAL_FORMAT.format( input ).replace("," , ".") );
    }


}
