package bst;

import java.util.Scanner;

public class Teste {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int op = -1;
        BSTImpl tree = new BSTImpl();
        int[] valores = {50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45, 55, 65, 75, 90};
        
        for (int i = 0; i < valores.length; i++) {
            tree.insert(valores[i]);
        }


        do{
            System.out.println("1 para inserir um elemento na arvore");
            System.out.println("2 para remover um elemento da arvore");
            System.out.println("3 para imprimir os elementos da arvore");
            System.out.println("4 para mostrar o maior elemento da arvore");
            System.out.println("5 para mostrar o menor elemento da arvore");
            System.out.println("6 para mostrar o tamanho da arvore");
            System.out.println("7 para mostrar a altura da arvore");
            System.out.println("8 para procurar um elemento na arvore");
            System.out.println("9 para mostrar o sucessor de um No");
            System.out.println("10 para mostrar o antecessor de um NO");
            System.out.println("0 para sair do programa");
            System.out.print("Informe uma opcao: ");
            op = scan.nextInt();

            switch (op) {
                case 1:
                    System.out.print("Informe o valor a ser inserido na arvore: ");
                    tree.insert(scan.nextInt());
                    break;

                case 2:
                    System.out.print("Informe o elemento a ser removido: ");
                    tree.remove(scan.nextInt());
                    break;

                case 3:
                    System.out.println(tree);
                    break;

                case 4:
                    System.out.println("Maior elemento da arvore: " + tree.maximum(tree.getRoot()).getValue());
                    break;

                case 5:
                    System.out.println("Menor elemento da arvore: " + tree.minimum(tree.getRoot()).getValue());
                    break;

                case 6:
                    System.out.println("Tamanho atual da arvore: " + tree.size());
                    break;

                case 7:
                    System.out.println("Altura da arvore: " + tree.height());
                    break;

                case 8:
                    System.out.print("Informe o elemento a ser buscado: ");
                    Node result = tree.search(scan.nextInt());
                    if(result != null){
                        System.out.println("Elemento encontrado: " + result.getValue());
                    }else{
                        System.out.println("Elemento nao encontrado na arvore");
                    }
                    break;

                case 9:
                    System.out.print("Informe o elemento que quer achar o sucessor: ");
                    int elemento = scan.nextInt();
                    Node sucessor = tree.sucessor(tree.search(elemento));
                    if(sucessor != null){
                        System.out.println("Sucessor de " + elemento + " -> " + sucessor.getValue());
                    }else{
                        System.out.println("Nao foi possivel encontrar o sucessor do elemento");
                    }
                    break;

                case 10:
                    System.out.print("Informe o elemento que quer achar o predecessor: ");
                    int elemento2 = scan.nextInt();
                    Node predecessor = tree.predecessor(tree.search(elemento2));
                    if(predecessor != null){
                        System.out.println("Predecessor do elemento " + elemento2 + " -> " + predecessor.getValue());
                    }else{
                        System.out.println("Nao foi possivel achar o predecessor desse elemento");
                    }
                    break;

                default:
                    break;
            }
        }while(op != 0);

        scan.close();
    }    
}
