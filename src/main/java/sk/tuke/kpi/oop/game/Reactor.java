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
            if(this.damage > 80){
                this.hotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.03f, Animation.PlayMode.LOOP_PINGPONG);
            }
            else if(this.damage > 60){
                this.hotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.05f, Animation.PlayMode.LOOP_PINGPONG);
            }
            setAnimation(hotAnimation);
        }
        else{
            setAnimation(brokenAnimation);
        }
    }

    public void increaseTemperature(int increment){
        if(!this.isOn) return;
        if(increment < 0) return;
        if(this.damage > 100) return;
        int newIncrement = 0;
        if(this.damage >= 33 && this.damage <= 66){
            newIncrement = (int) Math.round(increment * 1.5);
        }
        if(this.damage > 66){
            newIncrement = increment * 2;
        }
        this.temperature = this.temperature + newIncrement;
        if(this.temperature > 2000){
            float newDamage = (float)(this.temperature - 2000)/40;
            this.damage = (int)Math.floor(newDamage);
            if(this.damage >= 100){
                this.damage = 100;
                this.isOn = false;
                if(this.devices != null) this.devices.forEach(this::updateStateOfDevice);
            }
        }
        updateAnimation();
    }

    public void decreaseTemperature(int decrement){
        if(!this.isOn) return;
        int newDecrement = 0;
        if(decrement < 0) return;
        if(this.damage == 100){
            return;
        }
        else if(this.damage >= 50 && this.damage < 100) {
            newDecrement = decrement / 2;
        }
        this.temperature -= newDecrement;
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
        scene.scheduleAction(new PerpetualReactorHeating(1), this);
        new PerpetualReactorHeating(1).scheduleFor(this);
    }
}

