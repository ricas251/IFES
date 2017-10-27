package controleestoque.fronteiras;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import controleestoque.Armazenamento.ArmazenamentoCompra;
import controleestoque.Armazenamento.ArmazenamentoFornecedor;
import controleestoque.Armazenamento.ArmazenamentoFuncionario;
import controleestoque.Armazenamento.ArmazenamentoItemCompra;
import controleestoque.Entidades.Compra;
import controleestoque.Entidades.Comprador;
import controleestoque.Entidades.Fornecedor;
import controleestoque.Entidades.Funcionario;
import controleestoque.Entidades.ItemCompra;

public class CadastroCompra {

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
			System.out.println("\n\nOpçoes do cadastro de compra:");
			System.out.println(" 1 - Inserir");
			System.out.println(" 2 - Listar");
			System.out.println(" 3 - Alterar");
			System.out.println(" 4 - Excluir");
			System.out.println(" 5 - Voltar ao menu anterior");
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
			if (opcao != OPCAO_VOLTAR_MENU_ANTERIOR) {
				System.out.println("VOCÊ DIGITOU UMA OPÇÃO INVÁLIDA!");
			}
		}
	}

	protected void inserir() {
		// obter dados da compra
		System.out.println("\nInserir novo registro de compra.\n");
		System.out.print(" - Código: ");
		long codigo = ler.nextLong();
		ler.nextLine(); // <------------------- para consumir a quebra-de-linha!

		Date data = null;
		DateFormat df = DateFormat.getDateInstance();
		while (data == null) {
			System.out.println(" - Data: ");
			String stringData = ler.nextLine();
			try {
				data = df.parse(stringData);
			} catch (ParseException e) {
				System.out.println("DATA INVÁLIDA! DIGITE NOVAMENTE");
			}
		}

		Comprador comprador = null;
		while (comprador == null) {
			System.out.println(" -Comprador :(código)");
			long codigoComprador = ler.nextLong();
			ler.nextLine();
			Funcionario f = ArmazenamentoFuncionario.buscar(new Funcionario(codigoComprador));
			if (f instanceof Comprador) {
				comprador = (Comprador) f;
			} else if (comprador == null) {
				System.out.println("NÃO EXISTE COMPRADOR COM O CÓDIGO CADASTRADO");
			}

		}

		Fornecedor fornecedor = null;
		while (fornecedor == null) {
			System.out.println(" -Fornecedor :(código)");
			long codigoFornecedor = ler.nextLong();
			ler.nextLine();
			fornecedor = ArmazenamentoFornecedor.buscar(new Fornecedor(codigoFornecedor));
			if (fornecedor == null) {
				System.out.println("NÃO EXISTE COMPRADOR COM O CÓDIGO CADASTRADO");
			}

		}

		// passar dados compra item-compra
		Compra compra = new Compra(codigo, data, comprador, fornecedor);
		CadastroItemCompra cadastroItemCompra = new CadastroItemCompra(compra);
		cadastroItemCompra.exibirMenu();

		// lista com os items da compra esta no armazenamento item compra
		for (ItemCompra i : ArmazenamentoItemCompra.getLista()) {
			compra.inserirItemCompra(i);
		}

		// confirmação de cadastro:
		System.out.println("\nConfirmação de dados de compra");
		System.out.printf(" -Código: %d\n", compra.getCodigo());
		System.out.printf(" -Data: %s\n", df.format(compra.getData()));
		System.out.printf(" -Comprador: %s\n", compra.getComprador().getNome());
		System.out.printf(" -Fornecedor: %s\n", compra.getFornecedor().getNomeFantasia());
		System.out.printf(" -Valor Total: %s\n", compra.getValorTotal());
		cadastroItemCompra.listar();
		System.out.print(" --> Confirmar? (s=sim/n=não) ");
		char opcao = ler.nextLine().charAt(0);
		if (opcao == 's') {
			ArmazenamentoCompra.getInstance().inserir(compra);
		}
	}

	protected void listar() {
		System.out.println("\nListagem de compras registradas.\n");
		System.out.println("+--------+------------+-------------+--------------------------------+--------------------------------+");
		System.out.println("| Código | Data       | Valor total | Comprador                      | Fornecedor                     |");
		System.out.println("+--------+------------+-------------+--------------------------------+--------------------------------+");
		DateFormat df = DateFormat.getDateInstance();
		ArrayList<Compra> listaCompra =ArmazenamentoCompra.getInstance().getLista();
		for (Compra c : listaCompra) {
			String nomeComprador = c.getComprador().getNome();
			nomeComprador = nomeComprador.length() > 30 ? nomeComprador.substring(0, 30) : nomeComprador;
			String nomeFornecedor = c.getFornecedor().getNomeFantasia();
			nomeFornecedor = nomeFornecedor.length() > 30 ? nomeFornecedor.substring(0, 30) : nomeFornecedor;
			System.out.printf("| %6d | %-10s | %11.2f | %-30s | %-30s |\n", c.getCodigo(), df.format(c.getData()),
					c.getValorTotal(), nomeComprador, nomeFornecedor);
		}
		System.out.println("+--------+------------+-------------+--------------------------------+--------------------------------+");
	}

	protected void alterar() {
		DateFormat df = DateFormat.getDateInstance();
		System.out.println("\nAlterar registro de compra.\n");
		// obter o código da compra a alterar
		System.out.print(" - Código: ");
		long codigo = ler.nextLong();
		ler.nextLine();
		// buscar dados da compra para confirmação de exclusão
		Compra compraAlterar = (Compra) ArmazenamentoCompra.getInstance().buscar(new Compra(codigo));
		if (compraAlterar == null) {
			System.out.println("NÃO HÁ COMPRA CADASTRADO COM O CÓDIGO INFORMADO.");
			return;
		}
		if (compraAlterar != null) {
			char opcaoAlterarData, opcaoAlterarItensCompra, opcaoAlterarFornecedor, opcaoAlterarComprador;
			System.out.println(
					"+--------+------------+-------------+--------------------------------+--------------------------------+");
			System.out.println(
					"| Código | Data       | Valor total | Comprador                      | Fornecedor                     |");
			System.out.println(
					"+--------+------------+-------------+--------------------------------+--------------------------------+");
			System.out.printf("| %6d | %-10s | %11.2f | %-30s | %-30s |\n", compraAlterar.getCodigo(),
					df.format(compraAlterar.getData()), compraAlterar.getValorTotal(), compraAlterar.getComprador(),
					compraAlterar.getComprador().getNome(), compraAlterar.getFornecedor().getNomeFantasia());

			Comprador comprador = compraAlterar.getComprador();
			Date data = compraAlterar.getData();
			Fornecedor fornecedor = compraAlterar.getFornecedor();

			System.out.println("Alterar a Data da Compra: (s = sim / n = não)");
			opcaoAlterarData = ler.nextLine().charAt(0);
			if (opcaoAlterarData == 's') {
				data = null;
				while (data == null) {
					System.out.println(" - Data: ");
					String stringData = ler.nextLine();
					try {
						data = df.parse(stringData);
					} catch (ParseException e) {
						System.out.println("DATA INVÁLIDA! DIGITE NOVAMENTE");
					}
				}
			}
			System.out.println("Alterar o comprador da Compra:(s = sim / n = não)");
			opcaoAlterarComprador = ler.nextLine().charAt(0);
			if (opcaoAlterarComprador == 's') {
				comprador = null;
				while (comprador == null) {
					System.out.println(" -Comprador :(código)");
					long codigoComprador = ler.nextLong();
					ler.nextLine();
					Funcionario f = ArmazenamentoFuncionario.buscar(new Funcionario(codigoComprador));
					if (f instanceof Comprador) {
						comprador = (Comprador) f;
					} else if (comprador == null) {
						System.out.println("NÃO EXISTE COMPRADOR COM O CÓDIGO CADASTRADO");
					}
				}
			}

			System.out.println("Alterar o fornecedor da Compra:(s = sim / n = não)");
			opcaoAlterarFornecedor = ler.nextLine().charAt(0);
			if (opcaoAlterarFornecedor == 's') {
				fornecedor = null;
				while (fornecedor == null) {
					System.out.println(" -Fornecedor :(código)");
					long codigoFornecedor = ler.nextLong();
					ler.nextLine();
					fornecedor = ArmazenamentoFornecedor.buscar(new Fornecedor(codigoFornecedor));
					if (fornecedor == null) {
						System.out.println("NÃO EXISTE COMPRADOR COM O CÓDIGO CADASTRADO");
					}
				}
			}

			Compra compra = new Compra(codigo, data, comprador, fornecedor);
			System.out.println("Alterar os itens da Compra:(s = sim / n = não)");
			opcaoAlterarItensCompra = ler.nextLine().charAt(0);
			if (opcaoAlterarItensCompra == 's') {
				CadastroItemCompra itemCompraAlterar = new CadastroItemCompra(compra);
				itemCompraAlterar.exibirMenu();

			}

			for (ItemCompra i : ArmazenamentoItemCompra.getLista()) {
				;
				compra.inserirItemCompra(i);
			}
		}
	}

	private void excluir() {
		System.out.println("\nExcluir registro de compra.\n");

		// obter o código do compra a excluir
		System.out.print(" - Código da compra a excluir: ");
		long codigo = ler.nextLong();
		ler.nextLine();

		// buscar dados da compra para confirmação de exclusão
		Compra compraExcluir = (Compra) ArmazenamentoCompra.getInstance().buscar(new Compra(codigo));

		if (compraExcluir == null) {
			System.out.println("NÃO HÁ COMPRA CADASTRADO COM O CÓDIGO INFORMADO.");
			return;
		}

		if (compraExcluir != null) {
			System.out.println(
					"+--------+------------+-------------+--------------------------------+--------------------------------+");
			System.out.println(
					"| Código | Data       | Valor total | Comprador                      | Fornecedor                     |");
			System.out.println(
					"+--------+------------+-------------+--------------------------------+--------------------------------+");
			DateFormat df = DateFormat.getDateInstance();
			System.out.printf("| %6d | %-10s | %11.2f | %-30s | %-30s |\n", compraExcluir.getCodigo(),
					df.format(compraExcluir.getData()), compraExcluir.getValorTotal(), compraExcluir.getComprador(),
					compraExcluir.getComprador(), compraExcluir.getFornecedor());

			System.out.print("\n  --> Confirma exclusão? (s=sim/n=não) ");

			char opcao = ler.nextLine().charAt(0);
			if (opcao == 's') {
				ArmazenamentoCompra.getInstance().excluir(compraExcluir);
			}
		}
	}
}
