

import java.awt.geom.Point2D;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;

import GameEngine.Game.GameDrawer;
import GameEngine.Game.ResourceLoader;
import GameEngine.GameObject;

class PlayerObject extends MovingObject

{
	private Weapon curWeapon;
	private int offsetWeapon = 10;
	private boolean attacking = false;
	

	public PlayerObject(float arg0, float arg1, ResourceLoader loader) {
		super(arg0, arg1);
        addTexture(loader.loadTexture("Textures/spaceship_sm.gif"), 16, 16);
        curWeapon = new Knife(arg0, arg1, loader, "Textures/Gun01.gif", this);
        SurvivalGame.objects.add(curWeapon);
        curWeapon.setPosition(getWeaponX(), getWeaponY());
		curWeapon.setRotation(getDirection());

	}

	@Override
	public float[] getColor() {
		return new float[]{0f, 1.0f, 0f, 1.0f};
	}

	@Override
	public void draw(GameDrawer drawer) {
		drawer.draw(this, getColor(), 1.0f);
		
	}

	public void attack() {
		if (!attacking)
			curWeapon.attack();
	}
	
	public float getWeaponX()
	{
		if (getDirection() == 180 || getDirection() == 0)
			return getPosition().x;
		else if (getDirection() == 90 )
			return getPosition().x + getOffset();
		else
			return getPosition().x - getOffset();
			
			
	}
	
	public float getWeaponY()
	{
		if (getDirection() == 90 || getDirection() == 270)
			return getPosition().y;
		else if (getDirection() == 0 )
			return getPosition().y - getOffset();
		else
			return getPosition().y + getOffset();
			
	}
	
	
	
	@Override
	public void moveInDirection(float direction, float speed)
	{
		super.moveInDirection(direction,speed);
		setWeapon();
	}
	
	public int getOffset()
	{
		return offsetWeapon;
	}

	public void setOffset(int offset)
	{
		offsetWeapon = offset;
	}

	public void setWeapon() {
		curWeapon.setPosition(getWeaponX(), getWeaponY());
		curWeapon.setRotation(getDirection());		
	}

	public void attacking(boolean b) {
		attacking = b;
	}

	public boolean isAttacking() {
		// TODO Auto-generated method stub
		return attacking;
	}
	
  
}
