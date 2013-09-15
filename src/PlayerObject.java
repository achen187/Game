

import java.awt.geom.Point2D;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;

import GameEngine.Game.GameDrawer;
import GameEngine.Game.ResourceLoader;
import GameEngine.GameObject;

class PlayerObject extends MovingObject

{

	public PlayerObject(float arg0, float arg1, ResourceLoader loader) {
		super(arg0, arg1);
        addTexture(loader.loadTexture("Textures/spaceship_sm.gif"), 16, 16);

	}

	@Override
	public float[] getColor() {
		return new float[]{0f, 1.0f, 0f, 1.0f};
	}

	@Override
	public void draw(GameDrawer drawer) {
		drawer.draw(this, getColor(), 1.0f);
		
	}
  
}
