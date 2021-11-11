package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;

import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.Map;
import java.util.Set;

public class MovableController implements KeyboardListener {
    private Movable actor;
    private Move<Movable> move;
    private Set<Direction> previousDirections;

    public MovableController(Movable actor) {
        this.actor = actor;
    }

    private Map<Input.Key, Direction> keyDirectionMap = Map.ofEntries(
        Map.entry(Input.Key.UP, Direction.NORTH),
        Map.entry(Input.Key.LEFT, Direction.WEST),
        Map.entry(Input.Key.RIGHT, Direction.EAST),
        Map.entry(Input.Key.DOWN, Direction.SOUTH)
    );

    @Override
    public void keyPressed(Input.@NotNull Key key) {
        Direction newDirection = keyDirectionMap.get(key);
        if(newDirection != null) {
            if (this.keyDirectionMap.containsKey(key)) {
                if (this.move != null) this.move.stop();
                this.move = new Move<>(newDirection, Float.MAX_VALUE);
                this.move.scheduleFor(this.actor);
            }
        }
    }

    @Override
    public void keyReleased(Input.@NotNull Key key){
        if(this.keyDirectionMap.containsKey(key)){
            if(this.move != null){
                this.move.stop();
                this.move = null;
            }
        }
    }

}