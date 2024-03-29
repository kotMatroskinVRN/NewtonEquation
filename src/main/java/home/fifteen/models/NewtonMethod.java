package home.fifteen.models;


import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

/**
 * @author Andrey Manankov
 * @version 1.0.0
 *
 *
 */
public class NewtonMethod implements ModelEquation {

    private boolean protectX0;
    private Equation equation;
    private int countException, countIteration;
    private double x0 , x ;

    private final Set<Double> roots = new TreeSet<>();

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
    public void init(String input){

        countException = 0;
        countIteration = 0;

        x0 = 0;

        protectX0 = false;
        initialX.clear();
        roots.clear();

        equation = new Equation();
        equation.init(input);


    }

    /**
     * Solves equation
     */
    @Override
    public void solve(){
        if(equation.isEquation()){
            double root = decimalCorrection( countNewton() ) ;
            if( Math.abs(root)<LIMIT ) roots.add(root)  ;
        }
        else {
            countException = LIMIT;
        }
    }

    @Override
    public Equation getEquation(){
        return equation;
    }


    @Override
    public Set<Double> getRoots() {
        return roots;
    }

    @Override
    public boolean hasRoots() {
        return countIteration < (int)(0.2/SIGMA) && countException < LIMIT ;
    }

    void setX0(double x0) {
        this.x0 = x0;
        protectX0 = true;
    }


    private double countNewton(){

        try {
            x = countNextX(x0);
            while ( hasRoots() && Math.abs(x - x0) > SIGMA) {
                x0 = x;
                x -= countNextX(x0);

                if(x>LIMIT) throw new ArithmeticException();

            }
        }
        catch (ArithmeticException e){
            handleException();
        }

        return x;
    }

    private double countNextX(double xi) throws ArithmeticException{
        try{
            double derivative = equation.derivative(xi);
            if (derivative != 0) {
                countIteration++;
            }

            return equation.fx(xi)/derivative;
        }
        catch(ArithmeticException e){
            throw new ArithmeticException();
        }
    }

    private void handleException(){
        countIteration = 0;

        int step = 1000;
        countException++;

        if(protectX0) {
            countException = LIMIT;
        }
        else{
            if (initialX.empty()) {
                initialX.push(countException + step);
                initialX.push(countException - step);
            }

            x0 = initialX.pop();

        }

        countNewton();
    }


}
