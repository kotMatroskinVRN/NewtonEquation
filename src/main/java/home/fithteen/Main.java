package home.fithteen;

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

   private static Gui gui;

    public static void main(String[] args) {




        SwingUtilities.invokeLater( () -> {
            gui = new Gui();

            if(args.length>0){
                System.out.println("false round");
                gui.falseRound();
                LinearEquation.setSIGMA( Double.parseDouble(args[0]));

            }


        } );



    }
}
