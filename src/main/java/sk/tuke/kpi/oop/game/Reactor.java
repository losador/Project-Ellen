package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;

import java.util.HashSet;
import java.util.Set;

public class Reactor extends AbstractActor implements Switchable, Repairable{
    private int temperature;
    private int damage;
    private boolean isOn;
    private Animation offAnimation;
    private Animation normalAnimation;
    private Animation hotAnimation;
    private Animation brokenAnimation;
    private Animation extinguishAnimation;
    private Set<EnergyConsumer> devices;

    public Reactor(){
        this.temperature = 0;
        this.damage = 0;
        this.isOn = false;
        this.normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.hotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.brokenAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.offAnimation = new Animation("sprites/reactor.png");
        this.extinguishAnimation = new Animation("sprites/reactor_extinguished.png", 80, 80, 0.5f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(offAnimation);
        this.devices = new HashSet<>();
    }

    public int getTemperature(){
        return this.temperature;
    }

    public int getDamage(){
        return this.damage;
    }

    public void updateAnimation(){
        if(this.temperature < 4000){
            setAnimation(normalAnimation);
        }
        else if(this.temperature < 6000){
            setAnimation(hotAnimation);
        }
        else{
            setAnimation(brokenAnimation);
        }
    }

    public void increaseTemperature(int increment){
        if(increment < 0 || !this.isOn) return;
        int newIncrement = increment;
        if(this.damage >= 33 && this.damage <= 66){
            newIncrement = (int)Math.round(increment * 1.5);
        }
        else if(this.damage > 66){
            newIncrement *= 2;
        }
        this.temperature += newIncrement;
        if(this.temperature > 2000 && this.damage < 100){
            this.damage = (this.temperature - 2000)/40;
            if(this.damage > 100){
                this.damage = 100;
                this.turnOff();
                this.devices.forEach(this::updateStateOfDevice);
            }
        }
        updateAnimation();
    }

    public void decreaseTemperature(int decrement){
        if(!this.isOn) return;
        int newDecrement = decrement;
        if(decrement < 0) return;
        if(this.damage == 100){
            return;
        }
        if(this.damage >= 50) newDecrement = Math.round(newDecrement / 2);
        this.temperature -= newDecrement;
        if(this.temperature < 0) this.temperature = 0;
        updateAnimation();
    }

    public boolean repair(){
        if(this.damage > 0 && this.damage < 100) {
            int newDamage;
            this.damage -= 50;
            newDamage = this.damage;
            if (this.damage < 0) this.damage = 0;
            this.temperature = (40 * newDamage) + 2000;
            updateAnimation();
            return true;
        }
        return false;
    }

    private void updateStateOfDevice(EnergyConsumer device){
        if(device == null) return;
        device.setPowered(isOn());
    }

    public void turnOn(){
        if(this.damage >= 100) return;
        this.isOn = true;
        updateAnimation();
        if(this.devices != null) this.devices.forEach(this::updateStateOfDevice);
    }

    public void turnOff(){
        this.isOn = false;
        setAnimation(offAnimation);
        if(this.devices != null) this.devices.forEach(this::updateStateOfDevice);
    }

    public boolean isOn(){
        return this.isOn;
    }

    public void addDevice(EnergyConsumer device){
        this.devices.add(device);
        device.setPowered(this.isOn);
    }

    public void removeDevice(EnergyConsumer device){
        device.setPowered(false);
        this.devices.remove(device);
    }

    public boolean extinguish() {
        if (this.damage >= 100){
            this.temperature -= 4000;
            if (this.temperature < 0) this.temperature = 0;
            setAnimation(extinguishAnimation);
            return true;
        }
        return false;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new PerpetualReactorHeating(1).scheduleFor(this);
    }
}

