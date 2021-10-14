package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Cooler extends AbstractActor {
    private Animation onAnimation;
    private Animation offAnimation;
    private Reactor currReactor;
    private boolean state;

    public Cooler(Reactor reactor){
        this.state = false;
        this.currReactor = reactor;
        this.onAnimation = new Animation("sprites/fan.png", 32, 32, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        this.offAnimation = new Animation("sprites/fan.png", 32, 32, 0.0f, Animation.PlayMode.ONCE_REVERSED);
        setAnimation(offAnimation);
    }

    public void turnOn(){
        this.state = true;
        setAnimation(this.onAnimation);
    }

    public void turnOff(){
        this.state = false;
        setAnimation(this.offAnimation);
    }

    public boolean isOn(){
        return this.state;
    }

    private void coolReactor(){
        if(this.state) return;
        if(this.currReactor.getTemperature() > 0) this.currReactor.decreaseTemperature(1);
    }

    @Override
    public void addedToScene(Scene scene){
        super.addedToScene(scene);
        new Invoke<>(this::coolReactor).scheduleFor(this);
        new Loop<>(new Invoke<>(this::coolReactor)).scheduleFor(this);
    }

}
