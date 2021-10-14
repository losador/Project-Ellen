package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;

public class Hammer extends BreakableTool{
    private int useNum;
    private Animation hamAnimation;

    public Hammer(){
        super(1);
        this.hamAnimation = new Animation("sprites/hammer.png");
        setAnimation(hamAnimation);
    }

    public int getUse(){
        return this.useNum;
    }

}
