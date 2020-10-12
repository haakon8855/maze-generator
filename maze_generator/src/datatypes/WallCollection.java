package datatypes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class WallCollection implements Iterable<Wall>{
	
	private final Collection<Wall> wallCollection = new ArrayList<Wall>();
	
	/**
	 * Adds a wall to the collection of walls, but only if 
	 * that wall is not already in the colleciton.
	 * @param inWall
	 */
	public void add(Wall inWall) {
		if (!this.contains(inWall)) {
			wallCollection.add(inWall);
		}
	}
	
	/**
	 * Fetches the wall instance in the collection with the same nodes as inWall
	 * @param inWall
	 * @return wall instance in the collection with same nodes as inWall, or null if 
	 * inWall is not in the collection
	 */
	public Wall get(Wall inWall) {
		for (Wall wall : wallCollection) {
			if (wall.equals(inWall)) {
				return wall;
			}
		}
		return null;
	}
	
	/**
	 * Fetches the i'th wall instance in the collection.
	 * @param int i, index of the wall which is to be returned
	 * @return i'th wall instance in the collection
	 */
	public Wall get(int i) {
		if (i < this.size() && i >= 0) {
			return ((List<Wall>) this.wallCollection).get(i);
		}
		throw new IllegalArgumentException("Must have 0 < i < size(), got i=" + i);
	}
	
	/**
	 * Removes the wall with the same nodes as inWall from the collection.
	 * @param inWall
	 * @return wall which was removed, or null if inWall is not in the collection
	 */
	public Wall remove(Wall inWall) {
		Wall wall = this.get(inWall);
		if (wall != null) {
			wallCollection.remove(wall);
		}
		return wall;
	}
	
	/**
	 * Removes the i'th wall in the collection.
	 * @param int i, index of the wall which is to be removed.
	 * @return the wall that was removed
	 */
	public Wall remove(int i) {
		Wall wall = get(i);
		return remove(wall);
	}
	
	/**
	 * Checks whether inWall (or a corresponding wall) is in the collection or not.
	 * @param inWall
	 * @return true if inWall is in the collection
	 */
	public boolean contains(Wall inWall) {
		return this.get(inWall) != null;
	}
	
	/**
	 * Returns the size of the wall collection
	 * @return int of amount of walls in the collection
	 */
	public int size() {
		return wallCollection.size();
	}
	
	/**
	 * Returns whether the wall collection is empty
	 * @return true if wall collection is empty
	 */
	public boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * Returns an iterator of the wallCollection.
	 * @return iterator instance of the collection of walls
	 */
	@Override
	public Iterator<Wall> iterator() {
		return wallCollection.iterator();
	}
	
	@Override
	public String toString() {
		if (wallCollection.isEmpty()) {
			return "";
		}
		String outString = "[";
		for (Wall wall : wallCollection) {
			outString += wall + ", ";
		}
		outString = outString.substring(0, outString.length()-2);
		outString += "]";
		return outString;
	}
	
}












