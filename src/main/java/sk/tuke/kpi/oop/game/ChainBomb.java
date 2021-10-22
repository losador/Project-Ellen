package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Objects;

public class ChainBomb extends TimeBomb {

    public ChainBomb(float time) {
        super(time);
    }

    private int getX(){
        int x;
        x = this.getPosX() - this.getWidth()/2;
        return x;
    }

    @Override
    public void explode() {
        super.explode();
        Ellipse2D.Float ellipse = new Ellipse2D.Float((this.getPosX() - this.getWidth()/2) - 50, (this.getPosY() - this.getHeight() / 2) - 50, 100, 100);
        List<Actor> actorslist = Objects.requireNonNull(getScene()).getActors();
        for (Actor actor : actorslist) {
            if (actor instanceof ChainBomb && !((ChainBomb) actor).isActivated()) {
                Rectangle2D.Float nextBomb = new Rectangle2D.Float(actor.getPosX() - actor.getWidth() / 2, actor.getPosY() - actor.getHeight() / 2, actor.getWidth(), actor.getHeight());
                if (ellipse.intersects(nextBomb)) ((ChainBomb) actor).activate();
            }
        }
    }
}
