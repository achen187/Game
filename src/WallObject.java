

import GameEngine.Game.GameDrawer;
import GameEngine.GameObject;

public class WallObject extends MyGameObject {

    public WallObject(float x, float y, SurvivalGame game, int degree){
    	super(x,y, game);
    	addTexture(game.wallTexture, 16, 2);
    	setRotation(degree);
    	setCollidable(false);
    }

	@Override
	public void draw(GameDrawer drawer) {
		drawer.draw(this, 1.0f, 1.0f, 1.0f, 1.0f, 0);
	}
}
