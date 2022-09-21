package home.fifteen.controllers;

import java.util.Set;
import java.util.TreeSet;

public class DataTransferObject {

    private String input;
    private String textSolution;
    private Set<Double> roots = new TreeSet<>();

//    public String getInput() {
//        return input;
//    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getTextSolution() {
        return textSolution;
    }

    public void setTextSolution(String textSolution){
        this.textSolution = textSolution;
    }

    public Set<Double> getRoots() {
        return roots;
    }

    public void setRoots(Set<Double> roots) {
        this.roots = roots;
    }
}
