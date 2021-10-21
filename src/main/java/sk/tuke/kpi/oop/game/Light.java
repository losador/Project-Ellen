package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor implements Switchable, EnergyConsumer{
    private boolean isLightOn;
    private boolean isEl;
    private Animation onAnimation;
    private Animation offAnimation;

    public Light(){
        this.isLightOn = false;
        this.onAnimation = new Animation("sprites/light_on.png");
        this.offAnimation = new Animation("sprites/light_off.png");
        setAnimation(offAnimation);
    }

    public void turnOn() {
        if(isEl){
            this.isLightOn = true;
            setAnimation(onAnimation);
        }
    }

    public void turnOff() {
        this.isLightOn = false;
        setAnimation(offAnimation);
    }

    public boolean isOn() {
        return this.isLightOn;
    }

    public void setPowered(boolean power) {
        this.isEl = power;
        if(power) turnOn();
        else turnOff();
    }
}
