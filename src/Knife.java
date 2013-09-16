import java.util.Timer;
import java.util.TimerTask;

import GameEngine.Game.GameDrawer;
import GameEngine.Game.ResourceLoader;


public class Knife extends Weapon {
	

	private int distanceKnife = 35;

	public Knife(float arg0, float arg1, ResourceLoader loader, String path,
			PlayerObject playerObject) {
		super(arg0, arg1, loader, path, playerObject);
        addTexture(loader.loadTexture(path), 3, 10);
	}

	@Override
	public void draw(GameDrawer drawer) {
		drawer.draw(this, 1.0f, 1.0f, 1.0f, 1.0f, 0);

	}

	@Override
	public void attack() {

		final int prevOffset = mPlayerObject.getOffset();
		mPlayerObject.setOffset(distanceKnife);
		mPlayerObject.setWeapon();
		mPlayerObject.attacking(true);
		new Timer().schedule(new TimerTask(){
			public void run()
			{
				mPlayerObject.setOffset(prevOffset);
				mPlayerObject.setWeapon();
				mPlayerObject.attacking(false);
				
			}
		}, 80);
		
	}

}
