package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.GameApplication;
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
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Gun;

import java.util.Objects;

public class Ripley extends AbstractActor implements Movable, Keeper, Alive, Armed {

    private int speed;
    private Animation ripleyAnimation;
    private Disposable energyDecreasing;
    private Animation diedAnimation;
    private Health currentHealth;
    private Firearm gun;
    private Backpack backpack;
    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("ripley died", Ripley.class);

    public Ripley(){
         super("Ellen");
         this.speed = 2;
         this.currentHealth = new Health(100, 100);
         this.backpack = new Backpack("Ripley`s backpack", 10);
         this.ripleyAnimation = new Animation("sprites/player.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
         this.diedAnimation = new Animation("sprites/player_die.png", 32, 32, 0.1f, Animation.PlayMode.ONCE);
         setAnimation(ripleyAnimation);
         this.ripleyAnimation.stop();
         this.currentHealth.onExhaustion(() -> {
            this.setAnimation(new Animation("sprites/player_die.png",32,32,0.1f, Animation.PlayMode.ONCE));
            Objects.requireNonNull(getScene()).getMessageBus().publish(RIPLEY_DIED,this);
         });
         this.gun = new Gun(100, 500);
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

    @Override
    public Backpack getBackpack() {
        return this.backpack;
    }

    public void showRipleyState(){
        int windowHeight = this.getScene().getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        int windowWidth = this.getScene().getGame().getWindowSetup().getWidth();
        int xTextPos = windowWidth - GameApplication.STATUS_LINE_OFFSET*9;
        this.getScene().getGame().getOverlay().drawText("Health: " + this.currentHealth.getValue() + "  |", xTextPos, yTextPos);
        this.getScene().getGame().getOverlay().drawText("Ammo: " + this.getFirearm().getAmmo(), xTextPos + 165, yTextPos);
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
        if(this.currentHealth.getValue() <= 0){
            setAnimation(this.diedAnimation);
            Objects.requireNonNull(this.getScene()).getMessageBus().publish(RIPLEY_DIED, this);
        }
    }

    public void explode(){
        this.setAnimation(new Animation("sprites/large_explosion.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG));
    }

    private void decrease(){
        this.currentHealth.drain(2);
    }

    public Disposable getDisposable(){
        return this.energyDecreasing;
    }

    @Override
    public Health getHealth() {
        return this.currentHealth;
    }

    @Override
    public Firearm getFirearm() {
        return this.gun;
    }

    @Override
    public void setFirearm(Firearm weapon) {
        this.gun = weapon;
    }
}
