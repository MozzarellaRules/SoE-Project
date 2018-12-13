package entity.strategy;

public class MoveRightStrategy implements StrategyX {
    private double moveSpeed;
    private double maxSpeed;

    public MoveRightStrategy() {
        moveSpeed = 0.3;
        maxSpeed = 2.6;
    }

    @Override
    public double recalcDx(double currentValue) {
        //System.out.println(currentValue);

        double dx = currentValue+moveSpeed; // speed increases progressively
        if(dx > maxSpeed)
            dx = maxSpeed; // max speed reached
        return dx;
    }

}