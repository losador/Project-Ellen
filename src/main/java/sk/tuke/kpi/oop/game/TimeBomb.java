package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

import javax.swing.*;
import java.util.Objects;

public class TimeBomb extends AbstractActor {
    private float time;
    private boolean isActivated;
    private boolean isExploded;
    private Animation bombAnimation;
    private Animation activeAnimation;
    private Animation explodeAnimation;

    public TimeBomb(float time){
        this.time = time;
        this.isActivated = false;
        this.isExploded = false;
        this.activeAnimation = new Animation("sprites/bomb_activated.png", 16, 16, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        this.explodeAnimation = new Animation("sprites/small_explosion.png", 16, 16, 0.1f, Animation.PlayMode.ONCE);
        setAnimation(this.bombAnimation = new Animation("sprites/bomb.png"));
    }

    public boolean isActivated(){
        return this.isActivated;
    }

    public boolean isExploded() {
        return isExploded;
    }

    public void explode(){
        this.isExploded = true;
        setAnimation(this.explodeAnimation);
    }

    public void removeBomb(){
        new When<>(
            () -> this.explodeAnimation.getCurrentFrameIndex() >= 7,
            new Invoke<>(() -> Objects.requireNonNull(this.getScene()).removeActor(this))
        ).scheduleFor(this);
    }

    public void activate(){
        this.isActivated = true;
        setAnimation(this.activeAnimation);
        new ActionSequence<>(
            new Wait<>(this.time),
            new Invoke<>(this::explode),
            new Invoke<>(this::removeBomb)
        ).scheduleFor(this);
    }
}
