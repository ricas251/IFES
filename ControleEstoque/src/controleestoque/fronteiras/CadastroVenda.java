package controleestoque.fronteiras;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

import controleestoque.Armazenamento.ArmazenamentoCliente;
import controleestoque.Armazenamento.ArmazenamentoFuncionario;
import controleestoque.Armazenamento.ArmazenamentoItemVenda;
import controleestoque.Armazenamento.ArmazenamentoVenda;
import controleestoque.Entidades.Cliente;
import controleestoque.Entidades.ClientePessoaFisica;
import controleestoque.Entidades.ClientePessoaJuridica;
import controleestoque.Entidades.Funcionario;
import controleestoque.Entidades.ItemVenda;
import controleestoque.Entidades.Venda;
import controleestoque.Entidades.Vendedor;

public class CadastroVenda {
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
			System.out.println("\n\nOp�oes do cadastro de venda:");
			System.out.println(" 1 - Inserir");
			System.out.println(" 2 - Listar");
			System.out.println(" 3 - Alterar");
			System.out.println(" 4 - Excluir");
			System.out.println(" 5 - Voltar ao menu anterior");
			System.out.print("---> Digite o n�mero da op��o desejada e tecle ENTER: ");

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
				System.out.println("VOC� DIGITOU UMA OP��O INV�LIDA!");
			}
		}
	}

	private void inserir() {
		// obter dados da compra
		System.out.println("\nInserir novo registro de VENDA.\n");
		System.out.print(" - C�digo: ");
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
				System.out.println("DATA INV�LIDA! DIGITE NOVAMENTE");
			}
		}

		Vendedor vendedor = null;
		while (vendedor == null) {
			System.out.println(" -Comprador :(c�digo)");
			long codigoVendedor = ler.nextLong();
			ler.nextLine();
			Funcionario f = ArmazenamentoFuncionario.buscar(new Funcionario(codigoVendedor));
			if (f instanceof Vendedor) {
				vendedor = (Vendedor) f;
			} else if (vendedor == null) {
				System.out.println("N�O EXISTE VENDEDOR COM O C�DIGO CADASTRADO");
			}

		}

		Cliente cliente = null;
		Cliente clienteGeral = null;
		while (cliente == null) {
			System.out.println(" -Cliente :(c�digo)");
			long codigoCliente = ler.nextLong();
			ler.nextLine();
			clienteGeral = ArmazenamentoCliente.buscar(new Cliente(codigoCliente));
			if (clienteGeral instanceof ClientePessoaFisica) {
				cliente = (ClientePessoaFisica) clienteGeral;
			}
			if (clienteGeral instanceof ClientePessoaJuridica) {
				cliente = (ClientePessoaJuridica) clienteGeral;
			}
			if (cliente == null) {
				System.out.println("N�O EXISTE CLIENTE COM O C�DIGO CADASTRADO");
			}

		}
		long valorTotal = 0;
		// passar dados compra item-venda
		Venda venda = new Venda(codigo, data, valorTotal, vendedor, cliente);
		CadastroItemVenda cadastroItemVenda = new CadastroItemVenda(venda);
		cadastroItemVenda.exibirMenu();

		// lista com os items da compra esta no armazenamento item venda
		for (ItemVenda i : ArmazenamentoItemVenda.getLista()) {
			venda.inserirItemVenda(i);
		}

		// confirma��o de cadastro:
		System.out.println("\nConfirma��o de dados de compra");
		System.out.printf(" -C�digo: %d\n", venda.getCodigo());
		System.out.printf(" -Data: %s\n", df.format(venda.getData()));
		System.out.printf(" -Vendedor: %s\n", venda.getVendedor().getNome());
		if (cliente instanceof ClientePessoaFisica) {
			System.out.printf(" -Cliente: %s\n", ((ClientePessoaFisica) cliente).getNome());
		}
		if (cliente instanceof ClientePessoaJuridica) {
			System.out.printf(" -Cliente: %s\n", ((ClientePessoaJuridica) cliente).getNomeFantasia());
		}
		System.out.printf(" -Valor Total: %s\n", venda.getValorTotal());
		cadastroItemVenda.listar();
		System.out.print(" --> Confirmar? (s=sim/n=n�o) ");
		char opcao = ler.nextLine().charAt(0);
		if (opcao == 's') {
			ArmazenamentoVenda.inserir(venda);
		}
	}

	private void listar() {
		System.out.println("\nListagem de compras registradas.\n");
		System.out.println(
				"+--------+------------+-------------+--------------------------------+--------------------------------+");
		System.out.println(
				"| C�digo | Data       | Valor total | Vendedor                       | Cliente                        |");
		System.out.println(
				"+--------+------------+-------------+--------------------------------+--------------------------------+");
		DateFormat df = DateFormat.getDateInstance();
		for (Venda v : ArmazenamentoVenda.getListaVenda()) {
			String nomeVendedor = v.getVendedor().getNome();
			nomeVendedor = nomeVendedor.length() > 30 ? nomeVendedor.substring(0, 30) : nomeVendedor;
			Cliente cliente = v.getCliente();
			String nomeCliente = "";
			if (cliente instanceof ClientePessoaFisica) {
				nomeCliente = ((ClientePessoaFisica) v.getCliente()).getNome();
			}
			if (cliente instanceof ClientePessoaJuridica) {
				nomeCliente = ((ClientePessoaJuridica) v.getCliente()).getNomeFantasia();
			}
			nomeCliente = nomeCliente.length() > 30 ? nomeCliente.substring(0, 30) : nomeCliente;
			System.out.printf("| %6d | %-10s | %11.2f | %-30s | %-30s |\n", v.getCodigo(), df.format(v.getData()),
					v.getValorTotal(), nomeVendedor, nomeCliente);
		}
		System.out.println(
				"+--------+------------+-------------+--------------------------------+--------------------------------+");
	}

	private void alterar() {
		DateFormat df = DateFormat.getDateInstance();
		System.out.println("\nAlterar registro de compra.\n");
		// obter o c�digo da compra a alterar
		System.out.print(" - C�digo: ");
		long codigo = ler.nextLong();
		ler.nextLine();
		// buscar dados da compra para confirma��o de exclus�o
		Venda parametroBusca = new Venda(codigo);
		Venda vendaAlterar = ArmazenamentoVenda.buscar(parametroBusca);
		if (vendaAlterar == null) {
			System.out.println("N�O H� VENDA CADASTRADO COM O C�DIGO INFORMADO.");
			return;
		}
		if (vendaAlterar != null) {
			char opcaoAlterarData, opcaoAlterarItensCompra, opcaoAlterarCliente, opcaoAlterarVendedor;
			System.out.println(
					"+--------+------------+-------------+--------------------------------+--------------------------------+");
			System.out.println(
					"| C�digo | Data       | Valor total | Vendedor                       | Cliente                        |");
			System.out.println(
					"+--------+------------+-------------+--------------------------------+--------------------------------+");
			if (vendaAlterar.getCliente() instanceof ClientePessoaFisica) {
				System.out.printf("| %6d | %-10s | %11.2f | %-30s | %-30s |\n", vendaAlterar.getCodigo(),
						df.format(vendaAlterar.getData()), vendaAlterar.getValorTotal(),
						vendaAlterar.getVendedor().getNome(),
						((ClientePessoaFisica) vendaAlterar.getCliente()).getNome());
			}
			if (vendaAlterar.getCliente() instanceof ClientePessoaJuridica) {
				System.out.printf("| %6d | %-10s | %11.2f | %-30s | %-30s |\n", vendaAlterar.getCodigo(),
						df.format(vendaAlterar.getData()), vendaAlterar.getValorTotal(),
						vendaAlterar.getVendedor().getNome(),
						((ClientePessoaJuridica) vendaAlterar.getCliente()).getNomeFantasia());
			}

			Vendedor vendedor = vendaAlterar.getVendedor();
			Date data = vendaAlterar.getData();
			Cliente cliente = vendaAlterar.getCliente();
			double valorTotal = vendaAlterar.getValorTotal();

			System.out.println("Alterar a Data da Venda: (s = sim / n = n�o)");
			opcaoAlterarData = ler.nextLine().charAt(0);
			if (opcaoAlterarData == 's') {
				data = null;
				while (data == null) {
					System.out.println(" - Data: ");
					String stringData = ler.nextLine();
					try {
						data = df.parse(stringData);
					} catch (ParseException e) {
						System.out.println("DATA INV�LIDA! DIGITE NOVAMENTE");
					}
				}
			}
			System.out.println("Alterar o vendedor da Venda:(s = sim / n = n�o)");
			opcaoAlterarVendedor = ler.nextLine().charAt(0);
			if (opcaoAlterarVendedor == 's') {
				vendedor = null;
				while (vendedor == null) {
					System.out.println(" -Vendedor :(c�digo)");
					long codigoVendedor = ler.nextLong();
					ler.nextLine();
					Funcionario f = ArmazenamentoFuncionario.buscar(new Funcionario(codigoVendedor));
					if (f instanceof Vendedor) {
						vendedor = (Vendedor) f;
					} else if (vendedor == null) {
						System.out.println("N�O EXISTE VENDEDOR COM O C�DIGO CADASTRADO");
					}
				}
			}

			System.out.println("Alterar o cliente da Venda:(s = sim / n = n�o)");
			opcaoAlterarCliente = ler.nextLine().charAt(0);
			if (opcaoAlterarCliente == 's') {
				cliente = null;
				while (cliente == null) {
					System.out.println(" -Cliente :(c�digo)");
					long codigoCliente = ler.nextLong();
					ler.nextLine();
					cliente = ArmazenamentoCliente.buscar(new Cliente(codigoCliente));
					if (cliente == null) {
						System.out.println("N�O EXISTE CLIENTE COM O C�DIGO CADASTRADO");
					}
				}
			}
			Venda venda = new Venda(codigo, data, valorTotal, vendedor, cliente);
			System.out.println("Alterar os itens da Compra:(s = sim / n = n�o)");
			opcaoAlterarItensCompra = ler.nextLine().charAt(0);
			if (opcaoAlterarItensCompra == 's') {
				CadastroItemVenda itemVendaAlterar = new CadastroItemVenda(venda);
				itemVendaAlterar.exibirMenu();

			}

			for (ItemVenda i : ArmazenamentoItemVenda.getLista()) {
				;
				venda.inserirItemVenda(i);
			}
		}
	}

	private void excluir() {
		DateFormat df = DateFormat.getDateInstance();
		System.out.println("\nExcluir registro de venda.\n");

		// obter o c�digo do venda a excluir
		System.out.print(" - C�digo da venda a excluir: ");
		long codigo = ler.nextLong();
		ler.nextLine();

		// buscar dados da compra para confirma��o de exclus�o
		Venda parametroBusca = new Venda(codigo);
		Venda vendaExcluir = ArmazenamentoVenda.buscar(parametroBusca);

		if (vendaExcluir == null) {
			System.out.println("N�O H� VENDA CADASTRADO COM O C�DIGO INFORMADO.");
			return;
		}

		if (vendaExcluir != null) {
			System.out.println(
					"+--------+------------+-------------+--------------------------------+--------------------------------+");
			System.out.println(
					"| C�digo | Data       | Valor total | Vendedor                       | Cliente                        |");
			System.out.println(
					"+--------+------------+-------------+--------------------------------+--------------------------------+");
			if (vendaExcluir.getCliente() instanceof ClientePessoaFisica) {
				System.out.printf("| %6d | %-10s | %11.2f | %-30s | %-30s |\n", vendaExcluir.getCodigo(),
						df.format(vendaExcluir.getData()), vendaExcluir.getValorTotal(),
						vendaExcluir.getVendedor().getNome(),
						((ClientePessoaFisica) vendaExcluir.getCliente()).getNome());
			}
			if (vendaExcluir.getCliente() instanceof ClientePessoaJuridica) {
				System.out.printf("| %6d | %-10s | %11.2f | %-30s | %-30s |\n", vendaExcluir.getCodigo(),
						df.format(vendaExcluir.getData()), vendaExcluir.getValorTotal(),
						vendaExcluir.getVendedor().getNome(),
						((ClientePessoaJuridica) vendaExcluir.getCliente()).getNomeFantasia());
			}

			System.out.print("\n  --> Confirma exclus�o? (s=sim/n=n�o) ");

			char opcao = ler.nextLine().charAt(0);
			if (opcao == 's') {
				ArmazenamentoVenda.excuir(vendaExcluir);
			}
		}
	}
}
