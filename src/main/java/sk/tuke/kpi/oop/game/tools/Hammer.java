package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Repairable;

import java.util.Objects;

public class Hammer extends BreakableTool<Repairable> {
    private Animation hamAnimation;

    public Hammer(int uses){
        super(uses);
        this.hamAnimation = new Animation("sprites/hammer.png");
        setAnimation(hamAnimation);
    }


    @Override
    public void useWith(Repairable repairable) {
        if (repairable == null) return;
        repairable.repair();
        super.useWith(repairable);
    }
}
