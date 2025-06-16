package SingleLinkedList;

public interface LinkedList_IF {
	public boolean isEmpty();
    public int size();
    public No search(Integer elemento);
    public void insert(Integer novoElemento);
    public No remove(Integer elemento);
    public No removeInicio();
    public No removeFinal();
    public No[] toArray();
}
