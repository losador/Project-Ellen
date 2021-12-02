package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Health;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Usable;
import sk.tuke.kpi.oop.game.openables.ForceField;

import java.util.Objects;

public class FieldSwitch extends AbstractActor implements Alive, Destroyable, Usable<Ripley>{

    private Health health;

    public FieldSwitch(){
        this.health = new Health(100);
        setAnimation(new Animation("sprites/destroyable_switch.png", 16, 16, 0.2f, Animation.PlayMode.ONCE));
        this.getAnimation().stop();
        this.health.onExhaustion(() -> {
            this.getAnimation().play();
            ForceField field = Objects.requireNonNull(this.getScene()).getFirstActorByType(ForceField.class);
            field.open();
        });
    }

    @Override
    public Health getHealth() {
        return this.health;
    }

    @Override
    public void useWith(Ripley actor) {
        actor.getHealth().drain(10);
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }
}
