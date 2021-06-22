package home.fifteen.controllers;


import home.fifteen.views.View;

public interface Controller {
    void action(final String input );
    DataTransferObject getDTO();
    void addView(View view);
    void invokeAllViews();
}
