package home.fifteen.models;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;


public class Equation {

    private final double SIGMA = ModelEquation.SIGMA;

    private Expression left;
    private Expression right;
    private String unknown;
    private String input ;
    private boolean isEquation;


    void init(String input ) {

        this.input = input;
        String equation = input.replaceAll(":", "\\/");
        String[] sides = equation.split("=");
        isEquation = true;
        unknown = findVariable(equation);

        if(sides.length>1 && !unknown.equals("")){

            left  = new ExpressionBuilder(sides[0])
                    .variable(unknown)
                    .build();
            right = new ExpressionBuilder(sides[1])
                    .variable(unknown)
                    .build();
        }
        else {
            makeDummyEquation();
            isEquation = false;
        }

    }

    public boolean isEquation() {
        return isEquation;
    }

    public String getUnknown() {
        return unknown;
    }

    String getInput() {
        return input;
    }

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
        try{
            double leftValue  =  left.setVariable(unknown,x).evaluate();
            double rightValue = right.setVariable(unknown,x).evaluate();
            result = leftValue - rightValue;
       }catch (ArithmeticException e){
            throw new ArithmeticException();
        }

        return result;
    }


    private String findVariable(String expr){

        for (char c : expr.toCharArray() ){
            if(c>96 && c<123 ) {
                return String.valueOf(c);
            }
        }
        return "";
    }

    private void makeDummyEquation(){

        System.out.println("Can't find \"=\" or variable");
        unknown = "";

        left  = new ExpressionBuilder("1/x")
                .variable("x")
                .build();
        right = new ExpressionBuilder("0")
                .build();

    }




}
