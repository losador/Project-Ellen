package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;

public class Mjolnir extends Hammer{
    private int useNum;
    private Animation mjAnimation;

    public Mjolnir(){
        super.remainingUses = 4;
        this.mjAnimation = new Animation("sprites/hammer.png");
        setAnimation(mjAnimation);
    }

    public int getUse(){
        return this.useNum;
    }

}