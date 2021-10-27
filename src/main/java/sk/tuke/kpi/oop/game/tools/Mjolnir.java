package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;

public class Mjolnir extends Hammer{

    public Mjolnir(){
        super(4);
        Animation mjAnimation = new Animation("sprites/hammer.png");
        setAnimation(mjAnimation);
    }

}
