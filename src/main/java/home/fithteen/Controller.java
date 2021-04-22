package home.fithteen;

public interface Controller {

    String action(final String input , Thread thread);
    void notifyAllViews();
}
