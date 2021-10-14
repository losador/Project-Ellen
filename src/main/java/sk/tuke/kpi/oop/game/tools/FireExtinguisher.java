package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;

public class FireExtinguisher extends BreakableTool{
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

}
