package entity.strategy;

public class SwimStrategyY implements StrategyY {

    private double swimSpeed;

    public SwimStrategyY(){swimSpeed = 1.0;}

    /**
     *
     * @param currentValue not used
     * @param state if true indicates that the character is swimming up
     * @param factor is the coefficient which varies the swimming velocity
     * @return the next dy increment
     */
    @Override
    public double recalcDy(double currentValue, boolean state, double factor) {

        if(state){
            return -factor*swimSpeed;
        }
        else return factor*swimSpeed;
    }
}
