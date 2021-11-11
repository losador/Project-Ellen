package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;

public class Move<A extends Movable> implements Action<A> {

    private A actor;
    private Direction direction;
    private float duration;
    private boolean isDone;
    private int isCalledFirstTime;

    public Move(Direction direction, float duration) {
        this.direction = direction;
        this.duration = duration;
        this.isDone = false;
        this.isCalledFirstTime = 0;
    }

    private Move(Direction direction) {
        this.direction = direction;
        duration = 0;
        isDone = false;
        this.isCalledFirstTime = 0;
    }


    @Override
    public A getActor(){
        return this.actor;
    }

    @Override
    public void setActor(@Nullable A actor) {
        this.actor = actor;
    }

    @Override
    public boolean isDone() {
        return this.isDone;
    }

    @Override
    public void execute(float deltaTime) {
        this.duration -= deltaTime;
        if(!isDone){
            if(this.isCalledFirstTime == 0){
                this.actor.startedMoving(this.direction);
                this.isCalledFirstTime = 1;
            }
            if(this.duration > 0){
                this.actor.setPosition(this.actor.getPosX() + this.direction.getDx() * this.actor.getSpeed(), this.actor.getPosY() + this.direction.getDy() * this.actor.getSpeed());
                if(this.getActor().getScene().getMap().intersectsWithWall(this.actor)){
                    this.actor.setPosition(this.actor.getPosX() - this.direction.getDx() * this.actor.getSpeed(), this.actor.getPosY() - this.direction.getDy() * this.actor.getSpeed());
                }
            }
            else{
                this.stop();
            }
        }
    }

    public void stop(){
        this.isDone = true;
        this.actor.stoppedMoving();
        this.isCalledFirstTime = 0;
    }

    @Override
    public void reset() {
        this.actor.stoppedMoving();
        this.isDone = false;
        this.duration = 0;
    }
}
