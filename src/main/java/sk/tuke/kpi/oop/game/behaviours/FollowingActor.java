package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.Objects;

public class FollowingActor implements Behaviour<Movable>{

    private Movable actor;
    private Move<Movable> move;

    @Override
    public void setUp(Movable actor) {
        if(actor == null) return;
        this.actor = actor;
        new Loop<>(
                new Invoke<>(this::follow)
        ).scheduleFor(actor);
    }

    public void follow(){
        Ripley ripley = Objects.requireNonNull(this.actor.getScene()).getFirstActorByType(Ripley.class);

        assert ripley != null;

        int dx = 0, dy = 0;

        if (this.actor.getPosY() < ripley.getPosY()) dy = 1;
        if (this.actor.getPosY() > ripley.getPosY()) dy = -1;
        if(this.actor.getPosY() == ripley.getPosY()) dy = 0;
        if (this.actor.getPosX() < ripley.getPosX()) dx = 1;
        if (this.actor.getPosX() > ripley.getPosX()) dx = -1;
        if(this.actor.getPosX() == ripley.getPosX()) dx = 0;
        Direction direction = null;

        for(Direction dir : Direction.values()){
            if(dx == dir.getDx() && dy == dir.getDy()){
                direction = dir;
            }
        }

        if(this.move != null){
            this.move.stop();
            this.move = null;
        }

        if(direction != null){
            this.move = new Move<>(direction, Float.MAX_VALUE);
            this.move.scheduleFor(this.actor);
            this.actor.getAnimation().setRotation(direction.getAngle());
        }

    }
}
