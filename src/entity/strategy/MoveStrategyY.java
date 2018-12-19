package entity.strategy;

public class MoveStrategyY implements StrategyY {

    private double jump;
    private double fallSpeed;
    private double maxFallSpeed;
    private double stopJumpSpeed;



    public MoveStrategyY(){
        jump = -5.8 ;
        fallSpeed = 0.15;
        maxFallSpeed = 3.0;
        stopJumpSpeed = 0.3;
    }


    /**
     *
     * @param currentValue is the current dy increment of the player on the y-axis
     * @param state is the parameter that indicates if the character is moving up or down
     * @param factor factor is the coefficient that modify the way in which the next "dy" is calculated
     * @return The possibile next "dy" position.
     */

    @Override
    public double recalcDy(double currentValue, boolean state, double factor) {
        //If setted,state indicates that the character is falling
        if(state){

                double dy = currentValue + fallSpeed;

                if(dy < 0)
                    dy = currentValue + stopJumpSpeed;
                else if(dy > maxFallSpeed)
                    dy = maxFallSpeed;

                return dy;

        }

        else return jump*factor;
    }
}
