package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Alive;

import java.util.List;
import java.util.Objects;

public class Rocket extends AbstractActor implements Fireable{

    private Animation rocketAnimation;
    private Animation explodeAnimation;

    public Rocket(){
        this.rocketAnimation = new Animation("sprites/rocket.png");
        this.explodeAnimation = new Animation("sprites/large_explosion.png", 32, 32, 0.1f, Animation.PlayMode.ONCE);
        setAnimation(this.rocketAnimation);
    }

    @Override
    public int getSpeed() {
        return 3;
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
            if(actor instanceof Alive && this.intersects(actor) && !(actor instanceof Turret)){
                ((Alive)actor).getHealth().drain(40);
                collidedWithWall();
                break;
            }
        }
    }

    @Override
    public void collidedWithWall(){
        this.getScene().cancelActions(this);
        this.setAnimation(this.explodeAnimation);
        new When<>(
            () -> this.explodeAnimation.getCurrentFrameIndex() >= 7,
            new Invoke<>(() -> Objects.requireNonNull(this.getScene()).removeActor(this))
        ).scheduleFor(this);
    }
}
