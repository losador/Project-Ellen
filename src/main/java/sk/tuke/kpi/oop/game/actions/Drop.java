package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

public class Drop<A extends Keeper> extends AbstractAction<A> {

    private Keeper keeper;

    public Drop(Keeper keeper){
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
        Collectible item = this.keeper.getBackpack().peek();
        this.keeper.getScene().addActor(item, (this.keeper.getPosX() + this.keeper.getWidth()/2 - item.getWidth()/2), (this.keeper.getPosY() + this.keeper.getHeight()/2 - item.getHeight()/2));
        this.keeper.getBackpack().remove(item);
        setDone(true);
    }
}
