package entity.strategy;

public class SwimStrategyY implements StrategyY {

    private double swimSpeed;

    public SwimStrategyY(){swimSpeed = 1.0;}

    @Override
    public double recalcDy(double currentValue, boolean state, double factor) {
        //If setted true, state indicates that the character is swimming up

        if(state){
            return -factor*swimSpeed;
        }
        else return factor*swimSpeed;
    }
}
