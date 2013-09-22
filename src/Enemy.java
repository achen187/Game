import GameEngine.GameObject;
import GameEngine.Game.GameDrawer;

//super class of all enemies
public abstract class Enemy extends MovingObject {

	//the spotlight of the enemy
	protected SpotLight spotLight;
	
	
	
	public Enemy(float arg0, float arg1, SurvivalGame game) {
		super(arg0, arg1, game);
	}
	
	public SpotLight getSpotLight()
	{
		return spotLight;
	}
	
	public abstract float offSetFlashlight();
	
	//sets where the spotlight goes relative to the enemy
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
	
	//sets the spotlight as well as itself
	@Override
	public void moveInDirection(float direction, float speed)
	{
		super.moveInDirection(direction,speed);
		spotLight.setPosition(getSpotlightX(), getSpotlightY());
	}
	
	public abstract void chase();
	public abstract void patrol();

	public abstract float patrolSpeed();
	public abstract float chaseSpeeed();
	
	//chase or patrol depending on game state
	@Override
	public void doTimeStep()
	{
		if (SurvivalGame.secondsRemaining > 0)
		{
			chase();
		}else
		{
			patrol();
		}
	}
	
	@Override
	public void setMarkedForDestruction(boolean b)
	{
		super.setMarkedForDestruction(b);
		spotLight.setMarkedForDestruction(b);
	}
	
	
	
	

	
}
