package edu.upenn.eas499.aimtd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Grid-based representation of the relevant coordinates in a TD level map.
 * @author fedenusy
 *
 */
public class Map {

	///// Instance variables /////
	Tile[][] _tiles;
	HashMap<Tile, ArrayList<Tile>> _edges;
	
	
	///// Constructors /////
	/**
	 * Convenience method, identical to Map(int[][] layout, true).
	 * @param layout The map's layout.
	 */
	public Map(int[][] layout) {
		this(layout, true);
	}
	
	/**
	 * @param layout The layout of the map represented as an array of rows; rows are themselves arrays 
	 * of integers. Within rows, 0s represent Field coordinates (upon which towers could be built), 1s 
	 * represent Road coordinates (upon which monsters can travel), and 2s represent Objective coordinates (which 
	 * monsters are trying to reach). 8s represent Rock coordinates, which are dead space. For AIMTD's purposes, 
	 * the map will be built as a square with length=(# of row arrays) and width=(# of integers in the first row). 
	 * Undefined row integers will be treated as Rocks. Example 5x5 map layout:
	 * new int[][] {	{1,1,0,0,0},
	 * 					{0,1,8,0,0},
	 * 					{0,1,1,8,0},
	 * 					{0,0,0,1,0},
	 * 					{0,0,0,0,2} };
	 * @param allowDiagonalMovement Whether the Map should allow Monsters to travel between diagonally adjacent 
	 * coordinates.
	 * 
	 */
	public Map(int[][] layout, boolean allowDiagonalMovement) {
		if (layout==null || layout[0]==null) throw new IllegalArgumentException("Invalid map layout");
		
		int length = layout.length;
		int width = layout[0].length;
		
		_tiles = new Tile[width][length];
		generateTiles(layout);
		
		_edges = new HashMap<Tile, ArrayList<Tile>>();
		generateNodes();
		generateEdges(allowDiagonalMovement);
	}
	
	private void generateTiles(int[][] layout) {
		for (int i=0; i < layout.length; i++) {
			for (int j=0; j < layout[0].length; j++) {
				try {
					if (layout[i][j]==0) _tiles[j][i] = new Tile(j, i, Tile.Type.FIELD);
					else if (layout[i][j]==1) _tiles[j][i] = new Tile(j, i, Tile.Type.ROAD);
					else if (layout[i][j]==2) _tiles[j][i] = new Tile(j, i, Tile.Type.OBJECTIVE);
					else _tiles[j][i] = new Tile(j, i, Tile.Type.ROCK);
				} catch (ArrayIndexOutOfBoundsException e) {
					_tiles[j][i] = new Tile(j, i, Tile.Type.ROCK);
				}
			}
		}
	}
	
	private void generateNodes() {
		for (int i=0; i < _tiles.length; i++) {
			for (int j=0; j < _tiles[0].length; j++) {
				if(_tiles[i][j].isWalkable()) 
					_edges.put(_tiles[i][j], new ArrayList<Tile>());
			}
		}
	}
	
	private void generateEdges(boolean generateDiagonals) {
		for (Tile node : _edges.keySet()) {
			int x = node.getX();
			int y = node.getY();
			for (int offX=-1; offX<=1; offX++) {
				for (int offY=-1; offY<=1; offY++) {
					if (!generateDiagonals && Math.abs(offX+offY)>1) continue;
					try { 
						Tile candidateNeighbor = _tiles[x+offX][y+offY];
						createEdge(node, candidateNeighbor, false);
					} catch (ArrayIndexOutOfBoundsException e) {
						continue;
					}
				}
			}
		}
	}
	
	
	///// Public methods /////
	/**
	 * Gets a random field-type Tile. This is a convenience method for the Monte Carlo Simulator; 
	 * game developers should ignore.
	 * @return A random field-type Tile in the Map.
	 */
	public Tile getRandomFieldTile() {
		Tile randomFieldTile = null;
		while (randomFieldTile==null) {
			int x = (int) Math.floor(Math.random() * _tiles.length);
			int y = (int) Math.floor(Math.random() * _tiles[0].length);
			randomFieldTile = getTile(x, y);
			if (!randomFieldTile.isField()) randomFieldTile = null;
		}
		return randomFieldTile;
	}
	
	///// Package-protected methods /////
	/**
	 * @param x
	 * @param y
	 * @return The Tile at the specified (x,y) position, or null if the coordinate is out of
	 * bounds.
	 */
	Tile getTile(int x, int y) {
		try {
			return _tiles[x][y];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	Collection<Tile> getNodes() {
		return _edges.keySet();
	}
	
	Collection<Tile> getNeighbors(Tile t) {
		return _edges.get(_tiles[t.getX()][t.getY()]);
	}
	
	Collection<Tile> getObjectives() {
		ArrayList<Tile> result = new ArrayList<Tile>();
		for (Tile tile : _edges.keySet()) {
			if (tile.isObjective()) result.add(tile);
		}
		return result;
	}
	
	/**
	 * Updates the damage-cost of moving over each tile.
	 */
	void resetTilesDamage() {
		for (Tile[] tileColumn : _tiles) {
			for (Tile tile : tileColumn) {
				tile.setDamageCost(0);
			}
		}
	}
	
	/**
	 * Creates an Edge from t1 to t2, allowing Monsters to travel between the two Tiles.
	 * @param t1
	 * @param t2
	 * @param biDirectional Whether another edge should be created from t2 to t1.
	 * @return Whether the edge was successfully created.
	 */
	boolean createEdge(Tile t1, Tile t2, boolean biDirectional) {
		if (!t1.isWalkable() || !t2.isWalkable()) return false;
		_edges.get(t1).add(t2);
		if (biDirectional) _edges.get(t2).add(t1);
		return true;
	}
	
}

