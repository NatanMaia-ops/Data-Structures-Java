package SingleLinkedList;

public class LinkedList implements LinkedList_IF{
	No inicio;
	No fim;


	@Override
	public boolean isEmpty() {
		//Se a lista estiver vazia, tanto o inicio como o final da lista aponta para null, ou o tamanho e zero
		if(inicio == null && fim == null){
			return true;
		}
		//Se nenhuma das condicoes acima for verdadeira a lista nao esta vazia
		return false;
	}

	@Override
	public int size() {
		//Se a lista estiver vazia retornamos zero
		if(isEmpty()){
			return 0;
		}
		//Se a lista nao estiver vazia, percorremos ela com um ponteiro auxiliar
		//para nos ajudar a contabilizar quantos elementos temos na lista (ate chegar em null)
		No aux = inicio;
		int tamanho = 0;
		while(aux != null){
			tamanho++;
			aux = aux.proxNo;
		}
		//Ao final da contagem, retornamos o total de elementos da lista
		return tamanho;
	}

	@Override
	public No search(Integer elemento) {
		//Se a lista estiver vazia certamente o nosso elemento nao esta nela 
		if(isEmpty()){
			return null;
		}

		//Para otimzar a busca implementamos dois ponteiros auxiliares para percorrer a lista de forma bidirecional (nois dois sentidos)
		//A complexidade continua sendo linear porem diminuiremos a quantidade de operacoes pela metade fazendo dessa forma
		No auxInicio = inicio;
		No auxFinal = fim;

		//Enquanto os ponteiros nao chegarem aos seus respectivos null, ele continuara varrendo a lista atras do elemento
		while(auxInicio != null && auxFinal != null){
			//Se o auxiliar que aponta pro comeco da lista contiver o elemento, retornamos esse no auxiliar
			if(auxInicio.dado.equals(elemento))
				return auxInicio;

			//Se o auxiliar que aponta pro final da lista contiver o elemento, o retornamos
			if(auxFinal.dado.equals(elemento))
				return auxFinal;

			//Se por acaso esses nos se cruzarem ou estiverem lado a lado depois de ser feito a verificacao significa que o no nao foi encontrado
			//logo nao precisamos que esses nos percorram ate a outra ponta por isso essa condicao nos ajuda nesse caso
			if(auxFinal == auxInicio || auxInicio.proxNo == auxFinal)
				return null;

			//O no auxiliar do inicio percorre para o proximo enquanto o no que aponta para o final faz o sentido contrario
			auxInicio = auxInicio.proxNo;
			auxFinal = auxFinal.antNo;
		}
		//Se nenhuma dessas condicoes foi satisfeita logo o nosso elemento procurado nao consta na lista
		return null;
	}

	@Override
	public void insert(Integer novoElemento) {
		No novo = new No(novoElemento);

		//Se a lista estiver vazia, tanto o inicio como o fim da lista vao apontar para o mesmo elemento
		if(isEmpty()){
			inicio = novo;
			fim = novo;
			return;
		}else{
			//Caso contrario o ponteiro anterior do primeiro no da lista ira apontar para o nosso novo elemento garantindo o encadeamento
			//O fim da lista continuara apontando para o mesmo elemento ja que o nosso novo elemento esta sendo incluido no inicio
			//Como adicionamos no inicio da lista temos um novo inicio para onde a cabeca da lista ira apontar agora
			novo.proxNo = inicio;
			inicio.antNo = novo;
			inicio = novo;
		}
	}

