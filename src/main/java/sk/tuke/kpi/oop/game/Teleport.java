package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

import java.util.Objects;

public class Teleport extends AbstractActor {
    private Animation teleportAnimation;
    private Teleport destTeleport;
    private boolean isActive;

    public Teleport(Teleport teleport){
        this.destTeleport = teleport;
        setAnimation(this.teleportAnimation = new Animation("sprites/lift.png"));
        this.isActive = false;
    }

    public void setDestination(Teleport destinationTeleport){
        this.destTeleport = destinationTeleport;
        this.destTeleport.isActive = false;
    }

    public boolean ifIntersects(Teleport tp, Actor actor){
        int xTp, yTp, xAc, yAc;
        xTp = tp.getPosX() + tp.getWidth()/2;
        yTp = tp.getPosY() + tp.getHeight()/2;
        xAc = actor.getPosX() + actor.getWidth()/2;
        yAc = actor.getPosY() + actor.getHeight()/2;
        if(((xAc > xTp - 5) && (xAc < xTp + 5)) && ((yAc > yTp - 5) && (yAc < yTp + 5))) return true;
        return false;
    }

    public void teleportPLayer(){
        int x, y;
        Player player = (Player) getScene().getFirstActorByName("Player");
        if(this.destTeleport != null) {
            if(this.isActive){
                if(this.intersects(player)) return;
                else this.isActive = false;
            }
            x = this.destTeleport.getPosX() + (this.destTeleport.getWidth()/4);
            y = this.destTeleport.getPosY() + (this.destTeleport.getHeight()/4);
            if(ifIntersects(this, player)) {player.setPosition(x, y); this.destTeleport.isActive = true;}
        }
    }

    @Override
    public void addedToScene(Scene scene){
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::teleportPLayer)).scheduleFor(this);
    }
}
