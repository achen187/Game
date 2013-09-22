import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;


public class Tile {

	private int [] walls;
	private int row;
	private int column;
	private boolean visited = false;
	private SurvivalGame game;
	
	public Tile(int x, int y, SurvivalGame game)
	{
		walls = new int[]{1,1,1,1};
		this.game = game;
		this.row = x;
		this.column = y;
	}
	
	public boolean getVisited()
	{
		return visited;
	}
	
	public void setVisited(boolean b)
	{
		visited = b;
	}
	
	public void setWall(int wall, int exists)
	{
		walls[wall] = exists;
	}
	
	public boolean hasWall(int wall)
	{
		if (walls[wall] == 1)
			return true;
		return false;
	}
	
	public int getMidX()
	{
		return column * SurvivalGame.widthTile + SurvivalGame.widthTile/2;
	}
	
	public int getMidY()
	{
		return row * SurvivalGame.heightTile + SurvivalGame.heightTile/2;

	}
	
	public void destroyWall(Tile other)
	{
		if (Math.abs(row - other.row)> 1 || Math.abs(column - other.column) > 1)
			System.out.println("ERROR NOT NEIGHBORS thix: " + row + " " + column + "!!!! " + "other: "+ other.row + " " + other.column );
		
		if (other.row > row)
		{
			setWall(0,0);
			other.setWall(2, 0);
		}
		else if (other.row < row)
		{
			setWall(2,0);
			other.setWall(0,0);
		}
		else if (other.column > column)
		{
			setWall(1,0);
			other.setWall(3,0);
		}
		else if (other.column < column)
		{
			setWall(3,0);
			other.setWall(1,0);
		}
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getColumn()
	{
		return column;
	}
	
	public String toString()
	{
		return "row: " + row + " column: " + column + " north " + walls[0] + " east " + walls[1] + " south " + walls[2] + " west " + walls[3] + " visited " + visited; 
	}
	
	public WallObject northWall()
	{
		int x = column * SurvivalGame.widthTile + SurvivalGame.widthTile/2;
		int y = (row+1)*SurvivalGame.heightTile;
		System.out.println(x + " " + y);
		return new WallObject(x,y,game,0);
		
	}
	
	public WallObject eastWall()
	{
		int x = (1+column) * SurvivalGame.widthTile ;
		int y = row*SurvivalGame.heightTile + SurvivalGame.heightTile/2;
		System.out.println(x + " " + y);

		return new WallObject(x,y,game,90);
		
	}
	
	public WallObject westWall()
	{
		int x = column * SurvivalGame.widthTile;
		int y = row*SurvivalGame.heightTile + SurvivalGame.heightTile/2;
		System.out.println(x + " " + y);

		return new WallObject(x,y,game,270);
		
	}
	
	public WallObject southWall()
	{
		int x = column * SurvivalGame.widthTile + SurvivalGame.widthTile/2;
		int y = row*SurvivalGame.heightTile;
		System.out.println(x + " " + y);

		return new WallObject(x,y,game,180);
		
	}
}
