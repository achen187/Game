import GameEngine.Game.GameDrawer;
import GameEngine.Game.ResourceLoader;
import GameEngine.GameTexture;


public class Gun extends Weapon {

	GameTexture bulletTexture;
	
	public Gun(float arg0, float arg1, SurvivalGame game,ResourceLoader loader,
			PlayerObject playerObject) {
		super(arg0, arg1, game,loader, playerObject);
        addTexture(loader.loadTexture("Textures/Gun03.gif"), 3, 13);
        bulletTexture = loader.loadTexture("Textures/bullet.png");

	}

	@Override
	public void attack() {
		super.attack();
		fireBullet();
	}

    public void fireBullet()
    {
//        cooldownTimer = cooldown;
        
        float dir = mPlayerObject.direction;
        BulletObject bullet =
                new BulletObject(
                         mPlayerObject.getPosition().x + (float)Math.sin(Math.toRadians(dir))*32, mPlayerObject.getPosition().y - (float)Math.cos(Math.toRadians(dir))*32, 1f, game, 300, bulletTexture);
        
        bullet.applyForceInDirection(dir, 6f);
        
        SurvivalGame.objects.add(bullet);
        
        game.alert(mPlayerObject);
    }
	@Override
	public void draw(GameDrawer drawer) {
		drawer.draw(this, 1.0f, 1.0f, 1.0f, 1.0f, 0);

	}

}
