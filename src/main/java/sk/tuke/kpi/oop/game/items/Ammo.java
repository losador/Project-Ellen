package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.Objects;

public class Ammo extends AbstractActor implements Usable<Ripley>{

    public Ammo(){
        setAnimation(new Animation("sprites/ammo.png"));
    }

    @Override
    public void useWith(Ripley ripley) {
        if(ripley == null || ripley.getAmmo() + 50 > 500) return;
        ripley.setAmmo(ripley.getAmmo() + 50);
        Objects.requireNonNull(this.getScene()).removeActor(this);
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }
}
