package entity.strategy;

public class MoveStrategyY implements StrategyY {

    private double jump;
    private double fallSpeed;
    private double maxFallSpeed;
    private double stopJumpSpeed;



    public MoveStrategyY(){
        jump = -5.8 ;
        fallSpeed = 0.15;
        maxFallSpeed = 4.0;
        stopJumpSpeed = 0.3;
    }

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
