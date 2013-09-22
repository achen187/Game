import GameEngine.Game.GameDrawer;
import GameEngine.Game.ResourceLoader;


public class TallSpotLight extends SpotLight {

	Enemy parent;
	
	

	public TallSpotLight(float arg0, float arg1, SurvivalGame game,
			TallGuard tallGuard) {
		super(arg0, arg1, game);
        addTexture(game.largeFlash, 96/2, 96/2);
        this.parent = parent;	}

	

}
