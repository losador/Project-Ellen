package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;

public class Shift<A extends Actor> extends AbstractAction<A> {

    private Keeper keeper;

    public Shift(Keeper keeper){
        this.keeper = keeper;
    }

    @Override
    public void execute(float deltaTime) {
        if(this.isDone()) return;
        if(getActor() == null || getActor().getScene() == null){
            setDone(true);
            return;
        }
        if(this.keeper.getBackpack().getSize() == 0){
            setDone(true);
            return;
        }
        this.keeper.getBackpack().shift();
        setDone(true);
    }
}
