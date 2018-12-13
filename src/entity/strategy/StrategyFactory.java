package entity.strategy;

public class StrategyFactory {
    private StopStrategyX stopStrategyX;
    private StopStrategyY stopStrategyY;
    private MoveLeftStrategy moveLeftStrategy;
    private MoveRightStrategy moveRightStrategy;
    private JumpStrategy jumpStrategy;
    private FallStrategy fallStrategy;

    private static StrategyFactory instance;

    private StrategyFactory() {
        stopStrategyX = new StopStrategyX();
        stopStrategyY = new StopStrategyY();
        moveLeftStrategy = new MoveLeftStrategy();
        moveRightStrategy = new MoveRightStrategy();
        jumpStrategy = new JumpStrategy();
        fallStrategy = new FallStrategy();
    }

    public static StrategyFactory getInstance() {
        if(instance == null)
            instance = new StrategyFactory();
        return instance;
    }

    public StopStrategyX getStopStrategyX() { return stopStrategyX; }
    public StopStrategyY getStopStrategyY() { return stopStrategyY; }
    public MoveLeftStrategy getMoveLeftStrategy() { return moveLeftStrategy; }
    public MoveRightStrategy getMoveRightStrategy() { return moveRightStrategy; }
    public JumpStrategy getJumpStrategy() { return jumpStrategy; }
    public FallStrategy getFallStrategy() { return fallStrategy; }
}