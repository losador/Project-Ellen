package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Reactor extends AbstractActor {
    private int temperature;
    private int damage;
    private boolean isOn;
    private int lightsCounter;
    private Light reactorLight;
    private Animation offAnimation;
    private Animation normalAnimation;
    private Animation hotAnimation;
    private Animation brokenAnimation;
    private Animation extinguishAnimation;

    public Reactor(){
        this.temperature = 0;
        this.damage = 0;
        this.lightsCounter = 0;
        this.isOn = false;
        this.normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.hotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.05f, Animation.PlayMode.LOOP_PINGPONG);
        this.brokenAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.offAnimation = new Animation("sprites/reactor.png");
        this.extinguishAnimation = new Animation("sprites/reactor_extinguished.png", 80, 80, 0.5f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(offAnimation);
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
            if(this.damage > 80){
                this.hotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.01f, Animation.PlayMode.LOOP_PINGPONG);
            }
            else if(this.damage > 60){
                this.hotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.03f, Animation.PlayMode.LOOP_PINGPONG);
            }
            setAnimation(hotAnimation);
        }
        else{
            setAnimation(brokenAnimation);
        }
    }

    public void increaseTemperature(int increment){
        if(!this.isOn) return;
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
            if(this.damage >= 100){
                this.damage = 100;
                this.isOn = false;
                if(this.reactorLight != null) reactorLight.setElectricityFlow(this.isRunning());
            }
        }
        updateAnimation();
    }

    public void decreaseTemperature(int decrement){
        if(!this.isOn) return;
        if(decrement < 0) return;
        if(this.damage == 100){
            return;
        }
        else if(this.damage >= 50 && this.damage < 100) {
            this.temperature = this.temperature - (decrement / 2);
        }
        updateAnimation();
    }

    public void repairWith(@NotNull Hammer hammer){
        if(this.damage < 0 || this.damage == 100) return;
        int newDamage;
        this.damage -= 50;
        newDamage = this.damage;
        if(this.damage < 0) this.damage = 0;
        this.temperature = (40 * newDamage) + 2000;
        hammer.use();
        updateAnimation();
    }

    public void turnOn(){
        this.isOn = true;
        updateAnimation();
        if(this.reactorLight != null) reactorLight.setElectricityFlow(this.isOn);
    }

    public void turnOff(){
        this.isOn = false;
        setAnimation(offAnimation);
        if(this.reactorLight != null) reactorLight.setElectricityFlow(this.isOn);
    }

    public boolean isRunning(){
        return this.isOn;
    }

    public void addLight(Light curLight){
        if(this.lightsCounter == 1) return;
        this.lightsCounter++;
        this.reactorLight = curLight;
        reactorLight.setElectricityFlow(this.isRunning());
        this.reactorLight.toggle();
    }

    public void removeLight(){
        reactorLight.setElectricityFlow(false);
        this.reactorLight = null;
        this.lightsCounter--;
    }

    public void extinguishWith(@NotNull FireExtinguisher extinguisher){
        if(this.damage < 100) return;
        this.temperature -= 4000;
        if(this.temperature < 0) this.temperature = 0;
        extinguisher.use();
        setAnimation(extinguishAnimation);
    }
}

