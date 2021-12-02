package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.items.Usable;

public class Tunnel extends AbstractActor implements Usable<Ripley> {

    private Animation openAnim;
    private Animation closeAnim;
    private Disposable loop;

    public Tunnel(){
        this.openAnim = new Animation("sprites/tunnel_black.png", 32, 32, 0.3f, Animation.PlayMode.ONCE);
        this.closeAnim = new Animation("sprites/tunnel_black.png", 32, 32, 0.3f, Animation.PlayMode.ONCE_REVERSED);
        setAnimation(this.openAnim);
    }

    public void spawnAliens(){
        this.getScene().addActor(new Alien(100, new RandomlyMoving()), this.getPosX(), this.getPosY());
    }

    @Override
    public void addedToScene(@NotNull Scene scene){
        super.addedToScene(scene);
        this.loop = new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::spawnAliens),
                new Wait<>(2)
            )
        ).scheduleFor(this);
    }

    @Override
    public void useWith(Ripley actor){
        setAnimation(this.closeAnim);
        this.loop.dispose();
        this.getScene().addActor(new AccessCard(), this.getPosX(), this.getPosY());
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }

}
