package entity.strategy;

public class StopStrategyX implements StrategyX {


    /**
     *
     * @param currentValue this parameter is not used
     * @param state this parameter is not used
     * @param factor this parameter is not used
     * @return 0 is the next dx.
     */
    @Override
    public double recalcDx(double currentValue, boolean state, double factor) {
        return 0;
        }



}
