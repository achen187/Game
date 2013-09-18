import java.util.ArrayList;
import java.util.Stack;


public class MazeGenerator {

	
	private static void printMaze(Tile[][] grid)
	{
		for (int i = 0; i < SurvivalGame.numRows; i++)
		{
			for (int j = 0; j < SurvivalGame.numCols; j++)
			{
				System.out.println(grid[i][j]);
			}
		}	}
	
	public static void generateMaze(Tile[][] grid)
	{
		ArrayList <Tile> unvisited = new ArrayList<Tile>();
		initMaze(grid, unvisited);

		Stack<Tile> tileStack = new Stack<Tile>();
		
		
		Tile curTile = grid[SurvivalGame.numRows/2][0];
		curTile.setVisited(true);
		unvisited.remove(curTile);

		while (unvisited.size() > 0)
		{
			System.out.println(curTile);
			ArrayList<Tile> unvisitedNeighbors = unvisitedNeighbors(curTile, grid);
			if (unvisitedNeighbors.isEmpty() == false)
			{
				int next = (int) (Math.random() * unvisitedNeighbors.size());
				Tile newTile = unvisitedNeighbors.get(next);

				tileStack.push(curTile);
				curTile.destroyWall(newTile);

				curTile = newTile;
				curTile.setVisited(true);
				unvisited.remove(curTile);
				
			}
			else if (tileStack.empty() == false)
			{
				System.out.println("STACK");
				curTile = tileStack.pop();
			}
			else
			{
				System.out.println("Unvisited");
				int next = (int) (Math.random() * unvisited.size());
				curTile = unvisited.get(next);
				curTile.setVisited(true);
				unvisited.remove(curTile);
			}
			
		}
				
		
		
	/*    Make the initial cell the current cell and mark it as visited
	    While there are unvisited cells
	        If the current cell has any neighbours which have not been visited
	            Choose randomly one of the unvisited neighbours
	            Push the current cell to the stack
	            Remove the wall between the current cell and the chosen cell
	            Make the chosen cell the current cell and mark it as visited
	        Else if stack is not empty
	            Pop a cell from the stack
	            Make it the current cell
	        Else
	            Pick a random cell, make it the current cell and mark it as visited
*/
	}
	
	private static ArrayList<Tile> unvisitedNeighbors(Tile tile, Tile[][] grid)
	{
		ArrayList<Tile> unvisited = new ArrayList<Tile>();
		int row = tile.getRow();
		int column = tile.getColumn();
		int left = column-1;
		int right = column + 1;
		int top = row + 1;
		int bottom =  row -1;
		if (left >= 0 && grid[row][left].getVisited() == false )
			unvisited.add(grid[row][left]);
		if (right < SurvivalGame.numCols && grid[row][right].getVisited() == false)
			unvisited.add(grid[row][right]);
		
		if (bottom >= 0 && grid[bottom][column].getVisited() == false)
			unvisited.add(grid[bottom][column]);
		if (top < SurvivalGame.numRows && grid[top][column].getVisited() == false)
			unvisited.add(grid[top][column]);
		
		return unvisited;
	}
	
	private static void initMaze(Tile[][] grid, ArrayList<Tile> unvisited)
	{
		for (int i = 0; i < SurvivalGame.numRows; i++)
		{
			for (int j = 0; j < SurvivalGame.numCols; j++)
			{
				grid[i][j] = new Tile(i,j);
				unvisited.add(grid[i][j]);
			}
		}
	}
}
