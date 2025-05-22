package bst;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class BSTImpl implements BST_IF {
	
	private Node root; //nó raiz
	
	public BSTImpl() { //Utilizar o construtor padrão

	}

	public Node getRoot(){
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		//funcao que verifica se a arvore esta vazia comparando se a raiz da arvore aponta para null
		return root == null;
	}

	@Override
	public int height() {
		//funcao que retorna a altura da arvore (que e a maior distancia entre a raiz e uma folha)
		return heightRecursive(root);
	}


	private int heightRecursive(Node node){
		//Se o no atual for null retornamos -1
		if(node == null){
			return -1;
		}

		//A cada No a esquerda e a direita que percorremos na arvore incrementamos em 1, a maior sera considerado apenas o maior valor dentre as subarvores a esquerda e a direita
		return 1 + Math.max(heightRecursive(node.getLeft()), heightRecursive(node.getRight()));
	}

	@Override
	public Node search(Integer value) {
		//funcao chamadora do metodo recursivo para achar um elemento na arvore
		return searchRecursive(root,value);
	}

	private Node searchRecursive(Node current, Integer value){
		//Se o No atual for null ou o conteudo do no atual for o valor que estamos procurando, retornamos esse proprio NO
		//Se o elemento nao estiver na arvore o No current retornara null, caso contrario sera retornado o No com o elemento procurado
		if(current == null || current.getValue() == value){
			return current;
		}

		//caso contrario precisamos percorrer recursivamente (com tempo O(log n)) a arvore em busca desse elemento se ele estiver de fato na arvore
		//se o elemento procurado for menor que o elemento atual pulamos para o proximo No a esquerda
		if(value < current.getValue()){
			return searchRecursive(current.getLeft(), value);
		}else{
			//caso contrario o elemento procurado e maior que o elemento atual entao pulamos para o elemento a direita
			return searchRecursive(current.getRight(), value);
		}
	}



	@Override
	public void insert(Integer value) {
		//se a arvore estiver vazia, o novo elemetnto inserido sera nossa raiz da arvore
		if(isEmpty()){
			Node newNode = new Node();
			newNode.setValue(value);
			root = newNode;
			return;
		}

		//caso nao for a raiz chamamos a funcao recursiva para percorrer a arvore para uma posicao valida de insercao
		insertRecursive(root, value);
	}

	private void insertRecursive(Node current, Integer value){
		Node newNode = new Node();
		newNode.setValue(value);

		//Caso o valor do elemento inserido seja menor que o do elemento atual olhamos a subarvore a esquerda
		if(value < current.getValue()){
			//Se o elemento estiver com a esquerda vazia, sera nessa posicao que vamos inserir nosso elemento, setando inclusive o pai para nosso No atual
			if(current.getLeft() == null){
				current.setLeft(newNode);
				newNode.setParent(current);
			}else{
				//Caso contrario, percorremos nossa subarvore a esquerda
				insertRecursive(current.getLeft(), value);
			}
		}else{
			//Se nosso elemento for maior que o atual, verificamos se a direita do No atual esta disponivel, caso esteja inserimos nosso elemento
			if(current.getRight() == null){
				current.setRight(newNode);
				newNode.setParent(current);
			}else{
				//Caso contrario, vamos percorrer nossa arvore a direita
				insertRecursive(current.getRight(), value);
			}
		}
	}

	@Override
	public Node maximum(Node node) {
		//Para encontra o maior elemento da nossa arvore precisamos encontrar o elemento mais a direita da nossa subarvrore a direita
		//Caso a arvore esteja vazia, ou a raiz passada for nula, retornamos null
		if(isEmpty() || node == null)
			return null;
		
		//caso contrario varremos nossa arvore a direita ate o elemento mais a direita (ultimo elemento a direita)
		while(node.getRight() != null){
			node = node.getRight();
		}

		//Ao terminar, retornamos o elemento
		return node;
	}

	@Override
	public Node minimum(Node node) {
		if(node == null || isEmpty()){
			return null;
		}
		//Para encontrar o menor elemento da arvore a logica e bem parecida, precisamos percorrer a ate o elemento mais a esquerda, da nossa subarvore a esquerda
		while(node.getLeft() != null){
			node = node.getLeft();
		}
	//se nao tiver esquerda ele retornara a propria raiz, mas se tiver direita ele retornara o elemento mais a esquerda
		return node;
	}

	@Override
	public Node predecessor(Node node) {
		//O predecessor possui uma logica parecida com o sucessor 
		if(node == null || isEmpty()){
			System.out.println("Elemento nao encontrado na arvore, ou arvore vazia");
			return null;
		}

		//se a raiz que passamos tem uma subarvore a esquerda, retornamos o valor maximo da subarvore direita
		if(node.getLeft() != null){
			return maximum(node.getLeft());
		}
		else{
			//caso contrario precisamos achar o parente mais proximo que contenha um ancestral do elemento atual a sua direita
			//percorremos enquanto o ancestral for diferente de null e enquanto o filho do ancestral for filho a esquerda
			//logo quando sair do laco teremos o ancestral com outro ancestral a direita
			Node parentAux = node.getParent();
			while(parentAux != null && node == parentAux.getLeft()){
				node = parentAux;
				parentAux = parentAux.getParent();
			}
			//retornamos o predecessor
			return parentAux;
		}
	}

	@Override
	public Node sucessor(Node node) {
		//Se a raiz passada for nula ou a arvore estiver vazia retornamos null
		if(node == null || isEmpty()){
			System.out.println("Elemento nao encontrado na arovore, ou arvore vazia");
			return null;
		}

		//Se a raiz passada tiver uma arvore a direita, retornamos o menor valor a direita
		if(node.getRight() != null){
			return minimum(node.getRight());
		}
		else{
			//caso contrario precisamos encontrar o ancestral que possui outro ancestral do elemento analisado a sua esquerda
			//para isso usamos um ponteiro auxiliar para percorrer enquando o parente for diferente de null e enquanto o filho 
			//desse parente for um filho, direito, logo o laco vai parar quando tivermos um parente com outro parente a esquerda
			Node parentAux = node.getParent();
			while(parentAux != null && node == parentAux.getRight()){
				node = parentAux;
				parentAux = parentAux.getParent();
			}
			//retornamos o sucessor
			return parentAux;
		}
	}

	@Override
	public void remove(Integer value) {
		//funcao chamadora do nosso metodo de remocao recursivo
		root = removeRecursive(root, value);
	}

	private Node removeRecursive(Node current, Integer value) {
		//O No current seria a raiz atual da arvore que estamos buscando o elemento para remocao, caso seja nula nao sera possivel realizar a remocao desse elemento
		if (current == null){
			System.out.println("A arvore esta vazia");
			return null;
		}
			

		//Esse metodo recursivo trabalha percorrendo os Nos da arvore setando os Nos anteriores com base se o valor a ser removido e maior ou menor que o valor atual
		//Se o valor removido for menor que o valor atual
		if (value < current.getValue()) {
			//Chamamos recursivamente nosso metodo, setando nosso ponteiro a esquerda do nosso No atual para que na hora que seja realizado um dos tres casos 
			//Esse ponteiro retornado para o no pai seja setado para null (caso 1), para no filho do filho (caso 2, seja esquerda ou direita), ou para o seu sucessor (caso 3) 
			//No Primeiramente vamos percorrer recursivamente a arvore em busca do No que precisamos remover, caso maior (procuramos a direita), caso menor (procuramos a esquerda)
			current.setLeft(removeRecursive(current.getLeft(), value));
		} else if (value > current.getValue()) {
			current.setRight(removeRecursive(current.getRight(), value));


			//Se o valor que estamos procurando nao for maior nem menor isso so pode significar que encontramos o elemento a ser removido, entao agora precisamos analisar o tipo de remocao
			//que iremos realizar com base na quantidade de filhos que o nosso elemento removido tem 
		} else {
			// Caso 1 -> nesse casso, usamos essas duas verificacoes para garantir tanto o primeiro como o segundo caso da remocao
			//Se a esquerda estiver disponivel significa que talvez tenhamos um filho a direira, nesse caso vamos fazer uma "aposta" criando um novo ponteiro para esse suposto filho a direita
			//Se esse ponteiro for null significa que o No que vamos remover e um No folha e vai retornar null para o No anterior (caindo no primeiro caso)
			//Caso contrario, se nosso ponteiro for diferente de null significa que temos um elemento a direita e apontamos o avo dele para ele elemento a direita, garantindo o encadeamento da arvore
			if (current.getLeft() == null) {
				Node rightChild = current.getRight();
				if (rightChild != null) rightChild.setParent(current.getParent());
				return rightChild;
			}

			//A mesma logica e seguida nessa verificacao, se nossa direita for nula, signifia que talvez tenhamos um lemento a nossa esquerda na arvore,
			//O que vai garantir se vamos cair no primeiro ou no segundo caso e se o outro lado tambem for nulo ou nao
			//Se minha direita for null, significa que talvez tenhamos um elemento a esquerda, entao usamos um ponteiro para a esquerda, se esse ponteiro for null, significa que era um No folha
			//caindo no primeiro caso, se for diferente de null significa que o No removido tem um filho a esquerda entao apontamos o pai do no removido para esse filho a esquerda
			if (current.getRight() == null) {
				Node leftChild = current.getLeft();
				if (leftChild != null) leftChild.setParent(current.getParent());
				return leftChild;
			}

			// Caso nao entre em nenhuma dessas condicoes, so pode significar que o No que pretendemos remover tem dois filhos, caindo no terceiro caso, onde precisamos substituir o no removido
			//pelo seu sucessor (que e o menor elemento da sua subarvore a direita), quando achamos o sucessor trocamos apenas seus valores ao inves que trocar os ponteiros, a tambem setamos 
			//O No pai do sucessor para null, verificando se o sucessor vai cair em algum dos tres casos para ser removido
			Node temp = sucessor(current);
			current.setValue(temp.getValue());
			current.setRight(removeRecursive(current.getRight(), temp.getValue()));
		}
		//Ao final retornamos o No removido
		return current;
	}


	@Override
	public Integer[] preOrder() {
		//Para nao precisar lidar com uma quantidade fixa de elementos de antemao usei uma estrutura auxiliar para armazenar os valores da arvore e no final usei um Casting para retornar 
		//Os valores da arvore em forma de um array de elementos
		List<Integer> treeArray = new ArrayList<>();
		//Tambem utilzei um metodo recursivo de forma a facilitar o percurso dentro da arvore e armazenando seus elementos na lista.
		preOrderRecursive(root, treeArray);
		return treeArray.toArray(new Integer[0]);
	}

	private void preOrderRecursive(Node current, List<Integer> treeArray){
		//Se a raiz atual for vazia, retornamos
		if(current == null){
			return;
		}

		//Caso contrario percorremos a partir da raiz passada, na pre-ordem primeiramente visitamos a raiz (imprimimos ou colocamos dentro da lista), depois disso visitamos seus filhos a esquerda e a direita
		if(current != null){
			treeArray.add(current.getValue());
			preOrderRecursive(current.getLeft(), treeArray);
			preOrderRecursive(current.getRight(), treeArray);
		}
	}

	@Override
	public Integer[] order() {
		List<Integer> treeArray = new ArrayList<>();
		orderRecursive(root, treeArray);
		return treeArray.toArray(new Integer[0]);
	}

	private void orderRecursive(Node current, List<Integer> treeArray){
		if(current == null){
			return;
		}

		//Ja na Ordem (ou ordem simetrica) primeiramente visitamos os filhos a esquerda, depois visitamos a raiz e depois os filhos a direita, gerantinho uma percurssao em ordem pela estrutura de arvore
		//Os elementos exibidos em ordem simetrica serao exibidos de forma ordenada desde que a arvore mantenha sua estrutura
		if(current != null){
			orderRecursive(current.getLeft(), treeArray);
			treeArray.add(current.getValue());
			orderRecursive(current.getRight(), treeArray);
		}
	}

	@Override
	public Integer[] postOrder() {
		List<Integer> treeArray = new ArrayList<>();
		postOrderRecursive(root, treeArray);
		return treeArray.toArray(new Integer[0]);
	}

	public void postOrderRecursive(Node current, List<Integer> treeArray){
		if(current == null){
			return;
		}

		//Ja na pos-ordem a raiz e visitada por ultimo, primeiramente visitamos os filhos a esquerda, em seguida os filhos a direita para assim depois visitarmos nossa raiz
		if(current != null){
			postOrderRecursive(current.getLeft(), treeArray);
			postOrderRecursive(current.getRight(), treeArray);
			treeArray.add(current.getValue());
		}
	}

	@Override
	public int size() {
		return sizeRecursive(root);
	}

	private int sizeRecursive(Node node){
		if(node == null){
			return 0;
		}
		//Nosso size retorna a quantidade de Nos que temos atualmente na nossa estrutura de arvore, incrementando enquanto passeamos na arvore
		return 1 + sizeRecursive(node.getLeft()) + sizeRecursive(node.getRight());
	}


	@Override
	public String toString() {
		//metodo que pega os elementos em ordem da nossa arvore e transforma em uma String
		StringBuilder tree = new StringBuilder();
		Integer[] treeArray = order();

		for (int i = 0; i < treeArray.length; i++) {
			tree.append(treeArray[i]).append(" ");
		}

		return tree.toString();
	}

	/**
	 * Método de brinde! Não modificar!
	 * Este método implementa uma busca em largura usando uma fila.
	 * @return
	 */
    public ArrayList<Integer> bfs() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        Deque<Node> queue = new LinkedList<Node>();
        
        if (!isEmpty()) {
            queue.addLast(this.root);
            while (!queue.isEmpty()) {
                Node current = queue.removeFirst();
                
                list.add(current.getValue());
                
                if(current.getLeft() != null) 
                    queue.addLast(current.getLeft());
                if(current.getRight() != null) 
                    queue.addLast(current.getRight());   
            }
        }
        return list;
    }

}
