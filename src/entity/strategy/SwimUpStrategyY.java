package entity.strategy;

public class SwimUpStrategyY implements StrategyY{

    private double maxSpeed;
    private double moveSpeed ;

    public SwimUpStrategyY(){
        maxSpeed = -5.0;
        moveSpeed = -0.3;

    }

    public double recalcDy(double currentValue){

        if (currentValue == maxSpeed){
          return currentValue;
      }else {
          currentValue+=moveSpeed;
          return currentValue;
      }

     }
}
