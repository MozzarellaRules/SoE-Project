package entity.strategy;

public class FallStrategy implements StrategyY {
    private double fallSpeed;
    private double maxFallSpeed;
    private double stopJumpSpeed;

    public FallStrategy() {
        fallSpeed = 0.15;
        maxFallSpeed = 4.0;
        stopJumpSpeed = 0.3;
    }

    @Override
    public double recalcDy(double currentValue) {
        double dy = currentValue + fallSpeed;

        if(dy < 0)
            dy = currentValue + stopJumpSpeed;
        else if(dy > maxFallSpeed)
            dy = maxFallSpeed;

        return dy;
    }

}