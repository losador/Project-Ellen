package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Hammer;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.Objects;

public class Locker extends AbstractActor implements Usable<Ripley> {

    private boolean isUsed;

    public Locker(){
        this.isUsed = false;
        setAnimation(new Animation("sprites/locker.png"));
    }

    @Override
    public void useWith(Ripley actor) {
        if(!this.isUsed){
            Objects.requireNonNull(this.getScene()).addActor(new Hammer(), this.getPosX(), this.getPosY());
            this.isUsed = true;
        }
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }
}
