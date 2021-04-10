package home.fithteen;


import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

class LinearEquation {

    private static   double SIGMA = 0.0001   ;

    private final Expression left;
    private final Expression right;
    private final String unknown;
    private final String equation ;

    private int count = 0;
    private double x0 = 0 ;

    LinearEquation( final String equation){

        this.equation = equation.replaceAll(":", "\\/");

        System.out.println(this.equation);
        final String[] sides = this.equation.split("=");

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

    double solution(){

        return newton();

    }


    String getUnknown() { return unknown; }

    String getEquation() { return equation; }

    static double getSIGMA() { return SIGMA; }

    static void setSIGMA(double SIGMA) {
        LinearEquation.SIGMA = SIGMA;
    }

    public boolean isMaxCount() { return count>=10; }



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


// 6/(2+x)+10=12