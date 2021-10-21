package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.graphics.Animation;

import java.util.Objects;

public class Hammer extends BreakableTool{
    private Animation hamAnimation;

    public Hammer(){
        super(1);
        this.hamAnimation = new Animation("sprites/hammer.png");
        setAnimation(hamAnimation);
    }

    public int getUse(){
        return this.remainingUses;
    }

    @Override
    public void useWith(Actor actor) {
        this.remainingUses -= 1;
        if(this.remainingUses == 0){
            Objects.requireNonNull(this.getScene()).removeActor(this);
        }
    }
}
