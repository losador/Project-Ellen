package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.Scenario;
import sk.tuke.kpi.oop.game.tools.FireExtinguisher;
import sk.tuke.kpi.oop.game.tools.Hammer;
import sk.tuke.kpi.oop.game.tools.Wrench;

public class Gameplay extends Scenario{

    public void addReactor(Reactor reactor, Scene scene){
        scene.addActor(reactor, 64, 64);
        reactor.turnOn();
    }

    public void addCooler(Scene scene, Cooler cooler){
        scene.addActor(cooler, 200, 64);
        new ActionSequence<>(
            new Wait<>(5),
            new Invoke<>(cooler::turnOn)
        ).scheduleFor(cooler);
    }

    public void repairReactor(Reactor reactor, Hammer hammer){
        new When<>(
            () -> reactor.getTemperature() >= 3000,
            new Invoke<>(() -> reactor.repair())
        ).scheduleFor(reactor);
    }

    public void extinguishReactor(Reactor reactor, FireExtinguisher ext){
        new When<>(
            () -> reactor.getDamage() >= 100,
            new Invoke<>(() -> reactor.extinguish())
        ).scheduleFor(reactor);
        new When<>(
            () -> reactor.getDamage() >= 100,
            new Invoke<>(() -> ext.useWith(reactor))
        ).scheduleFor(reactor);
    }

    public void addDefLight(Reactor reactor, DefectiveLight dfLight, Scene scene){
        scene.addActor(dfLight, 300, 150);
        reactor.addDevice(dfLight);
    }

    public void repairLight(DefectiveLight dfLight, Wrench wrench){
        new ActionSequence<>(
            new Wait<>(10),
            new Invoke<>(dfLight::repair)
        ).scheduleFor(dfLight);
        new ActionSequence<>(
            new Wait<>(10),
            new Invoke<>(wrench::useWith)
        ).scheduleFor(wrench);
    }

    @Override
    public void setupPlay(@NotNull Scene scene) {
        Reactor reactor = new Reactor();
        addReactor(reactor, scene);
        Cooler cooler = new Cooler(reactor);
        //Cooler cooler2 = new Cooler(reactor);
        addCooler(scene, cooler);
        //addCooler(scene, cooler2);
        Hammer hammer = new Hammer();
        scene.addActor(hammer);
        repairReactor(reactor, hammer);
        FireExtinguisher ext = new FireExtinguisher();
        scene.addActor(ext, 170, 98);
        extinguishReactor(reactor, ext);
        Wrench wrench = new Wrench();
        scene.addActor(wrench, 400, 100);
        DefectiveLight dfLight = new DefectiveLight();
        addDefLight(reactor, dfLight, scene);
        repairLight(dfLight, wrench);
    }
}
