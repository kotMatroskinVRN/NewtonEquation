package home.fithteen.models;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;


public class Equation {

    private final double SIGMA = 0.01   ;
    //private final ModelEquation MODEL;

    private Expression left;
    private Expression right;
    private String unknown;
    private String input ;




    //public Equation(ModelEquation MODEL) {this.MODEL = MODEL;}

    public void init(String input ) {

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
            unknown = "";

            left  = new ExpressionBuilder("1/x")
                    .variable("x")
                    .build();
            right = new ExpressionBuilder("0")
                    .build();

        }


    }

    String getInput() { return input; }

    public String getUnknown() {return unknown;}

    Expression getLeft() { return left; }

    Expression getRight() { return right; }

    double derivative(double x) throws ArithmeticException  {
        try {
            return ( fx(x+SIGMA) - fx(x) )/SIGMA;
        }
        catch (ArithmeticException e ){
            throw new ArithmeticException();
        }
    }

    double fx(double x) throws ArithmeticException {
        double result;
        //System.out.println("fx");
        try{
        double leftValue = left.setVariable(unknown,x).evaluate();
        double rightValue = right.setVariable(unknown,x).evaluate();
            result = leftValue - rightValue;
       }catch (ArithmeticException e){
            throw new ArithmeticException();
        }

        return result;
    }


    private String findVariable(final String expr){

        for (char c : expr.toCharArray() ){
            if(c>96 && c<123 ) return String.valueOf(c);
        }
        return "x";
    }




}
