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
class NewtonMethod implements ModelEquation {

    private double SIGMA = 0.01   ;
    private boolean round = true ;

    private Expression left;
    private Expression right;
    private String unknown;
    private String input ;

    private int count ;
    private double x0 ;

    private double solution ;


    NewtonMethod( ){




    }

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

        count = 0;
        x0 = 0;

        // saves original input string
        this.input = input;

        // set equation and replace : to /
        String equation = input.replaceAll(":", "\\/");

        //System.out.println(this.equation);
        // split equation by '='
        final String[] sides = equation.split("=");

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
    @Override
    public void solve(){

        solution = newton();

    }

    /**
     * @return result string according to accuracy or "Решений нет!!!"
     */
    @Override
    public String getTextSolution(){
        StringBuilder result = new StringBuilder();
        String format;

        // if no solve
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
    void setSIGMA(double SIGMA) {
        this.SIGMA = SIGMA;
    }

    /**
     * set round to false
     * make result integer number
     */
    void falseRound() {
        this.round = false;
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
            return (fx(x) - right.evaluate())/derivative(x);
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
