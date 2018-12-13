package entity.strategy;

public class SwimDownStrategyY implements StrategyY{

        private double maxSpeed;
        private double moveSpeed ;

        public SwimDownStrategyY(){
            maxSpeed = 5.0;
            moveSpeed = 0.3;

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
