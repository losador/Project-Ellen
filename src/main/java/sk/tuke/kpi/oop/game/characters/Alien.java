package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

import java.util.List;
import java.util.Objects;

public class Alien extends AbstractActor implements Enemy, Alive, Movable{

    private Health health;
    private Behaviour<? super Alien> behaviour;

    public Alien(){
        this.health = new Health(100);
        setAnimation(new Animation("sprites/alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG));
        this.health.onExhaustion(() -> {
            this.getScene().removeActor(this);
        });
    }

    public Alien(int health, Behaviour<? super Alien> behaviour){
        this.health = new Health(health, 100);
        this.behaviour = behaviour;
        setAnimation(new Animation("sprites/alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG));
        this.health.onExhaustion(() -> {
            getScene().removeActor(this);
        });
    }

    @Override
    public Health getHealth() {
        return this.health;
    }

    private void attack(){
        List<Actor> Actors;
        Actors = getScene().getActors();

        for(Actor actor : Actors){
            if(actor instanceof Alive && !(actor instanceof Enemy) && this.intersects(actor)){
                ((Alive) actor).getHealth().drain(2);
                break;
            }
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        if(behaviour != null) behaviour.setUp(this);
        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::attack),
                new Wait<>(0.1f)
            )
        ).scheduleFor(this);
    }

    @Override
    public int getSpeed() {
        return 1;
    }

    @Override
    public void collidedWithWall(){
        this.behaviour.setUp(this);
    }

}
