package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Reactor;

public class FireExtinguisher extends BreakableTool<Reactor> {
    private Animation exAnimation;

    public FireExtinguisher(){
        super(1);
        this.exAnimation = new Animation("sprites/extinguisher.png");
        setAnimation(exAnimation);
    }

    public void useWith(Reactor reactor) {
       if(reactor == null) return;
       if(reactor.extinguish()) super.useWith(reactor);
    }

}
