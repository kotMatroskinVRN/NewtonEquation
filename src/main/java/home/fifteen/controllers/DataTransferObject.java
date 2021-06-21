package home.fifteen.controllers;

import java.util.Set;
import java.util.TreeSet;

public class DataTransferObject {

    private String input;
    private String textSolution;
    private Set<Double> roots = new TreeSet<>();

    public String getInput() {
        return input;
    }

    void setInput(String input) {
        this.input = input;
    }

    public String getTextSolution() {
        return textSolution;
    }

    void setTextSolution(String textSolution){
        this.textSolution = textSolution;
    }

    Set<Double> getRoots() {
        return roots;
    }

    void setRoots(Set<Double> roots) {
        this.roots = roots;
    }
}
