package home.fithteen.controllers;


public interface Controller {
    void action(final String input );
    DataTransferObject getDTO();
}
