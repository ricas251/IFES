package controleestoque.fronteiras;

import java.util.Scanner;

import controleestoque.Armazenamento.ArmazenamentoItemCompra;
import controleestoque.Armazenamento.ArmazenamentoProduto;
import controleestoque.Entidades.Compra;
import controleestoque.Entidades.ItemCompra;
import controleestoque.Entidades.Produto;

public class CadastroItemCompra {

	private static final int OPCAO_INSERIR = 1;
	private static final int OPCAO_LISTAR = 2;
	private static final int OPCAO_ALTERAR = 3;
	private static final int OPCAO_EXCLUIR = 4;
	private static final int OPCAO_CONCLUIR_CADASTRO = 5;

	private Scanner ler;

	private Compra compraReferencia;

	public CadastroItemCompra(Compra c) {
		ArmazenamentoItemCompra.iniciarListaItemCompra();
		ArmazenamentoItemCompra.getLista().addAll(c.getItensCompra());
		compraReferencia = c;
	}

	public void exibirMenu() {
		ler = new Scanner(System.in);

		int opcao = 0;
		while (opcao != OPCAO_CONCLUIR_CADASTRO) {
			System.out.println("\n\nOpções do cadastro de itens da compra:");
			System.out.println(" 1 - Inserir");
			System.out.println(" 2 - Listar");
			System.out.println(" 3 - Alterar");
			System.out.println(" 4 - Excluir");
			System.out.println(" 5 - Concluir cadastro de itens da compra");
			System.out.print("---> Digite o número da opção desejada e tecle ENTER: ");

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
			if (opcao != OPCAO_CONCLUIR_CADASTRO) {
				System.out.println("VOCÊ DIGITOU UMA OPÇÃO INVÁLIDA!");
			}
		}
	}

	private void inserir() {
		System.out.println("\nInserir novo registro de item de compra.\n");
		System.out.print(" - Código (Item Compra): ");
		long codigo = ler.nextLong();
		ler.nextLine(); // <------------------- para consumir a quebra-de-linha!

		// campo produto:
		Produto produto = null;
		do {
			System.out.print(" - Produto (codigo): ");
			long codigoProduto = ler.nextLong();
			ler.nextLine();
			produto = ArmazenamentoProduto.buscarProduto(new Produto(codigoProduto));
			if (produto == null) {
				System.out.println("PRODUTO NÃO CADASTRADO!");
			}
		} while (produto == null);

		// campo precoCompra:
		System.out.print(" - Preço de compra: ");
		double precoCompra = ler.nextDouble();

		// campo quantidade:
		System.out.print(" - Quantidade: ");
		int quantidade = ler.nextInt();
		ler.nextLine();

		ItemCompra novoItemCompra = new ItemCompra(codigo, compraReferencia, produto, precoCompra, quantidade);

		ArmazenamentoItemCompra.inserir(novoItemCompra);
	}

	protected void listar() {
		System.out.println("\nListagem de itens de compra registrados.\n");
		System.out.println("+--------+--------------------------------+------------+------------+");
		System.out.println("| Código | Produto                        | Preço      | Quantidade |");
		System.out.println("+--------+--------------------------------+------------+------------+");
		for (ItemCompra i : ArmazenamentoItemCompra.getLista()) {
			System.out.printf("| %6d | %-30s | %10.2f | %10d |\n", i.getCodigo(), i.getProduto().getNome(),
					i.getPrecoCompra(), i.getQuantidade());
		}
		System.out.println("+--------+--------------------------------+------------+------------+");
	}

