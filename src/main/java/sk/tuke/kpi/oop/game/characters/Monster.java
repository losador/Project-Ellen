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
import sk.tuke.kpi.oop.game.Destroyable;
import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;

import java.util.List;

public class Monster extends AbstractActor implements Alive, Enemy, Movable {

    private Health health;
    private Behaviour<? super Monster> behaviour;

    public Monster(int health, Behaviour<? super Monster> behaviour){
        this.behaviour = behaviour;
        this.health = new Health(health);
        setAnimation(new Animation("sprites/monster.png", 78, 127, 0.5f, Animation.PlayMode.LOOP_PINGPONG));
        this.health.onExhaustion(() -> {
            this.getScene().cancelActions(this);
            this.getScene().addActor(new Locker(), this.getPosX(), this.getPosY());
            this.getScene().removeActor(this);
        });
    }

    @Override
    public int getSpeed() {
        return 1;
    }

    @Override
    public Health getHealth() {
        return this.health;
    }

    private void spawnAliens(){
        int x = (int) (Math.random() * this.getWidth());
        int y = (int) (Math.random() * this.getHeight());

        this.getScene().addActor(new Alien(100, new RandomlyMoving()), this.getPosX() + x, this.getPosY() + y);
    }

    private void attack(){
        List<Actor> Actors;
        Actors = getScene().getActors();

        for(Actor actor : Actors){
            if(actor instanceof Alive && !(actor instanceof Enemy) && !(actor instanceof Destroyable) && this.intersects(actor)){
                ((Alive) actor).getHealth().drain(5);
                break;
            }
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene){
        super.addedToScene(scene);
        if(this.behaviour != null) this.behaviour.setUp(this);
        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::spawnAliens),
                new Wait<>(3)
            )
        ).scheduleFor(this);
        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::attack),
                new Wait<>(0.5f)
            )
        ).scheduleFor(this);
    }
}
