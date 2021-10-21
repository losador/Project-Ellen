package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Helicopter extends AbstractActor {

    private Animation heliAnim;

    public Helicopter(){
        this.heliAnim = new Animation("sprites/heli.png",64,64,0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(this.heliAnim);
    }

    private void chasing(){
        Player player = (Player) getScene().getFirstActorByName("Player");
        if (player.getPosY() < this.getPosY()) this.setPosition(this.getPosX(),this.getPosY() - 1);
        if (player.getPosY() > this.getPosY()) this.setPosition(this.getPosX(),this.getPosY() + 1);
        if (player.getPosX() < this.getPosX()) this.setPosition(this.getPosX() - 1,this.getPosY());
        if (player.getPosX() > this.getPosX()) this.setPosition(this.getPosX() + 1,this.getPosY());
        if (this.intersects(player)) player.setEnergy(player.getEnergy() - 1);
    }

    public void searchAndDestroy(){
        new Loop<>(new Invoke<>(this::chasing)).scheduleFor(this);
    }

    /*@Override
    public void addedToScene(Scene scene){
        super.addedToScene(scene);
        searchAndDestroy();
    }*/
}
