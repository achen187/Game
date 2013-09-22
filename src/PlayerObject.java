

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
	private int curWeaponNumber = 1;
	private Knife knife;
	private Gun gun;
	
	public PlayerObject(float arg0, float arg1, SurvivalGame game, ResourceLoader loader) {
		super(arg0, arg1, game);
        addTexture(loader.loadTexture("Textures/spaceship_sm.gif"), 16, 16);
        knife = new Knife(arg0, arg1, game, loader, this);
        gun = new Gun(arg0, arg1, game, loader,this);
        curWeapon = knife;
        SurvivalGame.objects.add(curWeapon);
        curWeapon.setPosition(getWeaponX(), getWeaponY());
		curWeapon.setRotation(getDirection());

	}

	@Override
	public float[] getColor() {
		return new float[]{0f, 1.0f, 0f, 1.0f};
	}

	//changes the weapon
	public void changeWeapon(int weapon)
	{
		if (weapon == 1 && curWeaponNumber != 1)
		{
			SurvivalGame.objects.remove(curWeapon);
	        curWeapon = knife;
	        curWeapon.setPosition(getWeaponX(), getWeaponY());
			curWeapon.setRotation(getDirection());
			SurvivalGame.objects.add(curWeapon);
			curWeaponNumber = weapon;

		}
		else if (weapon == 2 && curWeaponNumber != 2)
		{
			SurvivalGame.objects.remove(curWeapon);
	        curWeapon = gun;
	        curWeapon.setPosition(getWeaponX(), getWeaponY());
			curWeapon.setRotation(getDirection());
			SurvivalGame.objects.add(curWeapon);
			curWeaponNumber = weapon;

		}
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

	//repositions weapon
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
