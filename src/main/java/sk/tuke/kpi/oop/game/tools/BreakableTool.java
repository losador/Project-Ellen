package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.framework.AbstractActor;

import java.util.Objects;

public abstract class BreakableTool extends AbstractActor {
    public int remainingUses;

    public BreakableTool(int uses){
        this.remainingUses = uses;
    }

    public void use(){
        this.remainingUses -= 1;
        if(this.remainingUses == 0){
            Objects.requireNonNull(this.getScene()).removeActor(this);
        }
    }
}
