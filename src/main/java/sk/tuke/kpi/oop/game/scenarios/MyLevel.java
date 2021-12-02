package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.map.MapMarker;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.oop.game.*;
import sk.tuke.kpi.oop.game.behaviours.FollowingActor;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.Monster;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.*;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.openables.ForceField;
import sk.tuke.kpi.oop.game.openables.LockedDoor;
import sk.tuke.kpi.oop.game.weapons.Turret;

import javax.swing.*;
import java.util.Map;
import java.util.Objects;

public class MyLevel implements SceneListener {

    public static class Factory implements ActorFactory {

        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            if(name == null) return null;
            switch(name){
                case "ellen": return new Ripley();
                case "energy": return new Energy();
                case "ammo": return new Ammo();
                case "alien": if(Objects.equals(type, "running")) return new Alien(100, new RandomlyMoving());
                case "start door": return new LockedDoor("front door", Door.Orientation.VERTICAL);
                case "tunnel": return new Tunnel();
                case "vdoor": return new Door("vdoor", Door.Orientation.VERTICAL);
                case "hdoor": return new Door("hdoor", Door.Orientation.HORIZONTAL);
                case "exit door": return new LockedDoor("exit door", Door.Orientation.HORIZONTAL);
                case "field": return new ForceField();
                case "ventilator": return new Ventilator();
                case "locker": return new Locker();
                case "hammer": return new Hammer();
                case "end_tp": return new EndTeleport();
                case "field switch": return new FieldSwitch();
                case "turret1": return new Turret("turret1", 3);
                case "turret2": return new Turret("turret2", 4);
                case "turret3": return new Turret("turret3", 2);
                case "terminal": return new Terminal("7266638");
                case "card": return new Card();
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

        Map<String, MapMarker> markers = scene.getMap().getMarkers();
        MapMarker boss = markers.get("boss room");
        MapMarker monsterArea = markers.get("monster");
        MapMarker fieldM = markers.get("fieldM");

        MapMarker card = markers.get("card");
        scene.getMessageBus().subscribe(ForceField.FIELD_DISABLED, (Scene) -> scene.addActor(new Monster(1000, new FollowingActor()), monsterArea.getPosX(), monsterArea.getPosY()));
        ForceField field = new ForceField();
        new When<>(
            () -> ripley.getPosY() < boss.getPosY() && (ripley.getPosX() > boss.getPosX()),
            new ActionSequence<>(
                new Wait<>(1),
                new Invoke<>(() -> scene.addActor(field, fieldM.getPosX(), fieldM.getPosY()))
            )
        ).scheduleOn(scene);
        scene.getMessageBus().subscribe(Monster.MONSTER_DIED, (Scene) -> this.removeField(scene, field));

        scene.getMessageBus().subscribe(Turret.TURRET_DISABLED, turret -> {
            if(turret.getName().equals("turret2")){
                scene.addActor(new Card(), card.getPosX(), card.getPosY());
            }
        });

        scene.getGame().pushActorContainer(ripley.getBackpack());

        EndTeleport end = scene.getFirstActorByType(EndTeleport.class);
        assert end != null;
        end.addedToScene(scene);

        scene.getMessageBus().subscribe(EndTeleport.PLAYER_IN, (Ripley) -> {new ActionSequence<>(
                                                                               new Invoke<>(() -> this.end(ripley, move, keep, shoot)),
                                                                               new Wait<>(2)
                                                                            ).scheduleFor(ripley);
                                                                            new ActionSequence<>(
                                                                                new Wait<>(2),
                                                                                new Invoke<>(this::showDialog)
                                                                            ).scheduleOn(scene);
        });
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> move.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> keep.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> shoot.dispose());

        scene.getMessageBus().subscribe(Ventilator.VENTILATOR_REPAIRED, (Scene) -> scene.addActor(new AccessCard(), 665, 760));

        Game game = scene.getGame();

        scene.getGame().getInput().onKeyPressed(Input.Key.ESCAPE, game::stop);

    }

    private void end(Ripley ripley, Disposable move, Disposable keep, Disposable shoot){
        System.out.println("JJJ");
        ripley.explode();
        ripley.getScene().cancelActions(ripley);
        move.dispose();
        keep.dispose();
        shoot.dispose();
    }

    private void removeField(Scene scene, ForceField field){
        scene.getMap().getTile(field.getPosX() / 16, field.getPosY() / 16).setType(MapTile.Type.CLEAR);
        scene.getMap().getTile((field.getPosX() / 16) + 1, field.getPosY() / 16).setType(MapTile.Type.CLEAR);
        scene.getMap().getTile((field.getPosX() / 16) + 2, field.getPosY() / 16).setType(MapTile.Type.CLEAR);
        scene.getMap().getTile((field.getPosX() / 16) + 3, field.getPosY() / 16).setType(MapTile.Type.CLEAR);
        scene.getMap().getTile((field.getPosX() / 16) + 4, field.getPosY() / 16).setType(MapTile.Type.CLEAR);
        scene.getMap().getTile((field.getPosX() / 16) + 5, field.getPosY() / 16).setType(MapTile.Type.CLEAR);
        scene.getMap().getTile((field.getPosX() / 16) + 6, field.getPosY() / 16).setType(MapTile.Type.CLEAR);
        scene.getMap().getTile((field.getPosX() / 16) + 7, field.getPosY() / 16).setType(MapTile.Type.CLEAR);
        scene.getMap().getTile((field.getPosX() / 16) + 8, field.getPosY() / 16).setType(MapTile.Type.CLEAR);
        scene.getMap().getTile((field.getPosX() / 16) + 1, (field.getPosY() / 16) + 1).setType(MapTile.Type.CLEAR);
        scene.getMap().getTile((field.getPosX() / 16) + 2, (field.getPosY() / 16) + 1).setType(MapTile.Type.CLEAR);
        scene.getMap().getTile((field.getPosX() / 16) + 3, (field.getPosY() / 16) + 1).setType(MapTile.Type.CLEAR);
        scene.getMap().getTile((field.getPosX() / 16) + 4, (field.getPosY() / 16) + 1).setType(MapTile.Type.CLEAR);
        scene.getMap().getTile((field.getPosX() / 16) + 5, (field.getPosY() / 16) + 1).setType(MapTile.Type.CLEAR);
        scene.getMap().getTile((field.getPosX() / 16) + 6, (field.getPosY() / 16) + 1).setType(MapTile.Type.CLEAR);
        scene.getMap().getTile((field.getPosX() / 16) + 7, (field.getPosY() / 16) + 1).setType(MapTile.Type.CLEAR);
        scene.getMap().getTile((field.getPosX() / 16) + 8, (field.getPosY() / 16) + 1).setType(MapTile.Type.CLEAR);
        scene.removeActor(field);
    }

    private void showDialog(){
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, "Oh no...This is captain Zorg... He...he killed Ripley. We have to get out of here. You done your mission Ripley. Rest in peace. We`ll remember you!", "End", JOptionPane.PLAIN_MESSAGE);
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        Ripley ripley = scene.getFirstActorByType(Ripley.class);
        assert ripley != null;
        ripley.showRipleyState();
    }


}

