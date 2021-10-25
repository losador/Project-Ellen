package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Reactor;
import sk.tuke.kpi.oop.game.Repairable;

import java.util.Objects;

public class FireExtinguisher extends BreakableTool<Repairable> {
    private int usage;
    private Animation exAnimation;

    public FireExtinguisher(){
        super(1);
        this.exAnimation = new Animation("sprites/extinguisher.png");
        setAnimation(exAnimation);
    }

    public int getUsage(){
        return this.usage;
    }

    public void useWith(Reactor reactor) {
       if(reactor == null) return;
       reactor.extinguish();
       super.useWith(reactor);
    }

}
