package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.graphics.Animation;

import java.util.Objects;

public class Wrench extends BreakableTool{

    private Animation wrenchAnimation;

    public Wrench() {
        super(2);
        setAnimation(this.wrenchAnimation = new Animation("sprites/wrench.png"));
    }

    @Override
    public void useWith(Actor actor){
        this.remainingUses -= 1;
        if(this.remainingUses == 0){
            Objects.requireNonNull(this.getScene()).removeActor(this);
        }
    }
}
