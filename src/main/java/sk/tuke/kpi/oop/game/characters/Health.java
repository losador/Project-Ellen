package sk.tuke.kpi.oop.game.characters;

import java.util.ArrayList;
import java.util.List;

public class Health {

    private int maxHealth;
    private int currentHealth;
    private List<ExhaustionEffect> effects;

    public Health(int current, int max) {
        this.currentHealth = current;
        this.maxHealth = max;
        this.effects = new ArrayList<>();
    }

    public Health(int current) {
        this.currentHealth = current;
        this.maxHealth = current;
        this.effects = new ArrayList<>();
    }

    public int getValue() {
        return this.currentHealth;
    }

    public void refill(int amount) {
        this.currentHealth += amount;
        if(this.currentHealth > this.maxHealth) this.currentHealth = this.maxHealth;
    }

    public void restore(){
        this.currentHealth = this.maxHealth;
    }

    public void drain(int amount){
        this.currentHealth -= amount;
        if(this.currentHealth < 0) this.exhaust();
    }

    public void exhaust(){
        this.currentHealth = 0;
        if(this.effects != null) effects.forEach(ExhaustionEffect::apply);
    }

    public void onExhaustion(ExhaustionEffect effect){
        if(this.effects != null) this.effects.add(effect);
    }

    @FunctionalInterface
    public interface ExhaustionEffect {
        void apply();
    }
}
