package entity.strategy;

public class JumpStrategy implements StrategyY {
    private double maxJumpSpeed;

    public JumpStrategy() {
        maxJumpSpeed = -5.8;
    }

    @Override
    public double recalcDy(double currentValue) {
        return maxJumpSpeed;
    }

}
