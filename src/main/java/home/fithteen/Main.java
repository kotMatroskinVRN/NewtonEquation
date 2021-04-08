package home.fithteen;

import javax.swing.*;

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
