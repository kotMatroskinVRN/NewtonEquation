package home.fifteen.models;


import java.util.Stack;

import static java.lang.Math.*;

/**
 * @author Andrey Manankov
 * @version 1.0.0
 *
 * Class for Equation
 *
 * e.g.
 * x+5 = 10
 *
 * '=' must be in equation
 * must be only one variable - any letter. Only in left part. Variable in right part is not acceptable
 * ()*\/:+- are possible operations
 * format 5*x and 5x if accepted
 * format x5 gives incorrect answer
 *
 */
public class NewtonMethod implements ModelEquation {

    private boolean protectX0;

    private Equation equation;

    private int countException, countIteration;
    private double x0 , x ;

    private double solution ;

    private final Stack<Integer> initialX = new Stack<>();


    /**
     * @param input
     * string e.g.
     * x+5 = 10
     *
     *
     * '=' must be in equation
     * must be only one variable - any letter . Only in left part. Variable in right part is not acceptable
     * ()*\/:+- are possible operations
     *
     */
    @Override
    public void init(final String input){

        countException = 0;
        countIteration = 0;
        x0 = 0;
        protectX0 = false;
        initialX.clear();

        equation = new Equation();
        equation.init(input);


    }

    /**
     * Solves equation
     */
    @Override
    public void solve(){
        if(equation.getUnknown().equals("")) countException = LIMIT;
        else solution = newton() ;
    }

    @Override
    public Equation getEquation(){
        return equation;
    }

    /**
     * @return result string according to accuracy or "Решений нет!!!"
     */
    @Override
    public String getTextSolution(){
        StringBuilder result = new StringBuilder();
        String format;

        // if not solved
        if( ifCantSolve() ) {
            result.append( "\n").append(equation.getInput()).append("\n");
            result.append( "Решений нет!!!"  ).append("\n");

        }else{

            int level = (int) abs(log10( SIGMA ) );
            format ="\n%s\n%s = %." + level + "f \n";


            result.append( String.format( format , equation.getInput() , equation.getUnknown() , solution ) );
        }

        return result.toString();
    }

    public double getSolution() {
        return solution;
    }

    void setX0(double x0) {
        this.x0 = x0;
        protectX0 = true;
    }

    @Override
    public boolean ifCantSolve() {
        return countIteration >=(int)(0.2/SIGMA) || countException >=LIMIT ;
    }


    private double newton(){

        try {
        x = nextX(x0);

            while ( !ifCantSolve() && Math.abs(x - x0) > SIGMA) {
                x0 = x;
                x -= nextX(x0);
            }
        }
        catch (ArithmeticException e){
            handleException();
        }

        return x;
    }

    private double nextX (double xi) throws ArithmeticException{
        try{
            double derivative = equation.derivative(xi);
            if (derivative == 0) throw new ArithmeticException();
            countIteration++;

            return equation.fx(xi)/derivative;
        }
        catch(ArithmeticException e){
            throw new ArithmeticException();
        }
    }

    private void handleException(){
        System.out.println("Exception\t" + x0);
        countIteration = 0;

        System.out.println(initialX);
        int step = 1000;
        countException++;

        if(protectX0) {
            countException = LIMIT;
        }
        else{
            if (initialX.empty()) {
                initialX.push(countException + step);
                initialX.push(countException - step);
            } else {
                x0 = initialX.pop();
            }
        }

        newton();
    }



}