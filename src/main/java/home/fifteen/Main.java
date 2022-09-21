package home.fifteen;

import home.fifteen.controllers.Controller;
import home.fifteen.controllers.MainController;
import home.fifteen.models.ModelEquation;
import home.fifteen.models.NewtonMethod;
import home.fifteen.views.console.LogConsole;
import home.fifteen.views.guiswing.MainView;
import home.fifteen.views.View;

import javax.swing.*;

/**
 * @author Andrey Manankov
 * @version 1.0.0
 *
 * Main Class
 * setup defaults
 * run GUI
 *
 *
 */
public class Main {


    public static void main(String[] args) {

        ModelEquation newtonMethod = new NewtonMethod();
        Controller controllerGUI = new MainController(newtonMethod);
        View mainView   = new MainView(controllerGUI);
        View logConsole = new LogConsole(controllerGUI);

        SwingUtilities.invokeLater(  mainView::init );

    }

}
