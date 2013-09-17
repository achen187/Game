import GameEngine.Game.GameDrawer;
import GameEngine.GameObject;


public abstract class MyGameObject extends GameObject {

	protected SurvivalGame game;
	public MyGameObject(float arg0, float arg1, SurvivalGame game) {
		super(arg0, arg1);
		this.game = game;
	}
	
	public abstract void draw(GameDrawer drawer);
	
	public boolean isBehind(MyGameObject other)
	{
		return true;
	}

}