	private void alterar() {
		System.out.println("\nAlterar registro de produto.\n");

		// obter o código do item a alterar
		System.out.print(" - Código: ");
		long codigo = ler.nextLong();
		ler.nextLine();

		// procurar o item para alterar na lista de produtos
		ItemCompra i = new ItemCompra(codigo);
		ItemCompra itemCompraParaAlterar = ArmazenamentoItemCompra.buscar(i);

		// caso nÃƒÂ£o encontre, exibir mensagem de erro ao usuÃƒÂ¡rio
		if (itemCompraParaAlterar == null) {
			System.out.println("NÃO HÁ PRODUTO CADASTRADO COM O CÓDIGO INFORMADO.");
			return;
		}

		// exibir produto
		System.out.printf("\n - Produto: %d - %s\n" + itemCompraParaAlterar.getProduto().getCodigo(),
				itemCompraParaAlterar.getProduto().getNome());
		// perguntar se quer alterar o nome
		System.out.print(" --> Alterar o produto? (s=sim/n=não) ");
		char opcaoProduto = ler.nextLine().charAt(0);

		Produto produto = itemCompraParaAlterar.getProduto();
		if (opcaoProduto == 's') {
			boolean produtoExistente = false;
			do {
				System.out.print(" - Novo produto: (código) ");
				long codigoProduto = ler.nextLong();
				Produto novoProduto = ArmazenamentoProduto.buscarProduto(new Produto(codigoProduto));
				if (novoProduto != null) {
					produtoExistente = true;
					produto = novoProduto;
				} else {
					System.out.println("NÃO HÁ PRODUTO CADASTRADO COM O CÓDIGO INFORMADO!");
				}
			} while (!produtoExistente);
		}

		// exibir preço
		System.out.printf("\n - Preço: %.2f\n", itemCompraParaAlterar.getPrecoCompra());
		// perguntar se quer alterar o preço
		System.out.print(" --> Alterar o preço? (s=sim/n=não) ");
		char opcaoPreco = ler.nextLine().charAt(0);

		double precoCompra = itemCompraParaAlterar.getPrecoCompra();
		if (opcaoPreco == 's') {
			System.out.print(" - Novo preço: ");
			precoCompra = ler.nextDouble();
			ler.nextLine();
		}

		// exibir quantidade
		System.out.printf("\n - Quantidade: %.2f\n", itemCompraParaAlterar.getQuantidade());
		// perguntar se quer alterar a quantidade
		System.out.print(" --> Alterar a quantidade? (s=sim/n=não) ");
		char opcaoQuantidade = ler.nextLine().charAt(0);

		int quantidade = itemCompraParaAlterar.getQuantidade();
		if (opcaoQuantidade == 's') {
			System.out.print(" - Nova quantidade: ");
			quantidade = ler.nextInt();
			ler.nextLine();
		}

		// confirmação final!!!
		System.out.println("\nConfirma alteração do item de compra?");
		System.out.printf(" - Código: %d\n", itemCompraParaAlterar.getCodigo());
		System.out.printf(" - Produto..: %d - %s\n", produto.getCodigo(), produto.getNome());
		System.out.printf(" - Preço.: %.2f\n", precoCompra);
		System.out.printf(" - Preço.: %d\n", quantidade);
		System.out.print(" --> (s=sim/n=não) ");
		char opcao = ler.nextLine().charAt(0);
		if (opcao == 's') {
			ItemCompra itemCompraAlterado = new ItemCompra(codigo, compraReferencia, produto, precoCompra, quantidade);
			ArmazenamentoItemCompra.alterar(itemCompraAlterado);
		}
	}

	private void excluir() {
		System.out.println("\nExcluir registro de item de compra.\n");

		// obter o código do item a excluir
		System.out.print(" - Código do item a excluir: ");
		long codigo = ler.nextLong();
		ler.nextLine();

		// buscar dados do produto para confirmação de exclusão
		ItemCompra parametroBusca = new ItemCompra(codigo);
		ItemCompra itemCompraExcluir = ArmazenamentoItemCompra.buscar(parametroBusca);

		if (itemCompraExcluir == null) {
			System.out.println("NÃO HÁ PRODUTO COMPRA COM O CÓDIGO INFORMADO.");
			return;
		}
		System.out.printf(" - Código: %d\n", itemCompraExcluir.getCodigo());
		System.out.printf(" - Produto..: %d - %s\n", itemCompraExcluir.getProduto().getCodigo(),
				itemCompraExcluir.getProduto().getNome());
		System.out.printf(" - Preço compra.: %.2f\n", itemCompraExcluir.getPrecoCompra());
		System.out.printf(" - Quantidade.: %d\n", itemCompraExcluir.getQuantidade());
		System.out.print(" --> (s=sim/n=não) ");
		char opcao = ler.nextLine().charAt(0);
		if (opcao == 's') {
			ArmazenamentoItemCompra.excluir(itemCompraExcluir);
		}
	}
}
