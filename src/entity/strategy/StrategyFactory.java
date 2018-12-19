package entity.strategy;

/**
 * We can have only one instance of this class.(Singletone pattern)
 */
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


    /**
     *
     * @return an instance of StopStrategyX
     */
    public StopStrategyX getStopStrategyX() { return stopStrategyX; }

    /**
     *
     * @return an instance of StopStrategyY
     */
    public StopStrategyY getStopStrategyY() { return stopStrategyY; }


    /**
     *
     * @return an instance of MoveStrategyX
     */
    public MoveStrategyX getMoveStrategyX(){return moveStrategyX;}

    /**
     *
     * @return an instance of MoveStrategyY
     */
    public MoveStrategyY getMoveStrategyY(){return moveStrategyY;}

    /**
     *
     * @return an instance of SwimStrategyY
     */
    public SwimStrategyY getSwimStrategyY() {return swimStrategyY;}

}
