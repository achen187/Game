

import GameEngine.Game.GameDrawer;
import GameEngine.GameObject;

//wallObject for the maze
public class WallObject extends MyGameObject {

    public WallObject(float x, float y, SurvivalGame game, int degree){
    	super(x,y, game);
    	if (degree == 90 || degree == 270)
    	{
    		addTexture(game.verticalWallTexture, 2, 16);
    	}
    	else
    	{
    		addTexture(game.wallTexture, 16, 2);
    	}
    }

	@Override
	public void draw(GameDrawer drawer) {
		drawer.draw(this, 1.0f, 1.0f, 1.0f, 1.0f, 0);
	}
}
