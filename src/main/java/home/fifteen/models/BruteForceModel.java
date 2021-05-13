package home.fifteen.models;


import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class BruteForceModel extends NewtonMethod {


    private double step ;
    private final Set<Double> approximateRoots = new HashSet<>();
    private final Set<Double> roots = new TreeSet<>();
    private Equation equation ;

    @Override
    public void init(final String input){

        super.init(input);
        equation = super.getEquation();

        step = 100;
        approximateRoots.clear();
        roots.clear();

    }

    @Override
    public void solve() {

        // fill root array with approximate solutions
        getApproximateRoots(-LIMIT,LIMIT,step);
        getExactRoots();

    }



    @Override
    public Set<Double> getRoots(){
        return roots;
    }


    private void getExactRoots(){

        for(double i : approximateRoots){
            super.setX0(i);
            super.solve();
            if(!super.ifCantSolve()) {

                for(double root : super.getRoots()){
                    root = Double.parseDouble( DECIMAL_FORMAT.format( root ) );
                    roots.add( root );
                }


            }
        }
    }

    private void getApproximateRoots(double low , double high , double step){

        double fx0 = 0;
        double d0  = 0;

        if(!super.ifCantSolve()) {
            for (double i = low + 1; i <= high; i += step) {

                try {
                    double fx = equation.fx(i);
                    double d  = equation.derivative(i);
                    if (fx == 0) {

                        roots.add( Double.parseDouble( DECIMAL_FORMAT.format(i) ) );
                    } else {
                        if (fx * fx0 < 0) {
                            System.out.println(i + "\t" + fx);
                            approximateRoots.add(fx < 0 ? i - 1 : i); // derivative >0 or <0
                        }

                        // when extreme is in segment
                        if(d*d0 < 0){
                            getApproximateRoots(i-step , i , step/100);
                        }
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



}
