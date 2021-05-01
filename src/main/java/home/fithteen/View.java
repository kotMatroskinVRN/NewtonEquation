package home.fithteen;


public interface View {


    void init();
    void action();

    default void actionThread(){
        new Thread( this::action ).start();
    }


}
