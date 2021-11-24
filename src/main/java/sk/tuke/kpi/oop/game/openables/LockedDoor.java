package sk.tuke.kpi.oop.game.openables;

import sk.tuke.kpi.gamelib.Actor;

public class LockedDoor extends Door{

    private boolean isLocked;

    public LockedDoor(){
        super();
        this.isLocked = true;
    }
    public LockedDoor(String name, Orientation orientation){
        super(name, orientation);
        this.isLocked = true;
    }

    public void lock(){
        this.isLocked = true;
        this.close();
    }

    public void unlock(){
        this.isLocked = false;
        this.open();
    }

    public boolean isLocked(){
        return this.isLocked;
    }

    @Override
    public void useWith(Actor actor) {
        if(!this.isLocked) super.useWith(actor);
    }
}
