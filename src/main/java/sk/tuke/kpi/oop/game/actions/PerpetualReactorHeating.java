package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Reactor;

public class PerpetualReactorHeating extends AbstractAction<Reactor> {

    private int inc;

    public PerpetualReactorHeating(int increment) {
        this.inc = increment;
    }

    @Override
    public void execute(float deltaTime) {
        this.getActor().increaseTemperature(this.inc);
    }

}
