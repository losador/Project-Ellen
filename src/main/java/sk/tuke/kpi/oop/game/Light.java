package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor {
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
        if(!this.isLightOn && isEl){
            this.isLightOn = true;
            setAnimation(onAnimation);
        }
        else {
            this.isLightOn = false;
            setAnimation(offAnimation);
        }
    }

    public void setElectricityFlow(boolean el){
        this.isEl = el;
    }
}
