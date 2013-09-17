import GameEngine.Game.GameDrawer;
import GameEngine.Game.ResourceLoader;


public class TallSpotLight extends SpotLight {

	Enemy parent;
	
	public TallSpotLight(float arg0, float arg1, SurvivalGame game, ResourceLoader loader, String path, Enemy parent) {
		super(arg0, arg1, game);
        addTexture(loader.loadTexture(path), 96/2, 96/2);
        this.parent = parent;	}

	@Override
	public void draw(GameDrawer drawer) {
		// TODO Auto-generated method stub
		drawer.draw(this, 1.0f, 1.0f, 1.0f, 1.0f, 0);

	}

}
