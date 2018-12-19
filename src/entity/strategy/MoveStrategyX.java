package entity.strategy;

public class MoveStrategyX implements StrategyX {
    private double moveSpeed;
    private double maxSpeed;

    public MoveStrategyX(){
        moveSpeed = 0.3;
        maxSpeed = 2.6;
    }

    /**
     *
     * @param currentValue is the current dx increment.
     * @param state is the parameter that indicates if the character is moving left or right
     * @param factor is the coefficient that modify the way in which the next "dx" is calculated
     * @return the next "dx" position.
     */

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
