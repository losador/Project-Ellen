package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;

import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MovableController implements KeyboardListener {
    private Movable actor;
    private Move<Movable> move;
    private Set<Direction> previousDirections = new HashSet<>();

    public MovableController(Movable actor) {
        this.actor = actor;
    }

    private Map<Input.Key, Direction> keyDirectionMap = Map.ofEntries(
        Map.entry(Input.Key.UP, Direction.NORTH),
        Map.entry(Input.Key.LEFT, Direction.WEST),
        Map.entry(Input.Key.RIGHT, Direction.EAST),
        Map.entry(Input.Key.DOWN, Direction.SOUTH)
    );

    private Direction combineDirections(Direction newDirection){
        for(Direction dir : this.previousDirections){
            if(dir != newDirection) return newDirection.combine(dir);
        }
        return newDirection;
    }

    @Override
    public void keyPressed(@NotNull Input.Key key) {
        Direction newDirection = keyDirectionMap.get(key);
        if(newDirection != null && this.keyDirectionMap.containsKey(key)) {
            this.previousDirections.add(newDirection);
            if(this.previousDirections.size() > 1) newDirection = combineDirections(newDirection);
            if (this.move != null) {
                this.move.stop();
                this.move = null;
            }
            this.move = new Move<>(newDirection, Float.MAX_VALUE);
            this.move.scheduleFor(this.actor);
        }
    }

    @Override
    public void keyReleased(@NotNull Input.Key key){
        if(this.keyDirectionMap.containsKey(key) && this.move != null){
            this.previousDirections.remove(this.keyDirectionMap.get(key));
            this.move.stop();
            this.move = null;
            if(!this.previousDirections.isEmpty()){
                Direction newDirection = Direction.NONE;
                for (Direction dir : this.previousDirections) {
                    newDirection = newDirection.combine(dir);
                }
                this.move = new Move<>(newDirection, Float.MAX_VALUE);
                this.move.scheduleFor(this.actor);
            }
        }
    }

}
