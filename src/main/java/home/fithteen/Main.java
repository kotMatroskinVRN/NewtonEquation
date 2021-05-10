package home.fithteen;

import home.fithteen.controllers.Controller;
import home.fithteen.controllers.MainController;
import home.fithteen.models.BruteForceModel;
import home.fithteen.models.ModelEquation;
import home.fithteen.models.NewtonMethod;
import home.fithteen.views.MainView;
import home.fithteen.views.View;

import javax.swing.*;

/**
 * @author Andrey Manankov
 * @version 1.0.0
 *
 * Main Class
 * run GUI
 *
 * defines if result is rounded by first argument
 *
 * argument  - blank or sigma(0.1 0.001 0.0001 etc)
 *
 *
 */
public class Main {


    public static void main(String[] args) {


        NewtonMethod newtonMethod = new NewtonMethod();
        if(args.length>0){

            double sigma = Double.parseDouble(args[0]);
            if( sigma!=0 ) {
                System.out.println("false round");
                //newtonMethod.falseRound();
                newtonMethod.setSIGMA( sigma );
            }

        }

        ModelEquation bruteForceModel = new BruteForceModel();


        Controller controllerGUI = new MainController(newtonMethod);
        //Controller controllerGUI = new MainController(bruteForceModel);

        View mainView = new MainView(controllerGUI);
        SwingUtilities.invokeLater(  mainView::init );




    }

}
