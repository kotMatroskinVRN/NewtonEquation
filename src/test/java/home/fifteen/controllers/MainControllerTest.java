package home.fifteen.controllers;

import home.fifteen.models.ModelEquation;
import home.fifteen.models.NewtonMethod;
import org.apache.maven.surefire.shared.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class MainControllerTest {

    private final ModelEquation   model = new NewtonMethod();
    private final Controller controller = new MainController(model);

    @Test
    void action() {

        checkResult("6/(x+2)+10=12" , new double[]{1.0}  );
        checkResult("140-(x/7+29)*4=12" , new double[]{21}  );
        checkResult("720 / (5x - 12) - 56 = 34" , new double[]{4}  );
        checkResult("(270/x-2)*30 = 7*120" , new double[]{9}  );
        checkResult("12 = 140-(x/7+29)*4" , new double[]{21}  );
        checkResult("200 /x = 1" , new double[]{200}  );
        checkResult("(50-x)/7+195=40*5" , new double[]{15}  );


        checkResult("x^2-7x+10 = 0" , new double[]{2,5}  );

        String result;

        controller.action("1/x = 0");
        result = "1/x = 0\nРешений нет!!!";
        assertEquals( controller.getDTO().getTextSolution().trim() , result );

        System.out.println("done");

    }


    private void checkResult(String input , double[] roots){

        controller.action(input);
        double[] calculatedArray = ArrayUtils.toPrimitive(controller.getDTO().getRoots().toArray(new Double[0]));

        assertArrayEquals( roots , calculatedArray , model.SIGMA );
    }




}