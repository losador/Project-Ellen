package sk.tuke.kpi.oop.game.weapons;

public class Gun extends Firearm{

    public Gun(int current, int max) {
        super(current, max);
    }

    @Override
    protected Fireable createBullet() {
        return new Bullet();
    }

}
