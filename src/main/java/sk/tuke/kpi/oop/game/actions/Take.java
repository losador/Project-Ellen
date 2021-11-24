package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;
import java.util.List;

public class Take<A extends Keeper> extends AbstractAction<A> {

    @Override
    public void execute(float deltaTime) {
        if(getActor().getScene() == null || getActor() == null || this.isDone()){
            setDone(true);
            return;
        }
        List<Actor> items = this.getActor().getScene().getActors();

        for(Actor item : items){
            if(item instanceof Collectible && item.intersects(this.getActor())){
                try {
                    this.getActor().getBackpack().add((Collectible) item);
                    this.getActor().getScene().removeActor(item);
                } catch (IllegalStateException exception){
                    this.getActor().getScene().getOverlay().drawText(exception.getMessage(), 0, 0).showFor(2);
                }
            }
        }
        setDone(true);
    }
}
