package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;

import java.util.Objects;

public abstract class BreakableTool<A extends Actor> extends AbstractActor implements Usable<A>{
    private int remainingUses;

    public BreakableTool(int uses){
        this.remainingUses = uses;
    }

    public int getRemainingUses(){
        return this.remainingUses;
    }

    public void useWith(A actor){
        this.remainingUses -= 1;
        if(this.remainingUses == 0){
            Objects.requireNonNull(this.getScene()).removeActor(this);
        }
    }
}
