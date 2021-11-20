package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.items.Backpack;

import java.util.Objects;

public class Ripley extends AbstractActor implements Movable, Keeper {

    private int speed;
    private Animation ripleyAnimation;
    private Disposable energyDecreasing;
    private Animation diedAnimation;
    private int energy;
    private int ammo;
    private Backpack backpack;
    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("ripley died", Ripley.class);

    public Ripley(){
         super("Ellen");
         this.speed = 2;
         this.energy = 100;
         this.backpack = new Backpack("Ripley`s backpack", 10);
         this.ripleyAnimation = new Animation("sprites/player.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
         this.diedAnimation = new Animation("sprites/player_die.png", 32, 32, 0.1f, Animation.PlayMode.ONCE);
         setAnimation(ripleyAnimation);
         this.ripleyAnimation.stop();
    }

    @Override
    public int getSpeed(){
        return this.speed;
    }

    @Override
    public void startedMoving(Direction direction) {
        this.ripleyAnimation.setRotation(direction.getAngle());
        this.ripleyAnimation.play();
    }

    @Override
    public void stoppedMoving(){
        this.ripleyAnimation.stop();
    }

    public void setEnergy(int energy){
        this.energy = energy;
    }

    public int getEnergy(){
        return this.energy;
    }

    public void setAmmo(int ammo){
        this.ammo = ammo;
    }

    public int getAmmo(){
        return this.ammo;
    }

    @Override
    public Backpack getBackpack() {
        return this.backpack;
    }

    public void showRipleyState(Ripley ripley, Scene scene){
        int windowHeight = scene.getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        int windowWidth = scene.getGame().getWindowSetup().getWidth();
        int xTextPos = windowWidth / 6 - GameApplication.STATUS_LINE_OFFSET;
        scene.getGame().getOverlay().drawText(" |  Energy: " + ripley.getEnergy(), xTextPos, yTextPos);
        scene.getGame().getOverlay().drawText(" |  Ammo: " + ripley.getAmmo(), xTextPos + 165, yTextPos);
    }

    public void decreaseEnergy(){
        checkEnergy();
        this.energyDecreasing = new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::checkEnergy),
                new Invoke<>(this::decrease),
                new Wait<>(1)
            )
        ).scheduleFor(this);
    }

    private void checkEnergy(){
        if(this.energy <= 0){
            setAnimation(this.diedAnimation);
            Objects.requireNonNull(this.getScene()).getMessageBus().publish(RIPLEY_DIED, this);
        }
    }

    private void decrease(){
        this.setEnergy(this.getEnergy() - 2);
        if(this.getEnergy() < 0) this.setEnergy(0);
    }

    public Disposable getDisposable(){
        return this.energyDecreasing;
    }
}
