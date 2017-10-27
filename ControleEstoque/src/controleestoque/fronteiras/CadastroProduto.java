/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controleestoque.fronteiras;

import java.util.Scanner;
import controleestoque.Entidades.Produto;
import controleestoque.Armazenamento.ArmazenamentoProduto;

/**
 *
 * @author 20162in005
 */
public class CadastroProduto {
	private static final int OPCAO_INSERIR = 1;
	private static final int OPCAO_LISTAR = 2;
	private static final int OPCAO_ALTERAR = 3;
	private static final int OPCAO_EXCLUIR = 4;
	private static final int OPCAO_VOLTAR_MENU_ANTERIOR = 5;

	private Scanner ler;

	public void exibirMenu() {
		ler = new Scanner(System.in);
		int opcao = 0;
		while (opcao != OPCAO_VOLTAR_MENU_ANTERIOR) {
			System.out.println("\nOpcoe de cadastro de Produto:");
			System.out.println("1 Inserir");
			System.out.println("2 Listar");
			System.out.println("3 Alterar");
			System.out.println("4 Excluir");
			System.out.println("5 Voltar ao Menu Principal");
			System.out.println("---> Escolha a opcao: ");
			opcao = ler.nextInt();
			processarOpcaoUsuario(opcao);
		}
	}

	private void processarOpcaoUsuario(int opcao) {
		switch (opcao) {
		case OPCAO_INSERIR:
			inserir();
			break;
		case OPCAO_LISTAR:
			listar();
			break;
		case OPCAO_ALTERAR:
			alterar();
			break;
		case OPCAO_EXCLUIR:
			excluir();
			break;
		default:
			if (opcao != OPCAO_VOLTAR_MENU_ANTERIOR)
				System.err.println("\nOPÇÃO INVÁLIDA!");
		}
	}

	private void inserir() {
		System.out.println("\nInserir novo registro de produto:");
		System.out.println("- Codigo: -");
		long codigo = ler.nextLong();
		ler.nextLine(); // para construir a quebra de linha!
		System.out.println("- Nome: -");
		String nome = ler.nextLine();
		System.out.println("- Preco -");
		double preco = ler.nextDouble();
		Produto novoProduto = new Produto(codigo, nome, preco);
		ArmazenamentoProduto.inserirProduto(novoProduto);

	}

	private void listar() {
		System.out.println("\nListagem de produtos registrados.\n");
		System.out.println("+--------+--------------------------------+------------+");
		System.out.println("| Código | Nome                           | Preço      |");
		System.out.println("+--------+--------------------------------+------------+");
		for (Produto p : ArmazenamentoProduto.getListaProduto()) {
			System.out.printf("| %6d | %-30s | %10.2f |\n", p.getCodigo(), p.getNome(), p.getPreco());
		}
		System.out.println("+--------+--------------------------------+------------+");

	}

	private void alterar() {
		System.out.println("\nAlterar registro de produto:\n");
		System.out.print("Insira o codigo: ");
		long codigo = ler.nextLong();
		ler.nextLine();
		Produto p = new Produto(codigo, "", 0);

		Produto produtoParaAlterar = ArmazenamentoProduto.buscarProduto(p);

		// caso nao encontre, exibir uma mens de aviso
		if (produtoParaAlterar == null) {
			System.out.println("PRODUTO NAO CADASTRADO COM O CODIGO INFORMADO");
			return;
		}
		// exibir nome
		System.out.println("\nNome: " + produtoParaAlterar.getNome());
		System.out.println("Deseja alterar o nome? (s=sim/n=nao)");
		char opcaoNome = ler.nextLine().charAt(0);
		ler.nextLine();

		String nome = produtoParaAlterar.getNome();
		if (opcaoNome == 's') {
			System.out.print(" - Novo nome: ");
			nome = ler.nextLine();
		}
		System.out.printf("\nPreco: %.2f\n" + produtoParaAlterar.getPreco());
		System.out.println("Deseja alterar o preco? (s=sim/n=nao)");
		char opcaoPreco = ler.nextLine().charAt(0);
		ler.nextLine();
		double preco = produtoParaAlterar.getPreco();
		if (opcaoPreco == 's') {
			System.out.print(" - Novo preco: ");
			preco = ler.nextDouble();
		}
		// confirmacao final
		System.out.println("Deseja mesmo alterar esse produto? (s=sim/n=nao)");
		System.out.printf(" - Codigo: %d\n", produtoParaAlterar.getCodigo());
		System.out.printf(" - Nome: %s\n", nome);
		System.out.printf(" - Preco: %.2f\n", preco);
		char alterar = ler.nextLine().charAt(0);
		if (alterar == 's') {
			Produto produtoAlterado = new Produto(codigo, nome, preco);
			ArmazenamentoProduto.alterarProduto(produtoAlterado);

		}
	}

	private void excluir() {
		System.out.println(" - Codigo do produto a excluir: ");
		long codigo = ler.nextLong(); // Obter o codigo do produto a excluir.
		ler.nextLine();
		// Buscar dados do produto para confirmar exclusao.
		Produto parametroBusca = new Produto(codigo, "", 0);
		Produto produtoExcluir = ArmazenamentoProduto.buscarProduto(parametroBusca);
		if (produtoExcluir == null) {
			System.out.println("Nao ha produto cadastrado com o codigo informado!");
			return;
		}
		System.out.println(" - Nome: " + produtoExcluir.getNome());
		System.out.printf(" - Preco: %.2f\n", produtoExcluir.getPreco());
		System.out.println("Confirmar exclusao? (s = sim, n = nao)");
		char opcao = ler.nextLine().charAt(0);
		ler.nextLine();

		if (opcao == 's') {
			ArmazenamentoProduto.excluirProduto(produtoExcluir);

		}
	}
}
