package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

import java.util.Objects;

public class Hammer extends AbstractActor {
    private int useNum;
    private Animation hamAnimation;

    public Hammer(){
        this.useNum = 1;
        this.hamAnimation = new Animation("sprites/hammer.png");
        setAnimation(hamAnimation);
    }

    public int getUse(){
        return this.useNum;
    }

    public void use(){
        if(this.useNum <= 0) return;
        this.useNum -= 1;
        if(this.useNum == 0){
            Objects.requireNonNull(this.getScene()).removeActor(this);
        }
    }

}
