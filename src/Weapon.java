import GameEngine.Game.GameDrawer;
import GameEngine.Game.ResourceLoader;


public abstract class Weapon extends MyGameObject {

	PlayerObject mPlayerObject;
	
	
	public Weapon(float arg0, float arg1, ResourceLoader loader, String path,
			PlayerObject playerObject) {
		super (arg0, arg1);
		mPlayerObject = playerObject;

		// TODO Auto-generated constructor stub
	}

	public abstract void attack();

}
