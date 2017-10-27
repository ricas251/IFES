package controleestoque.fronteiras;

import java.util.Scanner;

import controleestoque.Armazenamento.ArmazenamentoItemVenda;
import controleestoque.Armazenamento.ArmazenamentoProduto;
import controleestoque.Entidades.ItemVenda;
import controleestoque.Entidades.Produto;
import controleestoque.Entidades.Venda;

public class CadastroItemVenda {
	private static final int OPCAO_INSERIR = 1;
	private static final int OPCAO_LISTAR = 2;
	private static final int OPCAO_ALTERAR = 3;
	private static final int OPCAO_EXCLUIR = 4;
	private static final int OPCAO_CONCLUIR_CADASTRO = 5;

	private Scanner ler;

	private Venda vendaReferencia;

	public CadastroItemVenda(Venda v) {
		ArmazenamentoItemVenda.iniciarListaItemVenda();
		ArmazenamentoItemVenda.getLista().addAll(v.getItensVenda());
		vendaReferencia = v;
	}

	public void exibirMenu() {
		ler = new Scanner(System.in);

		int opcao = 0;
		while (opcao != OPCAO_CONCLUIR_CADASTRO) {
			System.out.println("\n\nOpções do cadastro de itens da venda:");
			System.out.println(" 1 - Inserir");
			System.out.println(" 2 - Listar");
			System.out.println(" 3 - Alterar");
			System.out.println(" 4 - Excluir");
			System.out.println(" 5 - Concluir cadastro de itens da venda");
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
		System.out.println("\nInserir novo registro de item de venda.\n");
		System.out.print(" - Código (Item Venda): ");
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
		System.out.print(" - Preço de venda: ");
		double precoVenda = ler.nextDouble();

		// campo quantidade:
		System.out.print(" - Quantidade: ");
		int quantidade = ler.nextInt();
		ler.nextLine();

		ItemVenda novoItemCompra = new ItemVenda(codigo, vendaReferencia, produto, precoVenda, quantidade);

		ArmazenamentoItemVenda.inserir(novoItemCompra);
	}

	protected void listar() {
		System.out.println("\nListagem de itens de venda registrados.\n");
		System.out.println("+--------+--------------------------------+------------+------------+");
		System.out.println("| Código | Produto                        | Preço      | Quantidade |");
		System.out.println("+--------+--------------------------------+------------+------------+");
		for (ItemVenda i : ArmazenamentoItemVenda.getLista()) {
			System.out.printf("| %6d | %-30s | %10.2f | %10d |\n", i.getCodigo(), i.getProduto().getNome(),
					i.getPrecoVenda(), i.getQuantidade());
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
		ItemVenda i = new ItemVenda(codigo);
		ItemVenda itemVendaParaAlterar = ArmazenamentoItemVenda.buscar(i);

		// caso nÃƒÂ£o encontre, exibir mensagem de erro ao usuÃƒÂ¡rio
		if (itemVendaParaAlterar == null) {
			System.out.println("NÃO HÁ PRODUTO CADASTRADO COM O CÓDIGO INFORMADO.");
			return;
		}

		// exibir produto
		System.out.printf("\n - Produto: %d - %s\n" + itemVendaParaAlterar.getProduto().getCodigo(),
				itemVendaParaAlterar.getProduto().getNome());
		// perguntar se quer alterar o nome
		System.out.print(" --> Alterar o produto? (s=sim/n=nÃ£o) ");
		char opcaoProduto = ler.nextLine().charAt(0);

		Produto produto = itemVendaParaAlterar.getProduto();
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
		System.out.printf("\n - Preço: %.2f\n", itemVendaParaAlterar.getPrecoVenda());
		// perguntar se quer alterar o preço
		System.out.print(" --> Alterar o preço? (s=sim/n=não) ");
		char opcaoPreco = ler.nextLine().charAt(0);

		double precoVenda = itemVendaParaAlterar.getPrecoVenda();
		if (opcaoPreco == 's') {
			System.out.print(" - Novo preço: ");
			precoVenda = ler.nextDouble();
			ler.nextLine();
		}

		// exibir quantidade
		System.out.printf("\n - Quantidade: %.2f\n", itemVendaParaAlterar.getQuantidade());
		// perguntar se quer alterar a quantidade
		System.out.print(" --> Alterar a quantidade? (s=sim/n=não) ");
		char opcaoQuantidade = ler.nextLine().charAt(0);

		int quantidade = itemVendaParaAlterar.getQuantidade();
		if (opcaoQuantidade == 's') {
			System.out.print(" - Nova quantidade: ");
			quantidade = ler.nextInt();
			ler.nextLine();
		}

		// confirmaÃƒÂ§ÃƒÂ£o final!!!
		System.out.println("\nConfirma alteração do item de venda?");
		System.out.printf(" - Código: %d\n", itemVendaParaAlterar.getCodigo());
		System.out.printf(" - Produto..: %d - %s\n", produto.getCodigo(), produto.getNome());
		System.out.printf(" - Preço.: %.2f\n", precoVenda);
		System.out.printf(" - Preço.: %d\n", quantidade);
		System.out.print(" --> (s=sim/n=não) ");
		char opcao = ler.nextLine().charAt(0);
		if (opcao == 's') {
			ItemVenda itemVendaAlterado = new ItemVenda(codigo, vendaReferencia, produto, precoVenda, quantidade);
			ArmazenamentoItemVenda.alterar(itemVendaAlterado);
		}
	}

	private void excluir() {
		System.out.println("\nExcluir registro de item de venda.\n");

		// obter o código do item a excluir
		System.out.print(" - Código do item a excluir: ");
		long codigo = ler.nextLong();
		ler.nextLine();

		// buscar dados do produto para confirmaÃƒÂ§ÃƒÂ£o de exclusÃƒÂ£o
		ItemVenda parametroBusca = new ItemVenda(codigo);
		ItemVenda itemVendaExcluir = ArmazenamentoItemVenda.buscar(parametroBusca);

		if (itemVendaExcluir == null) {
			System.out.println("NÃO HÁ PRODUTO VENDA COM O CÓDIGO INFORMADO.");
			return;
		}
		System.out.printf(" - Código: %d\n", itemVendaExcluir.getCodigo());
		System.out.printf(" - Produto..: %d - %s\n", itemVendaExcluir.getProduto().getCodigo(),
				itemVendaExcluir.getProduto().getNome());
		System.out.printf(" - Preço compra.: %.2f\n", itemVendaExcluir.getPrecoVenda());
		System.out.printf(" - Quantidade.: %d\n", itemVendaExcluir.getQuantidade());
		System.out.print(" --> (s=sim/n=não) ");
		char opcao = ler.nextLine().charAt(0);
		if (opcao == 's') {
			ArmazenamentoItemVenda.excluir(itemVendaExcluir);
		}
	}
}
