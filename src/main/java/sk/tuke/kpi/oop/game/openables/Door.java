package sk.tuke.kpi.oop.game.openables;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.Objects;

public class Door extends AbstractActor implements Openable, Usable<Actor> {

    private Animation openedDoorAnimation;
    private Animation closedDoorAnimation;
    public static final Topic<Door> DOOR_OPENED = Topic.create("door opened", Door.class);
    public static final Topic<Door> DOOR_CLOSED = Topic.create("door closed", Door.class);
    private boolean isOpened;

    public Door(){
        this.openedDoorAnimation = new Animation("sprites/vdoor.png", 16, 32, 0.2f, Animation.PlayMode.ONCE);
        this.closedDoorAnimation = new Animation("sprites/vdoor.png", 16, 32, 0.2f, Animation.PlayMode.ONCE_REVERSED);
        setAnimation(new Animation("sprites/vdoor.png", 16, 32));
        this.getAnimation().stop();
        this.isOpened = false;
    }

    @Override
    public void useWith(Actor actor) {
        if(this.isOpened) this.close();
        else this.open();
    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }

    @Override
    public void open() {
        Objects.requireNonNull(this.getScene()).getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
        Objects.requireNonNull(this.getScene()).getMap().getTile(this.getPosX() / 16, (this.getPosY() / 16) + 1).setType(MapTile.Type.CLEAR);
        this.isOpened = true;
        setAnimation(this.openedDoorAnimation);
        this.getAnimation().play();
        this.getScene().getMessageBus().publish(DOOR_OPENED, this);
    }

    @Override
    public void close() {
        this.isOpened = false;
        setAnimation(this.closedDoorAnimation);
        this.getAnimation().play();
        Objects.requireNonNull(this.getScene()).getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
        Objects.requireNonNull(this.getScene()).getMap().getTile(this.getPosX() / 16, (this.getPosY() / 16) + 1).setType(MapTile.Type.WALL);
        this.getScene().getMessageBus().publish(DOOR_CLOSED, this);
    }

    @Override
    public boolean isOpen() {
        return this.isOpened;
    }

    @Override
    public void addedToScene(@NotNull Scene scene){
        super.addedToScene(scene);
        Objects.requireNonNull(this.getScene()).getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
        Objects.requireNonNull(this.getScene()).getMap().getTile(this.getPosX() / 16, (this.getPosY() / 16) + 1).setType(MapTile.Type.WALL);
    }


}
