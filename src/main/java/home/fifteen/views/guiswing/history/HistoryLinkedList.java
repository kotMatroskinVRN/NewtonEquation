package home.fifteen.views.guiswing.history;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class HistoryLinkedList implements History{

    private final ListIterator<String> iterator;
    private String equation;

    public HistoryLinkedList(){
        List<String> equations = new LinkedList<>();
        iterator = equations.listIterator();
        equation = "";
    }

    @Override
    public void moveUp() {
        if(iterator.hasPrevious()){
            equation = iterator.previous();
        }
    }

    @Override
    public void moveDown() {
        if(iterator.hasNext()){
            equation = iterator.next();
        }
    }

    @Override
    public String getEquation() {
        return equation;
    }

    @Override
    public void addEquation(String equation) {
        iterator.add(equation);
    }
}
