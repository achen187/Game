import GameEngine.Game.GameDrawer;
import GameEngine.GameObject;


public abstract class MyGameObject extends GameObject {

	public MyGameObject(float arg0, float arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
	
	public abstract void draw(GameDrawer drawer);
	
	public boolean isBehind(MyGameObject other)
	{
		return true;
	}

}
