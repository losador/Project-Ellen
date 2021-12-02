package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Usable;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

import javax.swing.*;

public class Terminal extends AbstractActor implements Usable<Ripley> {

    public static final Topic<Terminal> CORRECT_PASS = Topic.create("correct pass", Terminal.class);
    private String password;
    private boolean isCorrect;
    private boolean calledFirstTime;
    private int attempts = 3;

    public Terminal(String pass){
        this.password = pass;
        this.isCorrect = false;
        this.calledFirstTime = true;
        setAnimation(new Animation("sprites/computer.png", 80, 48, 0.2f, Animation.PlayMode.LOOP_PINGPONG));
    }

    @Override
    public void useWith(Ripley actor) {
        JFrame frame = new JFrame();
        if(this.isCorrect){
            JOptionPane.showMessageDialog(frame,"You have already entered the correct password!");
            return;
        }
        if(this.calledFirstTime){
            JOptionPane.showMessageDialog(frame, "You must enter a password to unlock the door. The password consists of numbers only. Hint: you can find the password at the bottom", "Description", JOptionPane.PLAIN_MESSAGE);
        }
        if(this.attempts == 0){
            JOptionPane.showMessageDialog(frame,"You have run out of attempts! Use card to refill");
            return;
        }
        this.calledFirstTime = false;
        String getMessage = JOptionPane.showInputDialog(frame, "Enter password: " + "You have " + this.attempts + " attempts left!", "Password", JOptionPane.PLAIN_MESSAGE);
        if(getMessage.equals(this.password)){
            JOptionPane.showMessageDialog(frame, "Correct password...Door unlocked", "Correct", JOptionPane.INFORMATION_MESSAGE);
            this.getScene().getMessageBus().publish(CORRECT_PASS, this);
            LockedDoor door = null;
            for(Actor a : this.getScene().getActors()){
                if(a instanceof LockedDoor && a.getName().equals("exit door")){
                    door = (LockedDoor) a;
                }
            }
            assert door != null;
            door.unlock();
            door.close();
            this.isCorrect = true;
        }
        else{
            JOptionPane.showMessageDialog(frame, "Error: incorrect password. Try again", "Incorrect", JOptionPane.ERROR_MESSAGE);
        }
        this.attempts--;
    }

    public void refillAttempts(){
        this.attempts = 3;
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }
}
