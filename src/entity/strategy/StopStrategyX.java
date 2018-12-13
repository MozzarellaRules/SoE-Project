package entity.strategy;

public class StopStrategyX implements StrategyX {
    private double stopSpeed;

    public StopStrategyX() {
        stopSpeed = 0.4;
    }

    @Override
    public double recalcDx(double currentValue) {
        double dx = currentValue;
        if(dx > 0) {
            dx -= stopSpeed;
            if(dx < 0)
                dx = 0;
        }
        else if(dx < 0) {
            dx += stopSpeed;
            if(dx > 0)
                dx = 0;
        }
        return dx;
    }

}
