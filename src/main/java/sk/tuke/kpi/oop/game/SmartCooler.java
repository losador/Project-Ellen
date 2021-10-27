package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class SmartCooler extends Cooler{

    private Reactor currReactor;

    public SmartCooler(Reactor reactor) {
        super(reactor);
        this.currReactor = reactor;
    }

    public void cool(){
        if(this.currReactor == null) return;
        if(this.currReactor.getTemperature() > 2500){
            turnOn();
        }
        else if(this.currReactor.getTemperature() < 1500){
            turnOff();
        }
    }

    @Override
    public void addedToScene(Scene scene){
        super.addedToScene(scene);
        new Invoke<>(this::cool).scheduleFor(this);
        new Loop<>(new Invoke<>(this::cool)).scheduleFor(this);
    }
}
