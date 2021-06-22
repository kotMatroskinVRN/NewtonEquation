package home.fifteen.views;


public interface View {

    void init();
    void action();
    //void invoke();

    default void actionThread(){
        new Thread( this::action ).start();
    }

    default void invokeAllViews( View[] views ){

        for( View view : views ){
            view.init();
            view.action();
        }

    }

}
