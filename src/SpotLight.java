import GameEngine.Game.GameDrawer;

//abstract clas represinting all the spotlights of the enemies
public abstract class SpotLight extends MyGameObject {

	public SpotLight(float arg0, float arg1,SurvivalGame game) {
		super(arg0, arg1, game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(GameDrawer drawer) {
		// TODO Auto-generated method stub
		if (game.secondsRemaining > 0)
			drawer.draw(this, 1.0f, 0f, 0f, .50f, 0);			
		else
			drawer.draw(this, 1.0f, 1.0f, 1.0f, .50f, 0);			
	}

}
