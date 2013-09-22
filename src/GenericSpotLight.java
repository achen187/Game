import GameEngine.Game.GameDrawer;
import GameEngine.Game.ResourceLoader;
import GameEngine.GameObject;

//small spotlight
public class GenericSpotLight extends SpotLight {

	private Enemy parent;
	
	public GenericSpotLight(float arg0, float arg1, SurvivalGame game,
			GenericGuard genericGuard) {
		super(arg0, arg1, game);
        addTexture(game.dropTexture, 16, 16);
        this.parent = parent;	}
	

}
