package home.fifteen.controllers;


public interface Controller {
    void action(final String input );
    DataTransferObject getDTO();
}
