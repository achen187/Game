import GameEngine.Game.GameDrawer;
import GameEngine.GameObject;

//my game object
public abstract class MyGameObject extends GameObject {

	protected SurvivalGame game;
	public MyGameObject(float arg0, float arg1, SurvivalGame game) {
		super(arg0, arg1);
		this.game = game;
	}
	
	public abstract void draw(GameDrawer drawer);
	
	

}
