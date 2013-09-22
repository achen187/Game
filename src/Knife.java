import java.util.Timer;
import java.util.TimerTask;

import GameEngine.Game.GameDrawer;
import GameEngine.Game.ResourceLoader;

//backstabbing knife
public class Knife extends Weapon {
	

	private int distanceKnife = 40;
	private int prevOffset;

	public Knife(float arg0, float arg1,SurvivalGame game, ResourceLoader loader,
			PlayerObject playerObject) {
		super(arg0, arg1, game, loader, playerObject);
        addTexture(loader.loadTexture("Textures/Gun01.gif"), 3, 10);
	}

	@Override
	public void draw(GameDrawer drawer) {
		drawer.draw(this, 1.0f, 1.0f, 1.0f, 1.0f, 0);

	}

	//changes position of knife
	@Override
	public void attack() {
		super.attack();
		prevOffset = mPlayerObject.getOffset();
		mPlayerObject.setOffset(distanceKnife);
		mPlayerObject.setWeapon();
				
		
	}
	//put knife back in right spot
	@Override
	public void finishAttacking()
	{
		super.finishAttacking();
		mPlayerObject.setOffset(prevOffset);
		mPlayerObject.setWeapon();
	}
}
