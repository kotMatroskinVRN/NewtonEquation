package home.fithteen.models;


import java.util.HashSet;
import java.util.Set;

public class BruteForceModel extends NewtonMethod {


    private double step ;
    private Set<Double> approximateRoots;
    private Set<String> roots;
    private Equation equation;

    @Override
    public void init(final String input){

        super.init(input);
        equation = super.getEquation();

        step = 100;
        approximateRoots = new HashSet<>();
        roots = new HashSet<>();

    }

    @Override
    public void solve() {

        // fill root array with approximate solutions
        getApproximateRoots(-LIMIT,LIMIT,step);
        getExactRoots();



    }

    @Override
    public String getTextSolution() {
        StringBuilder result = new StringBuilder();

        result.append( "\n").append(equation.getInput()).append("\n");

        if(roots.size()>0) {
            int i =0;
            for (String root : roots) {

                result.append(String.format(
                        "%s%s = %s\n",
                        equation.getUnknown(),
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



    private void getExactRoots(){

        for(double i : approximateRoots){
            super.setX0(i);
            super.solve();
            if(!super.ifCantSolve()) {

                roots.add( DECIMAL_FORMAT.format(super.getSolution()) );
            }
        }
    }

    private void getApproximateRoots(double low , double high , double step){

        //String x = super.getEquation().getUnknown();

        double fx0 = 0;
        double d0  = 0;

        if(!super.ifCantSolve()) {
            for (double i = low + 1; i <= high; i += step) {

                try {
                    //double fx = equation.getLeft().setVariable(x, i).evaluate() - equation.getRight().setVariable(x,i).evaluate();
                    double fx = equation.fx(i);
                    double d  = equation.derivative(i);
                    if (fx == 0) {
                        roots.add(DECIMAL_FORMAT.format(i));
                    } else {
                        if (fx * fx0 < 0) {
                            System.out.println(i + "\t" + fx);
                            approximateRoots.add(fx < 0 ? i - 1 : i); // derivative >0 or <0
                        }

                        // when extreme is in segment
                        if(d*d0 < 0){ getApproximateRoots(i-step , i , step/100); }
                    }

                    fx0 = fx;
                    d0  = d;
                } catch (ArithmeticException e) {
                    fx0 = 0;
                    d0 = 0;
                }


            }
        }

    }

    public static void main(String[] args) {


        ModelEquation bruteForce = new BruteForceModel();
        //bruteForce.init("6/(x+2)+10=12");
        bruteForce.init("x^2=6.25");
        bruteForce.solve();

        System.out.println(
                bruteForce.getTextSolution()
        );



    }

}
