package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.List;
import java.util.Objects;

public class Bullet extends AbstractActor implements Fireable {
    private int speed;

    public Bullet(){
        setAnimation(new Animation("sprites/bullet.png", 16, 16));
        this.speed = 7;
    }

    @Override
    public int getSpeed() {
        return this.speed;
    }

    @Override
    public void startedMoving(Direction direction) {
        if(direction == null || direction == Direction.NONE) return;
        this.getAnimation().setRotation(direction.getAngle());
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(
            new Invoke<>(this::fire)
        ).scheduleFor(this);

    }

    private void fire(){
        List<Actor> Actors;
        Actors = getScene().getActors();

        for(Actor actor : Actors){
            if(actor instanceof Alive && this.intersects(actor) && !(actor instanceof Ripley)){
                ((Alive)actor).getHealth().drain(35);
                System.out.printf("%s: %d", actor.getName(), ((Alive) actor).getHealth().getValue());
                collidedWithWall();
            }
        }
    }

    @Override
    public void collidedWithWall(){
        Objects.requireNonNull(this.getScene()).removeActor(this);
    }
}
