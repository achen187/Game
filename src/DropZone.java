import GameEngine.Game.GameDrawer;
import GameEngine.Game.ResourceLoader;


//object representing where a new enemy is gonig to be placed, giving the player a headsup
public class DropZone extends MyGameObject {

	public DropZone(float arg0, float arg1,SurvivalGame game) {
		super(arg0, arg1, game);
        addTexture(game.dropTexture, 16, 16);
	}

	@Override
	public void draw(GameDrawer drawer) {
		drawer.draw(this, 1.0f, 0f, 0f, 1.0f, 0);

	}

}
