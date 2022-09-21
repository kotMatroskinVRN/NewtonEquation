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
    public void init(String input){

        super.init(input);
        equation = super.getEquation();

        step = 100;
        approximateRoots.clear();
        roots.clear();

    }

    @Override
    public void solve() {

        findApproximateRoots(-LIMIT,LIMIT,step);
        findExactRoots();

    }



    @Override
    public Set<Double> getRoots(){
        return roots;
    }


    private void findExactRoots(){

        for(double i : approximateRoots){
            super.setX0(i);
            super.solve();
            if(super.hasRoots()) {

                for(double root : super.getRoots()){
                    root = decimalCorrection( root ) ;
                    roots.add( root );
                }

            }
        }
    }

    private void findApproximateRoots(double low , double high , double step){

        double fx0 = 0;
        double d0  = 0;

        if(super.hasRoots()) {
            for (double i = low + 1; i <= high; i += step) {

                try {
                    double fx = equation.fx(i);
                    double d  = equation.derivative(i);

                    if (fx == 0) {
                        roots.add( decimalCorrection(i) );
                    } else {
                        if (fx * fx0 < 0) {
                            System.out.println(i + "\t" + fx);
                            approximateRoots.add(fx < 0 ? i - 1 : i); // derivative >0 or <0
                        }

                        // when extreme is in segment
                        if(d*d0 < 0){
                            findApproximateRoots(i-step , i , step/100);
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
