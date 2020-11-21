package datatypes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class NodeCollection implements Iterable<Node> {
	
	private final List<Node> nodeCollection = new ArrayList<Node>();
	
	/**
	 * Adds a node to the collection of nodes, but only if
	 * that node is not already in the collecion
	 * @param inNode
	 */
	public void add(Node inNode) {
		if (!this.contains(inNode)) {
			nodeCollection.add(inNode);
		}
	}
	
	/**
	 * Fetches the node instance in the collection with the same coords as inNode
	 * @param inNode
	 * @return node instance in the collection with same coords as inNode, or null if 
	 * inNode is not in the collection
	 */
	public Node get(Node inNode) {
		Node node;
		for (int i = 0; i < nodeCollection.size(); i++) {
			node = nodeCollection.get(i);
			if (node.equals(inNode)) {
				return node;
			}
		}
		return null;
	}
	
	/**
	 * Fetches the i'th node in the collection
	 * @param i integer which denotes which node to get
	 * @return node nr i in the collection
	 */
	public Node get(int i) {
		if (i < this.size() && i >= 0) {
			return ((List<Node>) this.nodeCollection).get(i);
		}
		throw new IllegalArgumentException("Must have 0 < i < size(), got i=" + i);
	}
	
	/**
	 * Removes the node with the same coords as inNode from the collection
	 * @param inNode
	 * @return node which was removed, or null if inNode is not in the collection
	 */
	public Node remove (Node inNode) {
		Node node = this.get(inNode);
		if (node != null) {
			nodeCollection.remove(node);
		}
		return node;
	}
	
	/**
	 * Checks whether inNode (or a corresponding node) is in the collection
	 * @param inNode
	 * @return true if inNode is in the collection
	 */
	public boolean contains(Node inNode) {
		return this.get(inNode) != null;
	}

	/**
	 * Fetches the value of a given node in the collection
	 * @param inNode object with the same coordinates as the requested node.
	 * @return int value assigned to the corresponding node to inNode in the nodeCollection
	 */
	public int getNodeValue(Node inNode) {
		Node node = get(inNode);
		return node.getValue();
	}

	/**
	 * Sets the value of a given node in the collection
	 * @param inNode object with the same coordinates as the requested node.
	 * @param int value which is to be assigned to the corresponding node in the collection
	 */
	public void setNodeValue(Node inNode, int value) {
		Node node = get(inNode);
		if (node == null) {
			throw new IllegalArgumentException("Node not in nodeCollection, cannot set value.");
		}
		remove(node);
		node.setValue(value);
		add(node);
	}
	
	/**
	 * Returns the size of the collection.
	 * @return an int representing the number of nodes in the collection
	 */
	public int size() {
		return this.nodeCollection.size();
	}
	
	/**
	 * Checks if the node collection is empty.
	 * @return true if the collection is empty
	 */
	public boolean isEmpty() {
		return (this.size() == 0);
	}
	
	/**
	 * Returns with a random node from the collection
	 * @return Node object chosen randomly from the collection
	 */
	public Node getRandomNode() {
		int rnd = new Random().nextInt(this.size());
		return ((List<Node>) this.nodeCollection).get(rnd);
	}

	/**
	 * Returns an iterator of the nodeCollection.
	 * @return iterator instance of the collection of nodes
	 */
	@Override
	public Iterator<Node> iterator() {
		return nodeCollection.iterator();
	}
	
	@Override
	public String toString() {
		if (nodeCollection.isEmpty()) {
			return "";
		}
		String outString = "[";
		for (Node node : nodeCollection) {
			outString += node + ", ";
		}
		outString = outString.substring(0, outString.length()-2);
		outString += "]";
		return outString;
	}

}




















