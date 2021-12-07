package sk.tuke.kpi.oop.game.weapons;

public class TurretGun extends Firearm{

    public TurretGun(int ammo){
        super(ammo, ammo);
    }

    @Override
    protected Fireable createBullet() {
        return new Rocket();
    }
}
