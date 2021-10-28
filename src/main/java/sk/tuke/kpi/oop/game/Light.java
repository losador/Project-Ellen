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

    public void toggle(){
        this.isLightOn = !this.isLightOn;
        updateAnimation();
    }

    public void turnOn() {
        this.isLightOn = true;
        updateAnimation();
    }

    public void turnOff() {
        this.isLightOn = false;
        updateAnimation();
    }

    public boolean isOn() {
        return this.isLightOn;
    }

    public void setPowered(boolean power) {
        this.isEl = power;
        updateAnimation();
    }

    private void updateAnimation() {
        if (this.isLightOn) {
            if (this.isEl) {
                setAnimation(onAnimation);
            } else {
                setAnimation(offAnimation);
            }
        }
    }

}
