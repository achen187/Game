import java.util.Timer;
import java.util.TimerTask;

import GameEngine.Game.GameDrawer;
import GameEngine.Game.ResourceLoader;

//abstract calss representing all weapons
public abstract class Weapon extends MyGameObject {

	PlayerObject mPlayerObject;
	
	
	public Weapon(float arg0, float arg1, SurvivalGame game, ResourceLoader loader,
			PlayerObject playerObject) {
		super (arg0, arg1, game);
		mPlayerObject = playerObject;

	}

	//better version of cooldown, sets a timer that sets whether the player is attacking already
	public void attack()
	{
		mPlayerObject.attacking(true);
		new Timer().schedule(new TimerTask(){
			public void run()
			{
				finishAttacking();
			}
		}, 80);
		
	}
	public void finishAttacking()
	{
		mPlayerObject.attacking(false);
	}

}
