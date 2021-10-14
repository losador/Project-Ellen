package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class DefectiveLight extends Light{

    public void defLight(){
        int min = 0, max = 20;
        int r = max - min;
        int random = (int) ((Math.random() * r) - min);
        if(random == 1) toggle();
    }

    @Override
    public void addedToScene(Scene scene){
        super.addedToScene(scene);
        new Invoke<>(this::defLight).scheduleFor(this);
        new Loop<>(new Invoke<>(this::defLight)).scheduleFor(this);
    }
}
