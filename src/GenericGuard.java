import GameEngine.Game.GameDrawer;
import GameEngine.Game.ResourceLoader;


public class GenericGuard extends Enemy {

	
	
	
	public GenericGuard(float arg0, float arg1, ResourceLoader loader) {
		super(arg0, arg1);
        addTexture(loader.loadTexture("Textures/spaceship_sm.gif"), 16, 16);
        spotLight = new SpotLight(arg0, arg1, loader, "Textures/flashlight.png", this);
        SurvivalGame.objects.add(spotLight);
	}

	@Override
	public void patrol() {
		System.out.println(getDirection());
		moveInDirection(getDirection(), .5f);
	}
	

	@Override
	public float[] getColor() {
		return new float[]{1.0f, 0f, 0f, 1.0f};
	}

	@Override
	public float offSetFlashlight() {
		return 32;
	}

	@Override
	public void chase() {
		int dx = SurvivalGame.lastSeenGridX - getGridX();
		int dy = SurvivalGame.lastSeenGridY - getGridY();
		if (Math.abs(dx) == 0 && Math.abs(dy) == 0)
		{
			patrol();
			return;
		}
		if (Math.abs(dx) > Math.abs(dy))
		{
			if (dx > 0)
			{
				moveInDirection(90f, 1.5f);
			}
			else
			{
				moveInDirection(270f, 1.5f);
			}
		}
		else
		{
			if (dy > 0)
			{
				moveInDirection(180f, 1.5f);

			}
			else
			{
				moveInDirection(0f, 1.5f);

			}
		}
	}

	

}
