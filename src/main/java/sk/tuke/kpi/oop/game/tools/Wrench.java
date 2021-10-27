package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.DefectiveLight;

public class Wrench extends BreakableTool<DefectiveLight> {

    public Wrench() {
        super(2);
        Animation wrenchAnimation;
        setAnimation(wrenchAnimation = new Animation("sprites/wrench.png"));
    }

    @Override
    public void useWith(DefectiveLight dfLight){
        if(dfLight == null) return;
        dfLight.repair();
        super.useWith(dfLight);
    }
}
