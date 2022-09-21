package home.fifteen.views.guiswing.history;

import java.util.ArrayList;
import java.util.List;

class EquationHistory implements History {

    private int number;
    private final List<String> equations;

    EquationHistory(){
        equations = new ArrayList<>();
        number = 0;
    }


    @Override
    public void moveUp(){
        number--;
        normalizeNumber();
    }

    @Override
    public void moveDown(){
        number++;
        normalizeNumber();
    }

    @Override
    public void addEquation(String equation){
        if(!equations.contains(equation)) {
            equations.add(equation);
            number = equations.size() - 1;
        }
    }

    @Override
    public String getEquation(){
        return equations.get(number);
    }

    private void normalizeNumber(){
        if(number<0){
            number=0;
        }
        if(number>=equations.size()){
            number=equations.size()-1;
        }
    }
}
