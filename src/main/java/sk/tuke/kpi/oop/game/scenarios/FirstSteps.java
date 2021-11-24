package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.*;

public class FirstSteps implements SceneListener {
    private Ripley ripley;

    @Override
    public void sceneInitialized(Scene scene){
        Ripley ripley = new Ripley();
        this.ripley = ripley;
        scene.addActor(ripley);
        MovableController movableController = new MovableController(ripley);
        scene.getInput().registerListener(movableController);
        KeeperController keeperController = new KeeperController(ripley);
        scene.getInput().registerListener(keeperController);
        Energy energy = new Energy();
        scene.addActor(energy, 100, 200);
        Ammo ammo = new Ammo();
        Ammo ammo1 = new Ammo();
        Ammo ammo2 = new Ammo();
        scene.addActor(energy, 100, 200);
        scene.addActor(ammo, 120, 200);
        scene.addActor(ammo1, 150, 200);
        scene.addActor(ammo2, 190, 200);
        new When<>(
            () -> energy.intersects(ripley),
            new Invoke<>(() -> new Use<>(energy).scheduleFor(ripley))
        ).scheduleFor(ripley);
        new When<>(
            () -> ammo.intersects(ripley),
            new Invoke<>(() -> new Use<>(ammo).scheduleFor(ripley))
        ).scheduleFor(ripley);
        new When<>(
            () -> ammo1.intersects(ripley),
            new Invoke<>(() -> new Use<>(ammo1).scheduleFor(ripley))
        ).scheduleFor(ripley);
        new When<>(
            () -> ammo2.intersects(ripley),
            new Invoke<>(() -> new Use<>(ammo2).scheduleFor(ripley))
        ).scheduleFor(ripley);

        Hammer hammer = new Hammer();
        FireExtinguisher fireExtinguisher = new FireExtinguisher();
        Wrench wrench = new Wrench();
        Hammer hammer1 = new Hammer();
        FireExtinguisher fireExtinguisher1 = new FireExtinguisher();
        Wrench wrench1 = new Wrench();
        Hammer hammer2 = new Hammer();
        FireExtinguisher fireExtinguisher2 = new FireExtinguisher();
        Wrench wrench2 = new Wrench();
        Hammer hammer3 = new Hammer();
        FireExtinguisher fireExtinguisher3 = new FireExtinguisher();
        Wrench wrench3 = new Wrench();
        Hammer hammer4 = new Hammer();
        FireExtinguisher fireExtinguisher4 = new FireExtinguisher();
        Wrench wrench4 = new Wrench();

        scene.addActor(hammer, 300, -50);
        scene.addActor(fireExtinguisher, 100, 40);
        scene.addActor(wrench, -150, 200);
        scene.addActor(hammer1, 200, -50);
        scene.addActor(fireExtinguisher1, 50, 40);
        scene.addActor(wrench1, -190, 200);
        scene.addActor(hammer2, 230, -50);
        scene.addActor(fireExtinguisher2, 130, 40);
        scene.addActor(wrench2, -258, 200);
        scene.addActor(hammer3, 360, -50);
        scene.addActor(fireExtinguisher3, 1, 40);
        scene.addActor(wrench3, -30, 200);
        scene.addActor(hammer4, 310, -50);
        scene.addActor(fireExtinguisher4, 100, 80);
        scene.addActor(wrench4, -150, 20);


        scene.getGame().pushActorContainer(ripley.getBackpack());

    }

    @Override
    public void sceneUpdating(@NotNull Scene scene){
        int windowHeight = scene.getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        int windowWidth = scene.getGame().getWindowSetup().getWidth();
        int xTextPos = windowWidth / 6 - GameApplication.STATUS_LINE_OFFSET;
        scene.getGame().getOverlay().drawText(" |  Energy: " + ripley.getHealth().getValue(), xTextPos, yTextPos);
        scene.getGame().getOverlay().drawText(" |  Ammo: " + ripley.getFirearm().getAmmo(), xTextPos + 165, yTextPos);
    }
}
