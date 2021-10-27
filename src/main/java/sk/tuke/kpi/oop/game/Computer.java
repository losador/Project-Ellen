package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Computer extends AbstractActor implements EnergyConsumer{

    private boolean isPowered;
    private Animation computerAnimation;

    public Computer(){
        this.computerAnimation = new Animation("sprites/computer.png", 80, 48, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(computerAnimation);
    }

    public int add(int a, int b){
        if(!this.isPowered) return 0;
        return a + b;
    }
    public float add(float a, float b){
        if(!this.isPowered) return 0;
        return a + b;
    }

    public int sub(int a, int b){
        if(!this.isPowered) return 0;
        return a * b;
    }
    public float sub(float a, float b){
        if(!this.isPowered) return 0;
        return a * b;
    }

    @Override
    public void setPowered(boolean power) {
        this.isPowered = power;
    }
}
