import GameEngine.Game.GameDrawer;
import GameEngine.Game.ResourceLoader;

//the game object you must reach!
public class EndPoint extends MyGameObject {

	public EndPoint(float arg0, float arg1,SurvivalGame game, ResourceLoader loader, String path) {
		super(arg0, arg1, game);
        addTexture(loader.loadTexture(path), 16, 16);
	}

	@Override
	public void draw(GameDrawer drawer) {
		drawer.draw(this, 1.0f, 1.0f, 1.0f, 1.0f, 0);

	}

}
