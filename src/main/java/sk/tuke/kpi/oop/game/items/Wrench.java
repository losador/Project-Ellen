package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.DefectiveLight;

public class Wrench extends BreakableTool<DefectiveLight> implements Collectible{

    public Wrench() {
        super(2);
        setAnimation(new Animation("sprites/wrench.png"));
    }

    @Override
    public void useWith(DefectiveLight dfLight){
        if(dfLight == null) return;
        if(dfLight.repair()) super.useWith(dfLight);
    }

    @Override
    public Class<DefectiveLight> getUsingActorClass() {
        return DefectiveLight.class;
    }
}
