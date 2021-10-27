package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Color;

public class PowerSwitch extends AbstractActor {
    private Switchable switchable;

    public PowerSwitch(Switchable switchable){
        Animation conImage = new Animation("sprites/switch.png");
        setAnimation(conImage);
        this.switchable = switchable;
    }

    public Switchable getDevice(){
        return this.switchable;
    }

    public void switchOn(){
        if(this.switchable == null) return;
        this.switchable.turnOn();
        getAnimation().setTint(Color.WHITE);
    }

    public void switchOff(){
        if(this.switchable == null) return;
        this.switchable.turnOff();
        getAnimation().setTint(Color.GRAY);
    }
}
