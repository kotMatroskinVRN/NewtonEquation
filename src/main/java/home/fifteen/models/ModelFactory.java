package home.fifteen.models;

public enum ModelFactory {
    NEWTON{
        @Override
        public ModelEquation getModel() {
            return new NewtonMethod();
        }
    },
    BRUTEFORCE{
        @Override
        public ModelEquation getModel() {
            return new BruteForceModel();
        }
    },
    ;

    abstract public ModelEquation getModel();
}
