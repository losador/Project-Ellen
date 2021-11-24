package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.behaviours.Observing;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.AlienMother;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;

import java.util.Objects;

public class EscapeRoom implements SceneListener {

    public static class Factory implements ActorFactory {

        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            if(name == null) return null;
            switch(name){
                case "energy": return new Energy();
                case "ellen": return new Ripley();
                case "alien": if(Objects.equals(type, "running")) return new Alien(100, new RandomlyMoving());
                              else if(Objects.equals(type, "waiting1")) return new Alien(100, new Observing<>(Door.DOOR_OPENED,
                                                                                                                       door -> door.getName().equals("front door"),
                                                                                                                       new RandomlyMoving()));
                              else if(Objects.equals(type, "waiting2")) return new Alien(100, new Observing<>(Door.DOOR_OPENED,
                                                                                                                       door -> door.getName().equals("back door"),
                                                                                                                       new RandomlyMoving()));
                case "ammo": return new Ammo();
                case "alien mother": return new AlienMother(200, new Observing<>(Door.DOOR_OPENED,
                                                                                       door -> door.getName().equals("back door"),
                                                                                       new RandomlyMoving()));
                case "front door": return new Door("front door", Door.Orientation.VERTICAL);
                case "back door": return new Door("back door", Door.Orientation.HORIZONTAL);
                case "exit door": return new Door("exit door", Door.Orientation.VERTICAL);
                default: return null;
            }
        }
    }

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        Ripley ripley = scene.getFirstActorByType(Ripley.class);
        assert ripley != null;
        scene.follow(ripley);

        MovableController movableController = new MovableController(ripley);
        Disposable move = scene.getInput().registerListener(movableController);
        KeeperController keeperController = new KeeperController(ripley);
        Disposable keep = scene.getInput().registerListener(keeperController);
        ShooterController shooterController = new ShooterController(ripley);
        Disposable shoot = scene.getInput().registerListener(shooterController);

        scene.getGame().pushActorContainer(ripley.getBackpack());

        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> move.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> keep.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> shoot.dispose());

        scene.getMessageBus().subscribe(Door.DOOR_OPENED, door -> { if(door.getName().equals("exit door")){
                                                                        move.dispose();
                                                                        keep.dispose();
                                                                        shoot.dispose();
                                                                    }});
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        Ripley ripley = scene.getFirstActorByType(Ripley.class);
        assert ripley != null;
        ripley.showRipleyState();
    }


}
