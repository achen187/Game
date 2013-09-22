

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.*;

import GameEngine.Game;
import GameEngine.GameTexture;
import GameEngine.GameFont;
import GameEngine.GameObject;



//==================================================================================================
//==================================================================================================

public class SurvivalGame extends Game
{
	// Offset of the screen
    private Point2D.Float offset = new Point2D.Float(0,0);
    
    private boolean alive = true;
    
    // A Collection of GameObjects in the world that will be used with the collision detection system
    public static Vector<GameObject> objects = new Vector<GameObject>();
    
    // Grid GameObjects, only used to draw floor
    private GameObject [] [] gridTile;
    
    //tiles for the maze
    public static int numRows = 24;
    public static int numCols = 24;
    private Tile[][] mazeTile;
    
    
    // Important GameObjects
    private PlayerObject player; // the player
    
    //Textures that will be used
    private GameTexture bulletTexture;
    public GameTexture wallTexture;
    public GameTexture verticalWallTexture;
    public GameTexture dropTexture;
    public GameTexture largeFlash;

    public GameTexture shipTexture;


    
    //GameFonts that will be used
    private GameFont arial, serif;
    
    // The positin of the mouse
    private Point2D.Float mousePos = new Point2D.Float (0,0);
    
    // a counter for how far the mousewheel has been moved (just an example)
    private int mouseWheelTick = 0;
    
    // Information for the random line at the bottom of the screen
    Point2D.Float [] linePositions = {new Point2D.Float(0,0), new Point2D.Float(100,100)};
    float [][] lineColours = {{1.0f,1.0f,1.0f,1.0f},{1.0f,0.0f,0.0f,1.0f}};
    
    // Variable to keep track of the background / world size
    private Point2D.Float worldSize;
    
    //how long before the chase mode expires
    public static int secondsRemaining;
    //where the user was last spotted
    public static int lastSeenGridX;
    public static int lastSeenGridY;
    
    //the height and width of each tile
    public static int heightTile;
    public static int widthTile;

    //how long to chase
    private int CHASE_TIME = 10;
    
    private Timer chaseTimer;
    private Timer generationTimer;
    private Timer timeLeft;
    
    //time given to user to finish maze
    private int secondsLeft = 180;
    private boolean gameWon = false;
    
    private int score = 0;
    
    private ArrayList<WallObject> walls = new ArrayList<WallObject> ();
    // Name your audio cues here and set the paths the files are located!!
    // Make sure the enum and paths match!
 	enum AudioFiles
 	{
 		// ***************************************
 		// Add items and define their index here!!
 		// Index must increment else you will be screwed
 		// ***************************************
 			AudioGun(0),
 			Laser(1),
 			Explosion(2);
 		// ***************************************
 		
 		
 		// DO NOT MODIFY THIS!!
 	    private final int index; 
 		AudioFiles(int index)
 		{
 	        this.index = index;
 	    }
 	    public int index()
 	    { 
 	        return(index); 
 	    }
 	}
 	private String[] AudioPaths =
 	{
 		"Audio/gun.wav",
 		"Audio/laser.wav",
 		"Audio/explosion.wav"
 	};
    
    //==================================================================================================
    
    public SurvivalGame (int GFPS)
    {
        super(GFPS);
        
        
    }
    


    //==================================================================================================
    
