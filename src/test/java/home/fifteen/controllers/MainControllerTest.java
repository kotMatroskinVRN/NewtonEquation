package home.fifteen.controllers;

import home.fifteen.models.ModelEquation;
import home.fifteen.models.NewtonMethod;

import static org.junit.jupiter.api.Assertions.*;

class MainControllerTest {

    private final ModelEquation   model = new NewtonMethod();
    private final Controller controller = new MainController(model);

    @org.junit.jupiter.api.Test
    void action() {

        assertTrue( checkResult("6/(x+2)+10=12" , 1 ) );
        assertTrue( checkResult("140-(x/7+29)*4=12" , 21 ) );
        assertTrue( checkResult("720 / (5x - 12) - 56 = 34" , 4 ) );
        assertTrue( checkResult("(270/x-2)*30 = 7*120" , 9 ) );
        assertTrue( checkResult("12 = 140-(x/7+29)*4" , 21 ) );
        assertTrue( checkResult("200 /x = 1" , 200 ) );


        controller.action("x^2-7x+10 = 0");
        String result = "x^2-7x+10 = 0\nx0 = 5.0000\nx1 = 2.0000";
        assertEquals( controller.getDTO().getTextSolution().trim() , result );

        controller.action("1/x = 0");
        result = "1/x = 0\nРешений нет!!!";
        assertEquals( controller.getDTO().getTextSolution().trim() , result );

        System.out.println("done");

    }

    @org.junit.jupiter.api.Test
    void getDTO() {
    }


    private boolean checkResult(String input , double solution){

        controller.action(input);

        return Math.abs(solution - controller.getDTO().getSolution()) < model.SIGMA ;
    }




}