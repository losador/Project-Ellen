package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Reactor;

public class Hammer extends BreakableTool<Reactor> implements Collectible{

    public Hammer(){
        this(1);
    }

    public Hammer(int uses){
        super(uses);
        Animation hamAnimation = new Animation("sprites/hammer.png");
        setAnimation(hamAnimation);
    }

    public void useWith(Reactor reactor) {
        if (reactor == null) return;
        if(reactor.repair()) super.useWith(reactor);
    }
}
