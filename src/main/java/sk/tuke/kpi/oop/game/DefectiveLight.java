package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class DefectiveLight extends Light implements Repairable{

    private Disposable disposeLight;
    private boolean repaired;

    public DefectiveLight(){
        super();
        this.repaired = false;
    }

    public void startBlink(){
        this.repaired = false;
        int min = 0, max = 20;
        int r = max - min;
        int random = (int) ((Math.random() * r) - min);
        if(random == 1) toggle();
    }

    @Override
    public void addedToScene(@NotNull Scene scene){
        super.addedToScene(scene);
        this.disposeLight = new Loop<>(new Invoke<>(this::startBlink)).scheduleFor(this);
    }

    public void breakLight(){
        this.disposeLight = new Loop<>(new Invoke<>(this::startBlink)).scheduleFor(this);
    }

    @Override
    public boolean repair(){
        if(repaired) return false;
        this.repaired = true;
        this.disposeLight.dispose();
        turnOn();
        new ActionSequence<>(
            new Wait<>(10),
            new Invoke<>(this::turnOn)
        ).scheduleFor(this);
        new ActionSequence<>(
            new Wait<>(10),
            new Invoke<>(this::breakLight)
        ).scheduleFor(this);
        return true;
    }
}
