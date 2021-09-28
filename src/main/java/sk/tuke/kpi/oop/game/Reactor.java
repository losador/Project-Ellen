package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Reactor extends AbstractActor {
    private int temperature;
    private int damage;
    private Animation normalAnimation;

    public Reactor(Animation normalAnimation){
        this.temperature = 0;
        this.damage = 0;
        normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(normalAnimation);
        this.normalAnimation = normalAnimation;
    }

    public int getTemperature(){
        return this.temperature;
    }

    public float getDamage(){
        return this.damage;
    }

    public void updateAnimation(){
        if(this.temperature == 6000){
            this.normalAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
            setAnimation(this.normalAnimation);
        }
        else if(this.temperature >= 4000 && this.temperature < 6000){
            this.normalAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
            setAnimation(this.normalAnimation);
        }
        else if(this.temperature < 4000){
            this.normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
            setAnimation(normalAnimation);
        }
    }

    public void increaseTemperature(int increment){
        this.temperature = this.temperature + increment;
        if(this.temperature > 2000){
            float newDamage = (float)(this.temperature - 2000)/40;
            this.damage = (int)Math.floor(newDamage);
            updateAnimation();
        }
    }

    public void decreaseTemperature(int decrement){
        if(this.damage == 100){
            return;
        }
        else if(this.damage >= 50 && this.damage < 100) {
            this.temperature = this.temperature - (decrement / 2);
        }
        updateAnimation();
    }

}

