package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

public class RandomlyMoving implements Behaviour<Movable>{

    private Move<Movable> action = null;
    private Movable actor;

    @Override
    public void setUp(Movable actor) {
        if(actor == null) return;
        this.actor = actor;
        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::move),
                new Wait<>(1)
            )
        ).scheduleFor(actor);
    }

    public void move(){
        if(this.actor == null) return;
        if(this.action != null) action.stop();
        int x = (int) (Math.random() * 3) - 1;
        int y = (int) (Math.random() * 3) - 1;

        Direction direction = null;

        for(Direction dir : Direction.values()){
            if(x == dir.getDx() && y == dir.getDy()){
                direction = dir;
            }
        }

        assert direction != null;
        actor.getAnimation().setRotation(direction.getAngle());
        this.action = new Move<>(direction, 1);
        this.action.scheduleFor(this.actor);
    }
}
