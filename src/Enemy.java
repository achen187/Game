import GameEngine.GameObject;
import GameEngine.Game.GameDrawer;


public abstract class Enemy extends MovingObject {

	protected SpotLight spotLight;
	
	public Enemy(float arg0, float arg1) {
		super(arg0, arg1);
	}
	
	public SpotLight getSpotLight()
	{
		return spotLight;
	}
	
	public abstract float offSetFlashlight();
	
	private float getSpotlightX()
	{
		if (getDirection() == 180 || getDirection() == 0)
			return getPosition().x;
		else if (getDirection() == 90 )
			return getPosition().x + offSetFlashlight();
		else
			return getPosition().x - offSetFlashlight();
			
			
	}
	
	private float getSpotlightY()
	{
		if (getDirection() == 90 || getDirection() == 270)
			return getPosition().y;
		else if (getDirection() == 0 )
			return getPosition().y - offSetFlashlight();
		else
			return getPosition().y + offSetFlashlight();
			
			
	}
	
	@Override
	public void draw(GameDrawer drawer) {
		drawer.draw(this, getColor(), 1.0f);
	}
	
	@Override
	public void moveInDirection(float direction, int speed)
	{
		super.moveInDirection(direction,speed);
		spotLight.setPosition(getSpotlightX(), getSpotlightY());
	}
	
	public abstract void chase();
	public abstract void patrol();

	
	@Override
	public void doTimeStep()
	{
		if (SurvivalGame.secondsRemaining > 0)
			chase();
		else
			patrol();
	}

	
}
