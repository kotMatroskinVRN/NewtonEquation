package home.fifteen.models;

import java.text.DecimalFormat;

public interface ModelEquation {


    int LIMIT = 1000000 ;
    DecimalFormat DECIMAL_FORMAT = new DecimalFormat(".0000");
    String DEBUG_FORMAT = "%-10s = %s\n";
    String NOT_FOUND = "NOT_FOUND";
    double SIGMA =  0.01   ;

    void init(final String input);
    void solve();
    String getTextSolution();
    double getSolution();
    boolean ifCantSolve();
    Equation getEquation();





}
