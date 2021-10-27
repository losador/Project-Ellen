package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Repairable;

public class Hammer extends BreakableTool<Repairable> {

    public Hammer(){
        this(1);
    }

    public Hammer(int uses){
        super(uses);
        Animation hamAnimation = new Animation("sprites/hammer.png");
        setAnimation(hamAnimation);
    }


    @Override
    public void useWith(Repairable repairable) {
        if (repairable == null) return;
        repairable.repair();
        super.useWith(repairable);
    }
}
