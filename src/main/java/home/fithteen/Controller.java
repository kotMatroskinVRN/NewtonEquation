package home.fithteen;

public interface Controller {

    String action(final String input );
    //void notifyAllViews();
    String getInput();
    String getSolution();
}
