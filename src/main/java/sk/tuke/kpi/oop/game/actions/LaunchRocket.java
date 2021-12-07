package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.weapons.Fireable;


public class LaunchRocket<A extends Armed> extends AbstractAction<A> {
    @Override
    public void execute(float deltaTime) {
        if(this.getActor() == null){
            setDone(true);
            return;
        }

        if(!isDone()){
            Fireable fireable = this.getActor().getFirearm().fire();
            if(fireable != null){
                this.getActor().getScene().addActor(fireable, this.getActor().getPosX() + (this.getActor().getWidth()/3), this.getActor().getPosY() + (this.getActor().getHeight()));
                fireable.startedMoving(Direction.fromAngle(getActor().getAnimation().getRotation()));
                new Move<>(Direction.fromAngle(getActor().getAnimation().getRotation()), Float.MAX_VALUE).scheduleFor(fireable);
            }
            setDone(true);
        }
    }
}
