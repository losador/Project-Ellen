package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.ActorContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class Backpack implements ActorContainer<Collectible> {
    private String name;
    private int capacity;
    private List<Collectible> items = new ArrayList<>();

    public Backpack(String name, int capacity){
        this.name = name;
        this.capacity = capacity;
    }

    @Override
    public @NotNull List<Collectible> getContent() {
        return List.copyOf(items);
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public int getSize() {
        return items.size();
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public void add(@NotNull Collectible actor) {
        if(this.getSize() < this.getCapacity()) this.items.add(actor);
        else throw new IllegalStateException("Backpack " + getName() + " is full");
    }

    @Override
    public void remove(@NotNull Collectible actor) {
        this.items.remove(actor);
    }

    @Override
    public @Nullable Collectible peek(){
        if(this.items.isEmpty()) return null;
        return this.items.get(items.size() - 1);
    }

    @Override
    public void shift() {
        Collections.rotate(this.items, 1);
    }

    @NotNull
    @Override
    public Iterator<Collectible> iterator() {
        return this.items.iterator();
    }
}
