package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Reactor extends AbstractActor {
    private int temperature;
    private int damage;
    private Animation normalAnimation;
    private Animation hotAnimation;
    private Animation brokenAnimation;

    public Reactor(){
        this.temperature = 0;
        this.damage = 0;
        this.normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.hotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.05f, Animation.PlayMode.LOOP_PINGPONG);
        this.brokenAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(normalAnimation);
    }

    public int getTemperature(){
        return this.temperature;
    }

    public float getDamage(){
        return this.damage;
    }

    public void updateAnimation(){
        if(this.temperature < 4000){
            setAnimation(normalAnimation);
        }
        else if(this.temperature < 6000){
            setAnimation(hotAnimation);
        }
        else{
            setAnimation(brokenAnimation);
        }


    }

    public void increaseTemperature(int increment){
        if(increment < 0) return;
        if(this.damage > 100) return;
        double newIncrement;
        if(this.damage >= 33 && this.damage <= 66){
            newIncrement = (double)increment * 1.5;
            newIncrement = Math.round(newIncrement);
            increment = (int)newIncrement;
        }
        if(this.damage > 66){
            increment *= 2;
        }
        this.temperature = this.temperature + increment;
        if(this.temperature > 2000){
            float newDamage = (float)(this.temperature - 2000)/40;
            this.damage = (int)Math.floor(newDamage);
            if(this.damage > 100) this.damage = 100;
            updateAnimation();
        }
    }

    public void decreaseTemperature(int decrement){
        if(decrement < 0) return;
        if(this.damage == 100){
            return;
        }
        else if(this.damage >= 50 && this.damage < 100) {
            this.temperature = this.temperature - (decrement / 2);
        }
        updateAnimation();
    }

}

