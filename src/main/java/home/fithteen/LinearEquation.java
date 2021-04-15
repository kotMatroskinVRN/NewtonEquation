package home.fithteen;


import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

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
 *
 *
 */
class LinearEquation {

    private static   double SIGMA = 0.0001   ;

    private final Expression left;
    private final Expression right;
    private final String unknown;
    private final String equation ;
    private final String input ;

    //private String textSolution ;

    private int count = 0;
    private double x0 = 0 ;

    private double solution ;

    /**
     * @param equation
     * string e.g.
     * x+5 = 10
     *
     *
     * '=' must be in equation
     * must be only one variable - any letter . Only in left part. Variable in right part is not acceptable
     * ()*\/:+- are possible operations
     *
     */
    LinearEquation( final String equation){

        // saves original input string
        input = equation;

        // set equation and replace : to /
        this.equation = equation.replaceAll(":", "\\/");

        //System.out.println(this.equation);
        // split equation by '='
        final String[] sides = this.equation.split("=");

        // checks if equation has both sides
        // set correspondent variables : sides and unknown
        if(sides.length>1){
            unknown = findVariable(sides[0]);

            left  = new ExpressionBuilder(sides[0])
                    .variable(unknown)
                    .build();
            right = new ExpressionBuilder(sides[1])
                    .variable(unknown)
                    .build();
        }
        else {
            // fill dummy if '=' was not found
            System.out.println("Can't find \"=\"");
            unknown = findVariable("x");

            left  = new ExpressionBuilder("x")
                    .variable(unknown)
                    .build();
            right = new ExpressionBuilder("1")
                    .build();
            count = 10;
        }



    }

    /**
     * Solves equation
     */
    void solution(){

        solution = newton();

    }

    /**
     * @param round true then result is integer , false - accuracy is set by SIGMA
     * @return result string according to accuracy or "Решений нет!!!"
     */
    String getTextSolution( boolean round ){
        StringBuilder result = new StringBuilder();
        String format;

        // if no solution
        if( isMaxCount() ) {
            result.append( "\n").append(input).append("\n");
            result.append( "Решений нет!!!"  ).append("\n");

        }else{
            if(round){
                format ="\n%s\n%s = %.0f \n";
                solution = round(solution);
            }
            else{
                int level = (int) abs(log10( SIGMA ) );
                format ="\n%s\n%s = %." + level + "f \n";

            }
            result.append( String.format( format , input , unknown , solution ) );
        }

        return result.toString();
    }

    /**
     * @param SIGMA set accuracy
     */
    static void setSIGMA(double SIGMA) {
        LinearEquation.SIGMA = SIGMA;
    }


    private  boolean isMaxCount() { return count>=10; }

    private String findVariable(final String expr){

        for (char c : expr.toCharArray() ){
            if(c>96 && c<123 ) return String.valueOf(c);
        }
        return "x";
    }



    private double newton(){



        double x = nextX(x0);


        while( count <10 && Math.abs(x - x0) > SIGMA  ){

            x0=x;


            // prevent division by zero in derivate
            if ( derivative( x0) == 0  ){
                x0 = count;
                x = nextX(x0);

                count++;
            }
            else
                x -= nextX(x0);

        }

        System.out.printf(" Normal \nX0 = %.3f COUNT = %d\n" , x0 , count);
        return x;
    }

    private double nextX (double x){
        try{
            return (fx(x) - right.evaluate())/derivative( x);
        }catch(ArithmeticException e){
            x0 = ++count;
            System.out.printf(" Exception next \nX0 = %.3f COUNT = %d\n" , x0 , count);
            return nextX(x0);
        }
    }


    //private double derivative(double x) throws ArithmeticException {
    private double derivative(double x)  {

        return ( fx(x+SIGMA) - fx(x) )/SIGMA;
    }

    //private double fx(double x) throws ArithmeticException {
    private double fx(double x)  {
        double result;

        try{
            result = left.setVariable(unknown,x).evaluate();
        }catch (ArithmeticException e){
            x0=++count;
            System.out.printf(" Exception fx \nX0 = %.3f COUNT = %d\n" , x0 , count);
            result = fx(x0);
        }

        return result;
    }

}
