package entity.strategy;

public class StrategyFactory {
    private StopStrategyX stopStrategyX;
    private StopStrategyY stopStrategyY;

    private MoveStrategyX moveStrategyX;
    private MoveStrategyY moveStrategyY;

    private SwimStrategyY swimStrategyY;


    private static StrategyFactory instance;

    private StrategyFactory() {
        stopStrategyX = new StopStrategyX();
        stopStrategyY = new StopStrategyY();
        moveStrategyX = new MoveStrategyX();
        moveStrategyY = new MoveStrategyY();
        swimStrategyY = new SwimStrategyY();
    }

    public static StrategyFactory getInstance() {
        if(instance == null)
            instance = new StrategyFactory();
        return instance;
    }

    public StopStrategyX getStopStrategyX() { return stopStrategyX; }
    public StopStrategyY getStopStrategyY() { return stopStrategyY; }

    public MoveStrategyX getMoveStrategyX(){return moveStrategyX;}
    public MoveStrategyY getMoveStrategyY(){return moveStrategyY;}

    public SwimStrategyY getSwimStrategyY() {return swimStrategyY;}

}
