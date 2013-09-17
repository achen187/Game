

import GameEngine.GameTexture;

public class BulletObject extends PhysicalObject {
    private int destroyTimer = 0;
    
    public BulletObject (float x, float y, float m,SurvivalGame game, int time, GameTexture bt) {
        super (x, y, m, game);
        setDestroyTimer(time);
        addTexture(bt);
    }
    
    public void setDestroyTimer(int time) {
        destroyTimer = time;
    }
    
    public void doTimeStep() {
        destroyTimer--;
        if (destroyTimer == 0)
            setMarkedForDestruction(true);
        
        super.doTimeStep();
    }
}