	@Override
	public No remove(Integer elemento) {
		if(isEmpty()){
			return null;
		}

		//vamos usar um ponteiro auxiliar para varrer a lista atras do elemento a ser removido
		No aux = inicio;
		//Nesse laco, vamos percorrer ate a lista chegar ao final ou ate encontrar o elemento a ser removido
		while(aux != null && !aux.dado.equals(elemento)){
			aux = aux.proxNo;
		}

		//se a lista chegou ao final significa que o elemento nao se encontra na nossa lista 
		if(aux == null)
			return null;

		//Se o anterior do auxiliar for igual a null, significa que o elemento que temos que remover se encontra no inicio da lista
		if(aux.antNo == null){
			return removeInicio();
		}

		//Se o proximo do auxiliar for igual a null, significa que o elemento que temos que remover e o ultimo elemento da lista
		if(aux.proxNo == null){
			return removeFinal();
		}

		//Se nao for nenhum desses dois casos, significa que o elemento que precisamos remover se encontra no meio da lista
		//para realizar uma remocao no meio da lista precisamos conectar o No anterior do auxiliar com o proximo No do auxiliar
		//O No anterior do auxiliar recebe o proximo do auxiliar
		aux.antNo.proxNo = aux.proxNo;
		//O proximo do auxiliar aponta tambem para o no anterior do auxiliar, garantinho o encadeamento
		aux.proxNo.antNo = aux.antNo;

		//por ultimo podemos isolar o no auxiliar para que ele nao tenha mais nenhuma conexao com nossa lista (boas praticas)
		aux.proxNo = null;
		aux.antNo = null;

		//por ultimo retornamos o No removido
		return aux;
	}

	@Override
	public No removeInicio() {
		//Se a lista estiver vazia nao temos como remover um elemento
		if(isEmpty()){
			return null;
		}

		//Usamos um No auxiliar para nao perdemos a referencia do comeco da lista ja que 
		//na insercao no inicio movemos a cabeca da lista para o proximo elemento
		No aux = inicio;
		//se a lista contiver apenas um elemento, quando removemos tanto o inicio como o fim da lista vao apontar para null
		if(inicio == fim){
			inicio = null;
			fim = null;
		}else{
			//Se tivermos mais de um elemento na lista simplesmente apontamos o inicio da lista para o proximo elemento
			//Por questao de padronizacao podemos setar o antigo inicio como null
			inicio = inicio.proxNo;
        	inicio.antNo = null;
		}
		aux.proxNo = null;
		aux.antNo = null;
		//No final retornamos o No auxiliar que aponta para o antigo inicio da lista de foi removido
		return aux;
	}

	@Override
	public No removeFinal() {
		//Se a lista estiver vazia nao temos como remover um elemento
		if(isEmpty())
			return null;

		//Usamos da mesma logica de remocao no inicio so que por ser uma remocao agora no final da lista 
		//trabalhamos agora com a calda da lista 
		No aux = fim;
		if(inicio == fim){
			//se tivermos apenas um elemento apontamos o inicio e o fim da lista para null
			inicio = fim = null;
		}
		else{
			//se tivermos mais de um elemento movemos a calda da lista para o elemento anterior e guardamos o antigo fim da lista em um No auxiliar
			//depois disso apenas por questao de boas praticas setamos o antigo final da lista como null
			fim = fim.antNo;
			fim.proxNo = null;
			
		}
		aux.antNo = null;
		aux.proxNo = null;
		//apos a remocao, retornamos o antigo final da lista
		return aux;
	}

	@Override
	public No[] toArray() {
		//se a lista estiver vazia nao teremos elementos para colocar em um array logo retornamos null
		if(isEmpty()){
			return null;
		}

		//Criamos um array com o atual tamanho da nossa lista para evitar alocacao desnecessaria de memoria
		//vamos iterando sobre cada No da lista adicionando ele no nosso array usando um indice auxiliar
		No[] listToArray = new No[size()];
		No aux = inicio;
		int index = 0;
		while(aux != null){
			listToArray[index++] = aux;
			aux = aux.proxNo;
		}

		//No final retornamos o array com os Nos da nossa lista encadeada
		return listToArray;
	}

	@Override
	public String toString() {
		//Esse metodo simplesmente salva a lista dentro de um StringBuilder (mais perfomatico que uma string comum)
		//Se a lista nao estiver vazia e claro

		if(isEmpty()){
			return "A lista esta vazia";
		}

		StringBuilder list = new StringBuilder();
		list.append("head -> ");

		No aux = inicio;
		while(aux != null){
			list.append(aux.dado + " -> ");
			aux = aux.proxNo;
		}
		list.append("null");

		return list.toString();
	}
}
