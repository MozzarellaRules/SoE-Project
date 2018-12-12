package entity.strategy;

public class JumpStrategy implements IStrategy {
    private double jumpStart;

    public JumpStrategy() {
        jumpStart = -5.8;
    }

    @Override
    public double recalcDx(double currentValue) {
        return currentValue;
    }

    @Override
    public double recalcDy(double currentValue) {
        return jumpStart;
    }

}
