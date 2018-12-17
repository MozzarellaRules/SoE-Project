package entity.strategy;

public class MoveStrategyX implements StrategyX {
    private double moveSpeed;
    private double maxSpeed;

    public MoveStrategyX(){
        moveSpeed = 0.3;
        maxSpeed = 2.6;
    }

    @Override
    public double recalcDx(double currentValue, boolean state, double factor) {
        double moveSpeed = factor*this.moveSpeed;
        double maxSpeed = factor*this.maxSpeed;
        double dx;

        if(state){
            dx = currentValue+moveSpeed;
            if(dx > maxSpeed)
                dx = maxSpeed;
            return dx;

        }
        else{
            dx = currentValue-moveSpeed; // speed increases progressively
            if(dx < -maxSpeed)
                dx = -maxSpeed; // max speed reached
            return dx;
        }
    }
}
