package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

import java.util.Objects;

public class Teleport extends AbstractActor {
    private Teleport destTeleport;
    private int checkCounter = 0;
    private boolean isActive;
    private Disposable disposeTp;

    public Teleport(Teleport teleport){
        this.destTeleport = teleport;
        setAnimation(new Animation("sprites/lift.png"));
        this.isActive = false;
    }

    public void setDestination(Teleport destinationTeleport){
        if(destinationTeleport == this) return;
        this.destTeleport = destinationTeleport;
    }

    public Teleport getDestination(){
        if(this.destTeleport == null) return null;
        return this.destTeleport;
    }

    private boolean isInMiddle(Player player){
        if(player.getPosX() == this.getPosX() + (this.getWidth()/6) && player.getPosY() == this.getPosY() + (this.getHeight()/6)) return true;
        return false;
    }

    private boolean check(Player player){
        if(this.isActive) return false;
        if(!ifIntersects(this, player)) return false;
        return true;
    }

    public boolean ifIntersects(Teleport tp, Player player){
        int xTp, yTp, xAc, yAc;
        xTp = tp.getPosX() + tp.getWidth()/2;
        yTp = tp.getPosY() + tp.getHeight()/2;
        xAc = player.getPosX() + player.getWidth()/2;
        yAc = player.getPosY() + player.getHeight()/2;
        if(((xAc > xTp - 24) && (xAc < xTp + 24)) && ((yAc > yTp - 24) && (yAc < yTp + 24))) return true;
        return false;
    }

    private boolean playerInTp(Player player){
        if(check(player)) return true;
        return false;
    }

    private void teleportOn(Player player){
        if(playerInTp(player) && this.destTeleport != null){
            this.destTeleport.teleportPlayer(player);
        }
    }

    public void teleportPlayer(Player player){
        int x, y;
        if(player != null) {
            x = this.getPosX() + (this.getWidth()/6);
            y = this.getPosY() + (this.getHeight()/6);
            player.setPosition(x, y);
            this.isActive = true;
            new When<>(
                () -> (!player.intersects(this) && !this.playerInTp(player)),
                new Invoke<>(() -> { this.isActive = false; })
            ).scheduleFor(this);
        }
    }



    @Override
    public void addedToScene(Scene scene){
        super.addedToScene(scene);
        Player player = (Player) Objects.requireNonNull(getScene()).getFirstActorByName("Player");
        this.disposeTp = new Loop<>(new Invoke<>(this::teleportOn)).scheduleFor(player);
    }
}
