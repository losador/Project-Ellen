package sk.tuke.kpi.oop.game.openables;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.messages.Topic;

import java.util.Objects;

public class ForceField extends AbstractActor implements Openable{

    public static final Topic<ForceField> FIELD_DISABLED = Topic.create("field disabled", ForceField.class);
    private boolean isOpen;

    public ForceField(){
        setAnimation(new Animation("sprites/field.png", 144, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG));
        this.isOpen = false;
    }

    @Override
    public void open() {
        this.isOpen = true;
        Objects.requireNonNull(this.getScene()).getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
        this.getScene().getMap().getTile((this.getPosX() / 16) + 1, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
        this.getScene().getMap().getTile((this.getPosX() / 16) + 2, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
        this.getScene().getMap().getTile((this.getPosX() / 16) + 3, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
        this.getScene().getMap().getTile((this.getPosX() / 16) + 4, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
        this.getScene().getMap().getTile((this.getPosX() / 16) + 5, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
        this.getScene().getMap().getTile((this.getPosX() / 16) + 6, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
        this.getScene().getMap().getTile((this.getPosX() / 16) + 7, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
        this.getScene().getMap().getTile((this.getPosX() / 16) + 8, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
        this.getScene().getMap().getTile((this.getPosX() / 16) + 1, (this.getPosY() / 16) + 1).setType(MapTile.Type.CLEAR);
        this.getScene().getMap().getTile((this.getPosX() / 16) + 2, (this.getPosY() / 16) + 1).setType(MapTile.Type.CLEAR);
        this.getScene().getMap().getTile((this.getPosX() / 16) + 3, (this.getPosY() / 16) + 1).setType(MapTile.Type.CLEAR);
        this.getScene().getMap().getTile((this.getPosX() / 16) + 4, (this.getPosY() / 16) + 1).setType(MapTile.Type.CLEAR);
        this.getScene().getMap().getTile((this.getPosX() / 16) + 5, (this.getPosY() / 16) + 1).setType(MapTile.Type.CLEAR);
        this.getScene().getMap().getTile((this.getPosX() / 16) + 6, (this.getPosY() / 16) + 1).setType(MapTile.Type.CLEAR);
        this.getScene().getMap().getTile((this.getPosX() / 16) + 7, (this.getPosY() / 16) + 1).setType(MapTile.Type.CLEAR);
        this.getScene().getMap().getTile((this.getPosX() / 16) + 8, (this.getPosY() / 16) + 1).setType(MapTile.Type.CLEAR);
        this.getScene().removeActor(this);
        this.getScene().getMessageBus().publish(FIELD_DISABLED, this);
    }

    @Override
    public void close() {
        return;
    }

    @Override
    public boolean isOpen() {
        return this.isOpen;
    }

    @Override
    public void addedToScene(@NotNull Scene scene){
        super.addedToScene(scene);
        Objects.requireNonNull(this.getScene()).getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
        this.getScene().getMap().getTile((this.getPosX() / 16) + 1, this.getPosY() / 16).setType(MapTile.Type.WALL);
        this.getScene().getMap().getTile((this.getPosX() / 16) + 2, this.getPosY() / 16).setType(MapTile.Type.WALL);
        this.getScene().getMap().getTile((this.getPosX() / 16) + 3, this.getPosY() / 16).setType(MapTile.Type.WALL);
        this.getScene().getMap().getTile((this.getPosX() / 16) + 4, this.getPosY() / 16).setType(MapTile.Type.WALL);
        this.getScene().getMap().getTile((this.getPosX() / 16) + 5, this.getPosY() / 16).setType(MapTile.Type.WALL);
        this.getScene().getMap().getTile((this.getPosX() / 16) + 6, this.getPosY() / 16).setType(MapTile.Type.WALL);
        this.getScene().getMap().getTile((this.getPosX() / 16) + 7, this.getPosY() / 16).setType(MapTile.Type.WALL);
        this.getScene().getMap().getTile((this.getPosX() / 16) + 8, this.getPosY() / 16).setType(MapTile.Type.WALL);
        this.getScene().getMap().getTile((this.getPosX() / 16) + 1, (this.getPosY() / 16) + 1).setType(MapTile.Type.WALL);
        this.getScene().getMap().getTile((this.getPosX() / 16) + 2, (this.getPosY() / 16) + 1).setType(MapTile.Type.WALL);
        this.getScene().getMap().getTile((this.getPosX() / 16) + 3, (this.getPosY() / 16) + 1).setType(MapTile.Type.WALL);
        this.getScene().getMap().getTile((this.getPosX() / 16) + 4, (this.getPosY() / 16) + 1).setType(MapTile.Type.WALL);
        this.getScene().getMap().getTile((this.getPosX() / 16) + 5, (this.getPosY() / 16) + 1).setType(MapTile.Type.WALL);
        this.getScene().getMap().getTile((this.getPosX() / 16) + 6, (this.getPosY() / 16) + 1).setType(MapTile.Type.WALL);
        this.getScene().getMap().getTile((this.getPosX() / 16) + 7, (this.getPosY() / 16) + 1).setType(MapTile.Type.WALL);
        this.getScene().getMap().getTile((this.getPosX() / 16) + 8, (this.getPosY() / 16) + 1).setType(MapTile.Type.WALL);
    }
}
