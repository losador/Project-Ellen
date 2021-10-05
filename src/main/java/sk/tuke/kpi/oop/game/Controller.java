package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Controller extends AbstractActor {
    private Reactor reactor;
    private Animation conImage;

    public Controller(Reactor currentReactor){
        this.conImage = new Animation("sprites/switch.png");
        setAnimation(conImage);
        this.reactor = currentReactor;
    }

    public void toggle(){
        if(this.reactor.isRunning()) this.reactor.turnOff();
        else if(!this.reactor.isRunning()) this.reactor.turnOn();
    }
}
