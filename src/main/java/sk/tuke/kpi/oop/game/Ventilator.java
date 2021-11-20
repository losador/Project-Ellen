package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.openables.Door;

import java.util.Objects;

public class Ventilator extends AbstractActor implements Repairable {

    private Animation ventAnimation;
    private boolean isBroken;
    public static final Topic<Ventilator> VENTILATOR_REPAIRED = Topic.create("ventilator repaired", Ventilator.class);


    public Ventilator(){
        setAnimation(this.ventAnimation = new Animation("sprites/ventilator.png", 32, 32,0.1f, Animation.PlayMode.LOOP_PINGPONG));
        this.breakVentilator();
    }

    public void breakVentilator(){
        this.isBroken = true;
        this.ventAnimation.stop();
    }

    @Override
    public boolean repair() {
        if(!this.isBroken) return false;
        this.ventAnimation.play();
        this.isBroken = false;
        Objects.requireNonNull(this.getScene()).getMessageBus().publish(VENTILATOR_REPAIRED, this);
        return true;
    }
}
