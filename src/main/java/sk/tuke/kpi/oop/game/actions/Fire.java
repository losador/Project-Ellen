package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.weapons.Fireable;


public class Fire<A extends Armed> extends AbstractAction<A> {
    @Override
    public void execute(float deltaTime) {
        if(this.getActor() == null){
            setDone(true);
            return;
        }

        if(!isDone()){
            Fireable fireable = getActor().getFirearm().fire();
            int x = Direction.fromAngle(getActor().getAnimation().getRotation()).getDx();
            int y = Direction.fromAngle(getActor().getAnimation().getRotation()).getDy();
            if(fireable != null){
                this.getActor().getScene().addActor(fireable, this.getActor().getPosX() + 8 + x*8, this.getActor().getPosY() + 8 + y*8);
                fireable.startedMoving(Direction.fromAngle(getActor().getAnimation().getRotation()));
                new Move<>(Direction.fromAngle(getActor().getAnimation().getRotation()), Float.MAX_VALUE).scheduleFor(fireable);
            }
            setDone(true);
        }
    }
}
