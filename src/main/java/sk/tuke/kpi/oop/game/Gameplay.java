package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.Scenario;
import sk.tuke.kpi.gamelib.map.MapMarker;
import sk.tuke.kpi.oop.game.Computer;
import sk.tuke.kpi.oop.game.Cooler;
import sk.tuke.kpi.oop.game.DefectiveLight;
import sk.tuke.kpi.oop.game.Reactor;
import sk.tuke.kpi.oop.game.tools.FireExtinguisher;
import sk.tuke.kpi.oop.game.tools.Hammer;
import sk.tuke.kpi.oop.game.tools.Wrench;

import java.text.spi.CollatorProvider;
import java.util.Map;

public class Gameplay extends Scenario implements SceneListener {

    public void addReactor(Reactor reactor, Scene scene, Map<String,MapMarker> markers){// obtaining reference to marker named "reactor-area-1"
        MapMarker reactorArea1 = markers.get("reactor-area-1");
        scene.addActor(reactor, reactorArea1.getPosX(), reactorArea1.getPosY());
        reactor.turnOn();
    }

    public void addReactor2(Reactor reactor, Scene scene, Map<String,MapMarker> markers){// obtaining reference to marker named "reactor-area-1"
        MapMarker reactorArea2 = markers.get("reactor-area-2");
        scene.addActor(reactor, reactorArea2.getPosX(), reactorArea2.getPosY());
        reactor.turnOn();
    }


    public void addCooler(Scene scene, Cooler cooler, Map<String,MapMarker> markers){
        MapMarker coolerArea1 = markers.get("cooler-area-1");
        scene.addActor(cooler, coolerArea1.getPosX(), coolerArea1.getPosY());
        new ActionSequence<>(
            new Wait<>(5),
            new Invoke<>(cooler::turnOn)
        ).scheduleFor(cooler);
    }
    public void addCooler2(Scene scene, Cooler cooler, Map<String,MapMarker> markers){
        MapMarker coolerArea2 = markers.get("cooler-area-2");
        scene.addActor(cooler, coolerArea2.getPosX(), coolerArea2.getPosY());
        new ActionSequence<>(
            new Wait<>(5),
            new Invoke<>(cooler::turnOn)
        ).scheduleFor(cooler);
    }
    public void addCooler3(Scene scene, Cooler cooler, Map<String,MapMarker> markers){
        MapMarker coolerArea3 = markers.get("cooler-area-3");
        scene.addActor(cooler, coolerArea3.getPosX(), coolerArea3.getPosY());
        new ActionSequence<>(
            new Wait<>(5),
            new Invoke<>(cooler::turnOn)
        ).scheduleFor(cooler);
    }

    public void addComputer(Reactor reactor, Scene scene, Computer computer, Map<String,MapMarker> markers){
        MapMarker computerArea = markers.get("computer-area");
        scene.addActor(computer, computerArea.getPosX(), computerArea.getPosY());
        reactor.addDevice(computer);
    }

    public void repairReactor(Reactor reactor, Hammer hammer){
        new When<>(
            () -> reactor.getTemperature() >= 3000,
            new Invoke<>(reactor::repair)
        ).scheduleFor(reactor);
    }

    public void extinguishReactor(Reactor reactor, FireExtinguisher ext){
        new When<>(
            () -> reactor.getDamage() >= 100,
            new Invoke<>(reactor::extinguish)
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
        ).scheduleFor(dfLight);
    }

    @Override
    public void setupPlay(@NotNull Scene scene) {
        Map<String, MapMarker> markers = scene.getMap().getMarkers();
        Reactor reactor = new Reactor();
        addReactor(reactor, scene, markers);
        Reactor reactor2 = new Reactor();
        addReactor2(reactor2, scene, markers);
        Cooler cooler = new Cooler(reactor);
        Cooler cooler2 = new Cooler(reactor2);
        Cooler cooler3 = new Cooler(reactor2);
        addCooler(scene, cooler, markers);
        addCooler2(scene, cooler2, markers);
        addCooler3(scene, cooler3, markers);
        Computer computer = new Computer();
        addComputer(reactor2, scene, computer, markers);
        Hammer hammer = new Hammer(1);
        scene.addActor(hammer);
        repairReactor(reactor, hammer);
        FireExtinguisher ext = new FireExtinguisher();
        scene.addActor(ext);
        extinguishReactor(reactor, ext);
        Wrench wrench = new Wrench();
        scene.addActor(wrench);
        DefectiveLight dfLight = new DefectiveLight();
        addDefLight(reactor, dfLight, scene);
        repairLight(dfLight, wrench);
    }
}
