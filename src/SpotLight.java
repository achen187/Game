import GameEngine.Game.GameDrawer;
import GameEngine.Game.ResourceLoader;
import GameEngine.GameObject;


public class SpotLight extends MyGameObject {

	private Enemy parent;
	
	public SpotLight(float arg0, float arg1, ResourceLoader loader, String path, Enemy parent) {
		super(arg0, arg1);
        addTexture(loader.loadTexture(path), 16, 16);
        this.parent = parent;
	
	}
	@Override
	public void draw(GameDrawer drawer) {
		drawer.draw(this, 1.0f, 1.0f, 1.0f, 1.0f, 0);
	}

}
