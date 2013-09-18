
public class Tile {

	private int [] walls;
	private int row;
	private int column;
	private boolean visited = false;
	
	public Tile(int x, int y)
	{
		walls = new int[]{1,1,1,1};
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
}
