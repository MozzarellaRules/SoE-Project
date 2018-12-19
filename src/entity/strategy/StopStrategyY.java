package entity.strategy;

public class StopStrategyY implements StrategyY {


    /**
     *
     * @param currentValue this parameter is not used
     * @param state this parameter is not used
     * @param factor this parameter is not used
     * @return 0 is the next dy.
     */
    @Override
    public double recalcDy(double currentValue, boolean state, double factor) {
        return 0;
    }
}
