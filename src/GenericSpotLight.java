import GameEngine.Game.GameDrawer;
import GameEngine.Game.ResourceLoader;
import GameEngine.GameObject;


public class GenericSpotLight extends SpotLight {

	private Enemy parent;
	
	public GenericSpotLight(float arg0, float arg1,SurvivalGame game, ResourceLoader loader, String path, Enemy parent) {
		super(arg0, arg1, game);
        addTexture(loader.loadTexture(path), 16, 16);
        this.parent = parent;
	
	}
	@Override
	public void draw(GameDrawer drawer) {
		drawer.draw(this, 1.0f, 1.0f, 1.0f, 1.0f, 0);
	}

}
