package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.items.Backpack;

public class Ripley extends AbstractActor implements Movable, Keeper {

    private int speed;
    private Animation ripleyAnimation;
    private int energy;
    private int ammo;
    private Backpack backpack;

    public Ripley(){
         super("Ellen");
         this.speed = 2;
         this.energy = 100;
         this.backpack = new Backpack("Ripley`s backpack", 10);
         this.ripleyAnimation = new Animation("sprites/player.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
         setAnimation(ripleyAnimation);
         this.ripleyAnimation.stop();
    }

    @Override
    public int getSpeed(){
        return this.speed;
    }

    @Override
    public void startedMoving(Direction direction) {
        this.ripleyAnimation.setRotation(direction.getAngle());
        this.ripleyAnimation.play();
    }

    @Override
    public void stoppedMoving(){
        this.ripleyAnimation.stop();
    }

    public void setEnergy(int energy){
        this.energy = energy;
    }

    public int getEnergy(){
        return this.energy;
    }

    public void setAmmo(int ammo){
        this.ammo = ammo;
    }

    public int getAmmo(){
        return this.ammo;
    }

    @Override
    public Backpack getBackpack() {
        return this.backpack;
    }
}
