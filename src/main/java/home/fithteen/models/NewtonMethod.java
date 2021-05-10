package home.fithteen.models;


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

    private double SIGMA = 0.01   ;

    private Equation equation;

    private int countEsception , countIteration;
    private double x0 , x ;

    private double solution ;


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

        countEsception = 0;
        countIteration = 0;
        x0 = 0;

        //equation = new Equation(this);
        equation = new Equation();
        equation.init(input);

        //System.out.println(equation.getLeft());
        //System.out.println(equation.getRight());
        System.out.println(equation.getUnknown());
        System.out.println(equation.getInput());

    }

    /**
     * Solves equation
     */
    @Override
    public void solve(){

        if(equation.getUnknown().equals("")) countEsception =LIMIT;
        else solution = newton() ;
        //System.out.println(solution);
    }

    @Override
    public Equation getEquation(){ return equation; }

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



    /**
     * @param SIGMA set accuracy
     */
    public void setSIGMA(double SIGMA) {
        this.SIGMA = SIGMA;
    }

    /**
     * set round to false
     * make result integer number
     */
//    public void falseRound() {
//        this.round = false;
//    }

    double getSolution() { return solution; }

    void setX0(double x0) { this.x0 = x0; }

    @Override
    public boolean ifCantSolve() {
        return countIteration >=(int)(0.2/SIGMA) || countEsception >=LIMIT ;
    }


    private double newton(){

        try {
        x = nextX(x0);
        System.out.println(x);


            while ( !ifCantSolve() && Math.abs(x - x0) > SIGMA) {
                x0 = x;
                x -= nextX(x0);
                System.out.printf("x  = %s\n", x);
            }
        }
        catch (ArithmeticException e){
            handleException();
        }

        System.out.printf(" Normal \nX = %.3f X0 = %.3f COUNT = %d\n" , x , x0 , countEsception);
        return x;
    }

    private double nextX (double xi) throws ArithmeticException{
        try{

            //if( Math.abs(xi)==Double.POSITIVE_INFINITY) throw new ArithmeticException();
            //if( Math.abs(xi)> LIMIT || Math.abs(xi)<1/LIMIT) {
            //    throw new ArithmeticException();
            //}

            double derivative = equation.derivative(xi);
            if (derivative == 0) throw new ArithmeticException();

            System.out.println(countEsception);
            System.out.printf( "x0 = %s\n" , x0);
            System.out.printf( "x' = %s\n" , derivative);

            countIteration++;
            return equation.fx(xi)/derivative;
        }catch(ArithmeticException e){
            throw new ArithmeticException();
        }
    }

    private double handleException(){
        System.out.println("Exception\t" + x0);
        //countEsception++;
        countIteration = 0;
        x0 = ++countEsception;

        return newton();
    }



}
