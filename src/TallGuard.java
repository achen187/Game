import GameEngine.Game.ResourceLoader;

//tall guard, is veyr slow but has a huge spotlight same chase and patrol as generic guard
public class TallGuard extends Enemy {

	
	private float patrolSpeed = .25f;
	private float chaseSpeed = 1f;
	
	public TallGuard(float arg0, float arg1,SurvivalGame game) {
		super(arg0, arg1, game);
        addTexture(game.shipTexture, 16, 16);
        spotLight = new TallSpotLight(arg0, arg1, game, this);
        SurvivalGame.objects.add(spotLight);
	}

	@Override
	public float offSetFlashlight() {
		return spotLight.getCurrentTexture().getHeight()/2;
	}

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
		}
			

	}

	@Override
	public float[] getColor() {
		return new float[]{1.0f, .5f, 0f, 1.0f};
	}

	@Override
	public float patrolSpeed() {
		return patrolSpeed;
	}

	@Override
	public float chaseSpeeed() {
		return chaseSpeed;
	}

	
}
