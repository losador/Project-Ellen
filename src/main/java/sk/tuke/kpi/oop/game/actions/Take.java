package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;
import java.util.List;

public class Take<A extends Keeper> extends AbstractAction<A> {

    private Keeper keeper;

    public Take(Keeper keeper){
        this.keeper = keeper;
    }

    @Override
    public void execute(float deltaTime) {
        if(this.isDone()) return;
        if(getActor().getScene() == null || getActor() == null){
            setDone(true);
            return;
        }
        List<Actor> items = this.keeper.getScene().getActors();

        for(Actor item : items){
            if(item instanceof Collectible && item.intersects(this.keeper)){
                try {
                    this.keeper.getBackpack().add((Collectible) item);
                    this.keeper.getScene().removeActor(item);
                } catch (IllegalStateException exception){
                    this.keeper.getScene().getOverlay().drawText(exception.getMessage(), 0, 0).showFor(2);
                }
            }
        }
        setDone(true);
    }
}
