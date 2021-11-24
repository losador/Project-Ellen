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
    private Orientation orientation;
    public enum Orientation {VERTICAL, HORIZONTAL};

    public Door(){
        this.openedDoorAnimation = new Animation("sprites/vdoor.png", 16, 32, 0.2f, Animation.PlayMode.ONCE);
        this.closedDoorAnimation = new Animation("sprites/vdoor.png", 16, 32, 0.2f, Animation.PlayMode.ONCE_REVERSED);
        setAnimation(new Animation("sprites/vdoor.png", 16, 32));
        this.getAnimation().stop();
        this.isOpened = false;
    }

    public Door(String name, Orientation orientation){
        super(name);
        this.orientation = orientation;
        if(orientation == Orientation.VERTICAL) {
            this.openedDoorAnimation = new Animation("sprites/vdoor.png", 16, 32, 0.2f, Animation.PlayMode.ONCE);
            this.closedDoorAnimation = new Animation("sprites/vdoor.png", 16, 32, 0.2f, Animation.PlayMode.ONCE_REVERSED);
            setAnimation(new Animation("sprites/vdoor.png", 16, 32));
        }
        else if(orientation == Orientation.HORIZONTAL){
            this.openedDoorAnimation = new Animation("sprites/hdoor.png", 32, 16, 0.2f, Animation.PlayMode.ONCE);
            this.closedDoorAnimation = new Animation("sprites/hdoor.png", 32, 16, 0.2f, Animation.PlayMode.ONCE_REVERSED);
            setAnimation(new Animation("sprites/hdoor.png", 32, 16));
        }
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
        if(this.orientation == Orientation.VERTICAL) {
            Objects.requireNonNull(this.getScene()).getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
            Objects.requireNonNull(this.getScene()).getMap().getTile(this.getPosX() / 16, (this.getPosY() / 16) + 1).setType(MapTile.Type.CLEAR);
        }
        else if(this.orientation == Orientation.HORIZONTAL){
            Objects.requireNonNull(this.getScene()).getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
            Objects.requireNonNull(this.getScene()).getMap().getTile((this.getPosX() / 16) + 1, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
        }
        this.isOpened = true;
        setAnimation(this.openedDoorAnimation);
        this.getAnimation().play();
        this.getScene().getMessageBus().publish(DOOR_OPENED, this);
    }

    @Override
    public void close() {
        if(this.orientation == Orientation.VERTICAL){
            Objects.requireNonNull(this.getScene()).getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
            Objects.requireNonNull(this.getScene()).getMap().getTile(this.getPosX() / 16, (this.getPosY() / 16) + 1).setType(MapTile.Type.WALL);
        }
        else if(this.orientation == Orientation.HORIZONTAL){
            Objects.requireNonNull(this.getScene()).getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
            Objects.requireNonNull(this.getScene()).getMap().getTile((this.getPosX() / 16) + 1, this.getPosY() / 16).setType(MapTile.Type.WALL);
        }
        this.isOpened = false;
        setAnimation(this.closedDoorAnimation);
        this.getAnimation().play();
        this.getScene().getMessageBus().publish(DOOR_CLOSED, this);
    }

    @Override
    public boolean isOpen() {
        return this.isOpened;
    }

    @Override
    public void addedToScene(@NotNull Scene scene){
        super.addedToScene(scene);
        if(this.orientation == Orientation.VERTICAL) {
            Objects.requireNonNull(this.getScene()).getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
            Objects.requireNonNull(this.getScene()).getMap().getTile(this.getPosX() / 16, (this.getPosY() / 16) + 1).setType(MapTile.Type.WALL);
        }
        else if(this.orientation == Orientation.HORIZONTAL){
            Objects.requireNonNull(this.getScene()).getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
            Objects.requireNonNull(this.getScene()).getMap().getTile((this.getPosX() / 16) + 1, this.getPosY() / 16).setType(MapTile.Type.WALL);
        }
    }


}
