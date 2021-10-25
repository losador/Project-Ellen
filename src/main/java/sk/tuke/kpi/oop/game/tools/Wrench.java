package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.DefectiveLight;

import java.util.Objects;

public class Wrench extends BreakableTool<DefectiveLight> {

    private Animation wrenchAnimation;

    public Wrench() {
        super(2);
        setAnimation(this.wrenchAnimation = new Animation("sprites/wrench.png"));
    }

    @Override
    public void useWith(DefectiveLight dfLight){
        if(dfLight == null) return;
        dfLight.repair();
        super.useWith(dfLight);
    }
}
