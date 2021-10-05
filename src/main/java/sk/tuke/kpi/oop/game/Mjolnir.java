package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.graphics.Animation;

public class Mjolnir extends Hammer{
    private int useNum;
    private Animation mjAnimation;

    public Mjolnir(){
        this.useNum = 4;
        this.mjAnimation = new Animation("sprites/hammer.png");
        setAnimation(mjAnimation);
    }

    public int getUse(){
        return this.useNum;
    }

    public void use(){
        if(this.useNum <= 0) return;
        this.useNum -= 1;
        if(this.useNum == 0){
            this.getScene().removeActor(this);
        }
    }
}
