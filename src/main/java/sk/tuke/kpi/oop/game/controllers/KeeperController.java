package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;

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
    }
}
