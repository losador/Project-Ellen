package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.actions.LaunchRocket;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Usable;

public class Turret extends AbstractActor implements Armed, Usable<Ripley> {

    private Firearm gun;
    private Disposable fire;
    private int delay;
    public static final Topic<Turret> TURRET_DISABLED = Topic.create("turret disabled", Turret.class);

    public Turret(String name, int delay){
        super(name);
        this.delay = delay;
        this.gun = new TurretGun(10000);
        setAnimation(new Animation("sprites/turret.png"));
    }

    private void fire(){
        LaunchRocket<Armed> launch = new LaunchRocket<Armed>();
        launch.setActor(this);
        launch.scheduleFor(this);
    }

    @Override
    public void addedToScene(@NotNull Scene scene){
        super.addedToScene(scene);
        this.fire = new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::fire),
                new Wait<>(delay)
            )
        ).scheduleFor(this);
    }

    @Override
    public Firearm getFirearm() {
        return this.gun;
    }

    @Override
    public void setFirearm(Firearm weapon) {
        this.gun = weapon;
    }

    @Override
    public void useWith(Ripley actor) {
        this.fire.dispose();
        System.out.println("PIZDA");
        this.getScene().getMessageBus().publish(TURRET_DISABLED, this);
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }
}
