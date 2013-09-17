import GameEngine.Game.ResourceLoader;


public class TallGuard extends Enemy {

	
	private float patrolSpeed = .25f;
	private float chaseSpeed = 1f;
	
	public TallGuard(float arg0, float arg1,SurvivalGame game, ResourceLoader loader) {
		super(arg0, arg1, game);
        addTexture(loader.loadTexture("Textures/spaceship_sm.gif"), 16, 16);
        spotLight = new TallSpotLight(arg0, arg1, game, loader, "Textures/largeFlashlight.png", this);
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
		if (Math.abs(dx) > Math.abs(dy))
		{
			if (dx > 0)
			{
				moveInDirection(90f, chaseSpeed);
			}
			else
			{
				moveInDirection(270f, chaseSpeed);
			}
		}
		else
		{
			if (dy > 0)
			{
				moveInDirection(180f, chaseSpeed);

			}
			else
			{
				moveInDirection(0f,chaseSpeed);

			}
		}
	}

	@Override
	public void patrol() {
		moveInDirection(getDirection(), patrolSpeed);

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
