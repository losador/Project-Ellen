package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.items.Collectible;
import sk.tuke.kpi.oop.game.items.Usable;

public class KeeperController implements KeyboardListener {

    private Keeper keeper;

    public KeeperController(Keeper keeper){
        this.keeper = keeper;
    }

    @Override
    public void keyPressed(Input.@NotNull Key key) {
        if(key == Input.Key.ENTER){
            new Take<>(this.keeper).scheduleFor(this.keeper);
        }
        if(key == Input.Key.BACKSPACE){
            new Drop<>(this.keeper).scheduleFor(this.keeper);
        }
        if(key == Input.Key.S){
            new Shift<>(this.keeper).scheduleFor(this.keeper);
        }
        if(key == Input.Key.U){
            this.useKey();
        }
        if(key == Input.Key.B){
            this.bKey();
        }
    }

    public void useKey(){
        for (Actor item : this.keeper.getScene().getActors()) {
            if (item instanceof Usable && this.keeper.intersects(item)) {
                new Use<>((Usable<?>) item).scheduleForIntersectingWith(this.keeper);
            }
        }
    }

    public void bKey(){
        Collectible item = this.keeper.getBackpack().peek();
        if(item == null) return;
        if(item instanceof Usable){
            if(new Use<>((Usable<?>) item).scheduleForIntersectingWith(this.keeper) != null) this.keeper.getBackpack().remove(item);
        }
    }
}
