package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class FireExtinguisher extends AbstractActor {
    private int usage;
    private Animation exAnimation;

    public FireExtinguisher(){
        this.usage = 1;
        this.exAnimation = new Animation("sprites/extinguisher.png");
        setAnimation(exAnimation);
    }

    public int getUsage(){
        return this.usage;
    }

    public void use(){
        if(this.usage <= 0) return;
        this.usage -= 1;
        if(this.usage == 0){
            this.getScene().removeActor(this);
        }
    }
}
