package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Terminal;

public class Card extends AbstractActor implements Collectible, Usable<Terminal> {

    public Card(){
        setAnimation(new Animation("sprites/card.png"));
    }

    @Override
    public void useWith(Terminal actor) {
        actor.refillAttempts();
    }

    @Override
    public Class<Terminal> getUsingActorClass() {
        return Terminal.class;
    }
}
