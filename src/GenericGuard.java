import GameEngine.Game.GameDrawer;
import GameEngine.Game.ResourceLoader;

//generic guard, with normal spotlight and speed
public class GenericGuard extends Enemy {

	private float patrolSpeed = .5f;
	private float chaseSpeed = 1.5f;

	
	public GenericGuard(float arg0, float arg1,SurvivalGame game) {
		super(arg0, arg1, game);
        addTexture(game.shipTexture, 16, 16);
        spotLight = new GenericSpotLight(arg0, arg1, game, this);
        SurvivalGame.objects.add(spotLight);
	}

	//move in current direction if possible or pick a random new one
	@Override
	public void patrol() {
		if (game.canMoveInDirection(this, getDirection()))	
			moveInDirection(getDirection(), patrolSpeed);
		else
		{
			int prevDir = (int) getDirection();
			int direction =  ((int) (Math.random() * 4))*90;
			while (game.canMoveInDirection(this, direction) == false)
			{
				direction =  ((int) (Math.random() * 4))*90;
			}
			moveInDirection(direction, patrolSpeed);
		}	}
	

	@Override
	public float[] getColor() {
		return new float[]{1.0f, 0f, 0f, 1.0f};
	}

	//where the flashlight goes relative to the guard
	@Override
	public float offSetFlashlight() {
		return 32;
	}

	//travel in the y and x direction towards the users last known sighting
	//gets stuck on corners, not very smart
	@Override
	public void chase() {
		int dx = SurvivalGame.lastSeenGridX - getGridX();
		int dy = SurvivalGame.lastSeenGridY - getGridY();
		if (Math.abs(dx) == 0 && Math.abs(dy) == 0)
		{
			return;
		}
		
		float desiredX;
		float desiredY;
		
		if (dx > 0)
			desiredX = 90f;
		else
			desiredX = 270f;
		
		if (dy>0)
			desiredY = 180f;
		else
			desiredY = 0f;
		
		if (Math.abs(dx) > Math.abs(dy))
		{
			if (game.canMoveInDirection(this,desiredX))
				moveInDirection(desiredX, chaseSpeed);
			else
				moveInDirection(desiredY, chaseSpeed);
		}
		else
		{
			if (game.canMoveInDirection(this,desiredY))
				moveInDirection(desiredY, chaseSpeed);
			else
				moveInDirection(desiredX, chaseSpeed);
		}
	}

	@Override
	public float patrolSpeed() {
		// TODO Auto-generated method stub
		return .5f;
	}

	@Override
	public float chaseSpeeed() {
		// TODO Auto-generated method stub
		return 1.5f;
	}

	

}
