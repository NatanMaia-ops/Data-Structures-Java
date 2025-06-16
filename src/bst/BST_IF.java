package bst;

public interface BST_IF {
	public boolean isEmpty();
	public int height();
	public Node search(Integer value);
	public void insert(Integer value);
	public Node maximum(Node node);
	public Node minimum(Node node);
	public Node predecessor(Node node);
	public Node sucessor(Node node);
	public void remove(Integer value);
	public Integer[] preOrder();
	public Integer[] order();
	public Integer[] postOrder();
	public int size();
	public boolean isEquals(Node treeA, Node treeB);
}
