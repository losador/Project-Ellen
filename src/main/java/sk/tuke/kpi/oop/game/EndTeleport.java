package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.Objects;

public class EndTeleport extends AbstractActor {

    private Disposable loop;
    public static final Topic<EndTeleport> PLAYER_IN = Topic.create("player in", EndTeleport.class);


    public EndTeleport(){
        setAnimation(new Animation("sprites/lift.png"));
    }

    public boolean ifIntersects(EndTeleport tp, Ripley ripley){
        int xTp, yTp, xAc, yAc;
        xTp = tp.getPosX() + tp.getWidth()/2;
        yTp = tp.getPosY() + tp.getHeight()/2;
        xAc = ripley.getPosX() + ripley.getWidth()/2;
        yAc = ripley.getPosY() + ripley.getHeight()/2;
        if(((xAc > xTp - 10) && (xAc < xTp + 10)) && ((yAc > yTp - 10) && (yAc < yTp + 10))) return true;
        return false;
    }

    private void teleportOn(Ripley ripley){
        if(ifIntersects(this, ripley)){
            this.start();
        }
    }

    private void start(){
        this.loop.dispose();
        this.getScene().getMessageBus().publish(PLAYER_IN, this);
    }

    @Override
    public void addedToScene(Scene scene){
        super.addedToScene(scene);
        Ripley ripley = Objects.requireNonNull(getScene()).getFirstActorByType(Ripley.class);
        if(ripley != null) this.loop = new Loop<>(new Invoke<>(this::teleportOn)).scheduleFor(ripley);
    }
}