    public void initStep(final ResourceLoader loader)
    {
    	// Add the Audio paths
		for (int i = 0; i < AudioPaths.length; i++)
			gameAudio.AddNewAudioFile(AudioPaths[i]);
		// Initialise the audio engine
		gameAudio.Initialise();
		
        // Loading up some fonts
        arial = loader.loadFont(new Font("Arial", Font.ITALIC, 48));
        serif = loader.loadFont(new Font("Serif", Font.PLAIN, 48));
        
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        
        // Loading up our textures
        GameTexture softRockTexture = loader.loadTexture("Textures/asteroid.png");
        GameTexture rockTexture = loader.loadTexture("Textures/soft_rock.png");
        bulletTexture = loader.loadTexture("Textures/bullet.png");
        wallTexture = loader.loadTexture("Textures/wall.png" );
        verticalWallTexture = loader.loadTexture("Textures/wallvertical.png" );
        dropTexture = loader.loadTexture("Textures/flashlight.png" );
        largeFlash = loader.loadTexture("Textures/largeFlashlight.png" );

        shipTexture = loader.loadTexture("Textures/spaceship_sm.gif" );

     
        
     // Setup the world grid system
        GameTexture floorTexture = loader.loadTexture("Textures/floor_steel.jpg");

        int numTiles = 6;
        // Set the world size! This is to prevent the camera going over the edge of the background
        // For tiles, use the above. If using a single image, just read the textures size!!
        worldSize = new Point2D.Float(numTiles * floorTexture.getWidth(), numTiles * floorTexture.getHeight());
        heightTile = (int) (worldSize.y / numRows);
        widthTile = (int) (worldSize.x/ numCols);
        
        //generates and displays maze
        mazeTile = new Tile[numRows][numCols];
        MazeGenerator.generateMaze(mazeTile, this);
        displayMaze(mazeTile);
        
        //loads 4 generic guards and 4 tall guards
        for (int i = 0 ; i <4 ; i++)
        {
        	int gridX = (int) (Math.random()*(numCols-4)+4);
      		int gridY = (int) (Math.random() *(numRows-4)+4);
          
			GenericGuard go = new GenericGuard(mazeTile[gridY][gridX].getMidX(), mazeTile[gridY][gridX].getMidY(), this);
			
			float direction =((int) (Math.random()*4))*90;
			go.setDirection(direction);
			objects.add(go);
        }
        
        for (int i = 0 ; i <4 ; i++)
        {
        	int gridX = (int) (Math.random()*(numCols-4)+4);
      		int gridY = (int) (Math.random() *(numRows-4)+4);
          
			TallGuard go = new TallGuard(mazeTile[gridY][gridX].getMidX(), mazeTile[gridY][gridX].getMidY(), this);
			
			float direction =((int) (Math.random()*4))*90;
			go.setDirection(direction);
			objects.add(go);
        }
        
        
        
        // creating the floor objects
        gridTile = new GameObject[numTiles][numTiles];
        for (int i = 0 ; i < numTiles ; i++)
        {
        	for (int j = 0 ; j < numTiles ; j++)
        	{
        		gridTile[i][j] = new GameObject(floorTexture.getWidth() * i, floorTexture.getHeight() * j);
        		gridTile[i][j].addTexture(floorTexture, 0, 0);
            }
        }
        
        
        
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        
        // Creating the player's ship
        player = new PlayerObject(
                             mazeTile[0][0].getMidX(),
                             mazeTile[0][0].getMidY(), this, loader);
        
        objects.add(player);
        
        //sets the finish point
        objects.add(new EndPoint(mazeTile[23][23].getMidX(), mazeTile[23][23].getMidY(), this, loader, "Textures/flashlight.png"));
        
        //start the game timer
        timeLeft = new Timer();
        timeLeft.scheduleAtFixedRate(new TimerTask(){
        	public void run()
        	{
        		secondsLeft -= 1;
        		if (secondsLeft <= 0)
        		{
        			alive = false;
        			timeLeft.cancel();
        		}
        	}
        }
        , 1000, 1000);
        
        //loads new enemies every 20 seconds
        //first shows a drop zone to warn user
        generationTimer = new Timer();
        generationTimer.scheduleAtFixedRate(new TimerTask(){
        	public void run()
        	{
        		int gridRow = (int) (Math.random()*numRows);
        		int gridCol = (int) (Math.random()*numCols);
        		Tile curTile = mazeTile[gridRow][gridCol];
        		DropZone drop = new DropZone(curTile.getMidX(), curTile.getMidY(), SurvivalGame.this );
        		objects.add(drop);
        		try {
					Thread.sleep(4000);
					objects.remove(drop);
					GenericGuard go = new GenericGuard((float)curTile.getMidX(), (float)curTile.getMidY(), 
							SurvivalGame.this);
					objects.add(go);
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        	}
        	
        }, 10000, 20000);
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    }

    
    //given obj, returns whether an obj can move in directiona ngle
    public boolean canMoveInDirection(MovingObject obj, float angle)
    {
    	int gridX = obj.getGridX();
    	int gridY = obj.getGridY();
    	float objX = obj.getPosition().x;
    	float objY = obj.getPosition().y;
    	Tile curTile = mazeTile[gridY][gridX];
    	int midX = curTile.getMidX();
    	int midY = curTile.getMidY();

    	if (angle == 180 && ((curTile.hasWall(0) && objY >= midY)
    			|| Math.abs(objX- midX) > 10))
    		return false;
    	if (angle == 90 && ((curTile.hasWall(1)&& objX >= midX) || Math.abs(objY- midY) > 10))
    		return false; 
    	if (angle == 0 && ((curTile.hasWall(2)&& objY <= midY) || Math.abs(objX- midX) > 10))
    		return false;
    	if (angle == 270 && ((curTile.hasWall(3)&& objX <= midX) || Math.abs(objY- midY) > 10))
    		return false; 
    	
    	return true;
    }
    
   

    
    
    //displays the maze 
    private void displayMaze(Tile[][] mazeTile) {

		for (int i = 0; i < numRows; i++)
		{
			for (int j = 0; j < numCols; j++)
			{
			Tile curTile = mazeTile[i][j];
				if (j == 0)
					walls.add(curTile.westWall());
				if (i == 0)
					walls.add(curTile.southWall());
				if (j == numCols -1)
					walls.add(curTile.eastWall());
				if (i == numRows-1)
					walls.add(curTile.northWall());
				
				if (curTile.hasWall(0))
					walls.add(curTile.northWall());
				
				if(curTile.hasWall(1))
					walls.add(curTile.eastWall());
			}
		}
	}

	public static boolean isPointInBox(final Point2D.Float point, final Rectangle2D.Float box)
    {
        return box.contains(point.x, point.y);
    }
    
    // This is a pretty bad implementation and faster ones exist, it is suggested you find a better one. At least try make use of the Rectangle2D's createIntersection method.
    public static boolean boxIntersectBox (final Rectangle2D.Float d, final Rectangle2D.Float d2)
    {
        return  isPointInBox(new Point2D.Float (d.x, d.y), d2) ||
                isPointInBox(new Point2D.Float (d.x, d.y+d.height), d2) ||
                isPointInBox(new Point2D.Float (d.x+d.width, d.y), d2) ||
                isPointInBox(new Point2D.Float (d.x+d.width, d.y+d.height), d2) ||
                isPointInBox(new Point2D.Float (d2.x, d2.y), d) ||
                isPointInBox(new Point2D.Float (d2.x, d2.y+d2.height), d) ||
                isPointInBox(new Point2D.Float (d2.x+d2.width, d2.y), d) ||
                isPointInBox(new Point2D.Float (d2.x+d2.width, d2.y+d2.height), d);
    }
    
    private void handleControls(GameInputInterface gii)
    {
        //----------------------------------
    	
    	// This isn't so great, there are better and neater ways to do this, you are encouraged to implement a better one
        boolean move = false;
        float directionToMove = 0;
        
        if(gii.keyDown(KeyEvent.VK_W))
        {
        	move = true;
        	directionToMove = 180;

        }
        else if(gii.keyDown(KeyEvent.VK_S))
        {
        	move = true;
            
        	directionToMove = 0;

        }
        else if(gii.keyDown(KeyEvent.VK_A) && !gii.keyDown(KeyEvent.VK_D))
        {
        	move = true;
        	directionToMove = 270;
        	

        }
        else if(gii.keyDown(KeyEvent.VK_D) && !gii.keyDown(KeyEvent.VK_A))
        {
        	move = true;
        	directionToMove = 90;
        	

        }
        if (move)
        	player.moveInDirection(directionToMove,2 );
        
        if (gii.keyDown(KeyEvent.VK_1))
        	player.changeWeapon(1);
        if (gii.keyDown(KeyEvent.VK_2))
        	player.changeWeapon(2);



        if(gii.keyDown(KeyEvent.VK_SPACE) )
        {
        	player.attack();

        }
        
    }

    //==================================================================================================
    
    public void logicStep(GameInputInterface gii)
    {
        // some examples of the mouse interface
        mouseWheelTick += gii.mouseWheelRotation();
        mousePos.x = (float)gii.mouseXScreenPosition() - offset.x;
        mousePos.y = (float)gii.mouseYScreenPosition() - offset.y;
        
        //----------------------------------
        
        if (alive)
        {
            handleControls(gii);        
        
        // NOTE: you must call doTimeStep for ALL game objects once per frame!
        // updateing step for each object
        for (int i = 0 ; i < objects.size() ; i++)
        {
            objects.elementAt(i).doTimeStep();
        }
        
        // setting the camera offset
        offset.x = -player.getPosition().x + (this.getViewportDimension().width/2);
        offset.y = -player.getPosition().y + (this.getViewportDimension().height/2);
        
        // Code to prevent the camera moving past the background image
        if (offset.x > 0)
        	offset.x = 0;
        if (offset.x < -worldSize.x + this.getViewportDimension().width)
        	offset.x = -worldSize.x + this.getViewportDimension().width;
        if (offset.y > 0)
        	offset.y = 0;
        if (offset.y < -worldSize.y + this.getViewportDimension().height)
        	offset.y = -worldSize.y + this.getViewportDimension().height;

        }
    //**************************        
    // COLLISION DETECTION CODE!
    //**************************
        
        //checking each unit against each other unit for collisions
        for (int i = 0 ; i < objects.size() ; i++)
        {
        	GameObject o1 = objects.elementAt(i);
        	// If this item is marked as not collidable then skip it
        	if (!o1.getCollidable())
        		continue;
        	
        	//if the bullet collides with a wall, destroy it
        	if (o1 instanceof BulletObject)
        	{
        		if (wallCollision((BulletObject) o1))
        		{
        			o1.setMarkedForDestruction(true);
        			continue;
        		}
        	}
        	
            for (int j = i+1 ; j < objects.size() ; j++)
            {
            	GameObject o2 = objects.elementAt(j);
            	// If this item is marked as not collidable then skip it
            	if (!o2.getCollidable())
            		continue;
            	
                if (boxIntersectBox(o1.getAABoundingBox(), o2.getAABoundingBox()))
                {
                	if (o1 instanceof WallObject && o2 instanceof WallObject)
                	{
                		// Skip wall vs wall objects
                	}
                	else if (o1 instanceof PlayerObject && o2 instanceof Enemy || o1 instanceof Enemy && o2 instanceof PlayerObject)
                	{
                		if (fineTuneCollision(o1,o2))
                		{
                			if (o1 instanceof PlayerObject)
                				o1.setMarkedForDestruction(true);
                			else
                				o2.setMarkedForDestruction(true);
                			
                		}
                	}
                	else if (o1 instanceof PlayerObject && o2 instanceof SpotLight || o1 instanceof SpotLight && o2 instanceof PlayerObject)
                	{
                		if (fineTuneCollision(o1,o2))
                		{
                			alert(player);
                		}
                	}
                	else if (o1 instanceof Enemy && o2 instanceof Knife || o1 instanceof Knife && o2 instanceof Enemy)
                	{
                		if (player.isAttacking() == true)
                		{
                			if (fineTuneCollision(o1,o2))
                			{
                				if (o1 instanceof Enemy)
                				{
                					if (player.isBehind((MovingObject)o1))
                					{
                						o1.setMarkedForDestruction(true);
                					}
                				}
                				else
                				{
                					if (player.isBehind((MovingObject)o2))
                					{
                						o2.setMarkedForDestruction(true);
                					}
                				}
                			}
                			score+=50;
                		}
                	}
                	else if (o1 instanceof Enemy && o2 instanceof BulletObject || o1 instanceof BulletObject && o2 instanceof Enemy)
                	{
                		if (fineTuneCollision(o1,o2))
                		{
                			o1.setMarkedForDestruction(true);
                			o2.setMarkedForDestruction(true);
                			score+=10;
                		}
                	}
                	
                	else if (o1 instanceof PlayerObject && o2 instanceof EndPoint || o2 instanceof PlayerObject && o1 instanceof EndPoint)
                	{
                		if (fineTuneCollision(o1,o2))
                		{
                			gameWon = true;
                		}
                	}
                	else if (o1 instanceof PlayerObject && o2 instanceof PlayerObject)
                	{
                		if (fineTuneCollision(o1,o2))
                		{
                			player.revertPosition();
                		}
                	}
                	
                	else
                	{
                		
                	}
                }
            }
        }
        
    //**************************        
    //**************************
        
        // destroying units that need to be destroyed
        for (int i = 0 ; i < objects.size() ; i++)
        {
            if (objects.elementAt(i).isMarkedForDestruction())
            {
            	if (objects.elementAt(i) == player)
            	{
                    alive = false;
                }
                // removing object from list of GameObjects
                objects.remove(i);
                i--;
            }
        }
    }

    
//returns whether the bulelt collides with a wall. used so we dont include the walls
    //for normal collision detection. makes the game faster
    private boolean wallCollision(BulletObject bullet) {
    	for (WallObject w : walls)
    	{
    		if (boxIntersectBox(bullet.getAABoundingBox(), w.getAABoundingBox()))
    		{
    			if (fineTuneCollision(bullet, w))
    				return true;
    		}
    	}
    	return false;
    }

    //starts the chase
	public void startChase()
    {
    	if (chaseTimer != null)
			chaseTimer.cancel();
		secondsRemaining = CHASE_TIME;
		
		chaseTimer = new Timer();
		chaseTimer.scheduleAtFixedRate(new TimerTask(){
			public void run()
			{
				SurvivalGame.secondsRemaining -= 2;	
				if (SurvivalGame.secondsRemaining <= 0)
				{
					chaseTimer.cancel();
				
				}
				
			}
		}, 2000, 2000);
    }

	//given collision detection
	public static boolean fineTuneCollision(GameObject o1, GameObject o2) {

		ByteBuffer bb1 = o1.getCurrentTexture().getByteBuffer();
		ByteBuffer bb2 = o2.getCurrentTexture().getByteBuffer();
		int w1 = o1.getCurrentTexture().getWidth();
		int w2 = o2.getCurrentTexture().getWidth();
		int h1 = o1.getCurrentTexture().getHeight();
		int h2 = o2.getCurrentTexture().getHeight();
		Rectangle r1 = o1.getIntAABoundingBox();
		Rectangle r2 = o2.getIntAABoundingBox();
		Point subimageoffset1 = o1.getSubImageOffset();
		Point subimageoffset2 = o2.getSubImageOffset();

		Rectangle inter = r2.intersection(r1);

		boolean collided = false;

		int r1bx = subimageoffset1.x + inter.x - r1.x;
		int r1by = subimageoffset1.y + inter.y - r1.y;
		int r2bx = subimageoffset2.x + inter.x - r2.x;
		int r2by = subimageoffset2.y + inter.y - r2.y;
			

		outer : 
		for (int i = 0 ; i < inter.height ; i++) {
			for (int j = 0 ; j < inter.width ; j++) {
				int b1 = 0;
				b1 = (getAlphaAt(bb1, w1, h1, r1bx+j, r1by+i) == 0 ? 0 : 1);
				int b2 = 0;

				b2 = (getAlphaAt(bb2, w2, h2, r2bx+j, r2by+i) == 0 ? 0 : 1);

				if (b1 == 1 && b2 == 1) {
					collided = true;
					break outer;
				}
			}
		}

		return collided;
	}

	public static byte getAlphaAt(ByteBuffer b, int w, int h, int x, int y) {
		int index = (4*(x+((h-y)*w)))+3;
		return (index < w*h*4 ? b.get(index) : 0);
	}
    
    //==================================================================================================
    
    
    public void renderStep(GameDrawer drawer)
    {
    	//For every object that you want to be rendered, you must call the draw function with it as a parameter
    	
    	// NOTE: Always draw transparent objects last!
    	
    	// Offset the world so that all objects are drawn 
    	drawer.setWorldOffset(offset.x, offset.y);
        drawer.setColour(1.0f, 1.0f, 1.0f, 1.0f);

        // drawing the ground tiles
        for (int i = 0 ; i < gridTile.length ; i++ )
        {
        	for (int j = 0 ; j < gridTile[i].length ; j++ )
        	{
        		drawer.draw(gridTile[i][j], -1);
        	}
        }
        
        for (WallObject w : walls)
        {
        	w.draw(drawer);
        }
        
        // Drawing all the objects in the game
        for (GameObject o: objects)
        {
        	
        	((MyGameObject)o).draw(drawer);
        	
        }
        

        
        
        drawer.setColour(1.0f,1.0f,1.0f,1.0f);
        
        // Changing the offset to 0 so that drawn objects won't move with the camera
        drawer.setWorldOffset(0, 0);
        
        if (alive == false && gameWon == false)
        	{
        		drawer.draw(arial, "Game Over", new Point2D.Float(worldSize.x/4, worldSize.y/2), new float[]{1.0f, 0f, 0f, 1.0f}, 1.0f, 2.0f);
        	}
        if (gameWon == true)
        {
    		drawer.draw(arial, "Game Won", new Point2D.Float(worldSize.x/4, worldSize.y/2), new float[]{1.0f, 0f, 0f, 1.0f}, 1.0f, 2.0f);
    		alive = false;
        }

        
        drawer.draw(arial, ""+ secondsLeft, new Point2D.Float(worldSize.x/2,worldSize.y-100), 1.0f, 0.5f, 0.0f, 0.7f, 0.1f);
        drawer.draw(arial, "Score: "+ score, new Point2D.Float(20,worldSize.y-100), 1.0f, 0.5f, 0.0f, 0.7f, 0.1f);


       
    }

	public void alert(PlayerObject player) {
		lastSeenGridX = player.getGridX();
        lastSeenGridY = player.getGridY();
        
        startChase();
	}
}








