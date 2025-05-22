package bst;

public class Node {
	private Integer value;
    private Node left;
    private Node right;
    private Node parent;
    
    //É sempre recomendável utilizar os métodos gets e sets para acessar
    //os atributos de uma classe.
    //Não modificar os gets e sets desta classe!
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public Node getLeft() {
		return left;
	}
	public void setLeft(Node left) {
		this.left = left;
	}
	public Node getRight() {
		return right;
	}
	public void setRight(Node right) {
		this.right = right;
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
}
