package controleestoque.fronteiras;

import java.util.Scanner;

import controleestoque.Armazenamento.ArmazenamentoFuncionario;
import controleestoque.Entidades.Comprador;
import controleestoque.Entidades.Funcionario;
import controleestoque.Entidades.Vendedor;

public class CadastroFuncionario {
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
			System.out.println("\n\nOpções do cadastro de funcionarios:");
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
				System.out.println("VOCÊ DIGITOU UMA OPÇÃO INVÁLIDA!");
			}
		}
	}

	private void inserir() {
		System.out.println("\nInserir novo registro de funcionario.\n");

		System.out.print(" - Código: ");
		long codigo = ler.nextLong();
		ler.nextLine(); // <------------------- para consumir a quebra-de-linha!

		System.out.print(" - Nome: ");
		String nome = ler.nextLine();
		System.out.print(" - CPF: ");
		long cpf = ler.nextLong();
		ler.nextLine();
		System.out.print(" - Endereço: ");
		String endereco = ler.nextLine();
		System.out.print(" - telefone: ");
		String telefone = ler.nextLine();
		System.out.print(" - email: ");
		String email = ler.nextLine();
		Funcionario novoFuncionario = null;

		System.out.print(" - Comprador (C) ou vendedor (V)? ");
		char tipoFuncionario = ler.nextLine().toUpperCase().charAt(0);

		switch (tipoFuncionario) {
		case 'C':
			novoFuncionario = new Comprador(codigo, nome, cpf, endereco, telefone, email);

		case 'V':
			novoFuncionario = new Vendedor(codigo, nome, cpf, endereco, telefone, email);
			break;

		default:
			System.out.println("OPÇÃO DE TIPO DE FUNCIOARIO INVÁLIDO!");
			break;
		}

		if (novoFuncionario != null) {
			ArmazenamentoFuncionario.inserir(novoFuncionario);
		}

	}

	private void listar() {
		System.out.println("\nListagem de funcionarios registrados.\n");
		System.out.println(
				"+--------+--------------------------------+--------------------+------------+-------------------+");
		System.out.println(
				"| Código | Nome                           | CPF                | Endereço   | Cargo             |");
		System.out.println(
				"+--------+--------------------------------+--------------------+------------+-------------------+");
		for (Funcionario func : ArmazenamentoFuncionario.getListaFuncionario()) {
			String cargo = "";
			if (func instanceof Vendedor) {
				cargo = "Vendedor";
			} else {
				cargo = "Comprador";
			}

			System.out.printf("| %6d | %-30s | %18d | %9s | %-17s |\n", func.getCodigo(), func.getNome(), func.getCpf(),
					func.getEndereco().substring(0, 10), cargo);
		}
		System.out.println(
				"+--------+--------------------------------+--------------------+------------+-------------------+");
	}

	private void alterar() {
		System.out.println("\nAlterar registro de produto.\n");

		// obter o código do produto a alterar
		System.out.print(" - Código: ");
		long codigo = ler.nextLong();
		ler.nextLine();

		// procurar o fornecedor para alterar na lista de produtos
		Funcionario f = new Funcionario(codigo);
		Funcionario funcionarioParaAlterar = ArmazenamentoFuncionario.buscar(f);

		// caso nÃ£o encontre, exibir mensagem de erro ao usuÃ¡rio
		if (funcionarioParaAlterar == null) {
			System.out.println("NÃO HÁ FORNECEDOR CADASTRADO COM O CÓDIGO INFORMADO.");
			return;
		}

		// exibir nome
		System.out.println("\n - Nome " + funcionarioParaAlterar.getNome());
		// perguntar se quer alterar o nome
		System.out.print(" --> Alterar o nome? (s=sim/n=não) ");
		char opcaoNome = ler.nextLine().charAt(0);

		String nome = funcionarioParaAlterar.getNome();
		if (opcaoNome == 's') {
			System.out.print(" - Novo nome : ");
			nome = ler.nextLine();
		}

		System.out.print(" --> Alterar o CPF? (s=sim/n=não) ");
		char opcaoCpf = ler.nextLine().charAt(0);
		long cpf = funcionarioParaAlterar.getCpf();
		if (opcaoCpf == 's') {
			System.out.print(" - Novo CPF: ");
			cpf = ler.nextLong();
			ler.nextLine();
		}

		System.out.print(" --> Alterar o endereço? (s=sim/n=não) ");
		char opcaoEndereco = ler.nextLine().charAt(0);
		String endereco = funcionarioParaAlterar.getEndereco();
		if (opcaoEndereco == 's') {
			System.out.print(" - Novo endereço: ");
			endereco = ler.nextLine();
		}

		System.out.print(" --> Alterar o telefone? (s=sim/n=não) ");
		char opcaoTelefone = ler.nextLine().charAt(0);
		String telefone = funcionarioParaAlterar.getTelefone();
		if (opcaoTelefone == 's') {
			System.out.print(" - Novo telefone: ");
			telefone = ler.nextLine();
		}

		System.out.print(" --> Alterar o email? (s=sim/n=não) ");
		char opcaoEmail = ler.nextLine().charAt(0);
		String email = funcionarioParaAlterar.getTelefone();
		if (opcaoEmail == 's') {
			System.out.print(" - Novo email: ");
			email = ler.nextLine();
		}

		// confirmaÃ§Ã£o final!!!
		System.out.println("\nConfirmar alteração do fornecedor?");
		System.out.printf(" - Código..............: %d\n", funcionarioParaAlterar.getCodigo());
		System.out.printf(" - Nome ...............: %s\n", nome);
		System.out.printf(" - CPF.................: %s\n", cpf);
		System.out.printf(" - Endereço............: %s\n", endereco);
		System.out.printf(" - Telefone............: %s\n", telefone);
		System.out.printf(" - Email...............: %s\n", email);

		System.out.print(" --> (s=sim/n=nÃ£o) ");
		char opcao = ler.nextLine().charAt(0);
		if (opcao == 's') {
			Funcionario funcionarioAlterado = null;
			if (funcionarioParaAlterar instanceof Comprador) {
				funcionarioAlterado = new Comprador(codigo, nome, cpf, endereco, telefone, email);
			} else {
				funcionarioAlterado = new Vendedor(codigo, nome, cpf, endereco, telefone, email);
			}
			ArmazenamentoFuncionario.alterar(funcionarioAlterado);
		}
	}

	private void excluir() {
		System.out.println("\nExcluir registro de fornecedor.\n");

		// obter o cÃ³digo do produto a excluir
		System.out.print(" - Código do fornecedor a excluir: ");
		long codigo = ler.nextLong();
		ler.nextLine();

		// buscar dados do produto para confirmaÃ§Ã£o de exclusÃ£o
		Funcionario parametroBusca = new Funcionario(codigo);
		Funcionario funcionarioExcluir = ArmazenamentoFuncionario.buscar(parametroBusca);

		if (funcionarioExcluir == null) {
			System.out.println("NÃO HÁ PRODUTO CADASTRADO COM O CÓDIGO INFORMADO.");
			return;
		}

		System.out.println("\nConfirmar alteração do fornecedor?");
		System.out.printf(" - Código..............: %d\n", funcionarioExcluir.getCodigo());
		System.out.printf(" - Nome ...............: %s\n", funcionarioExcluir.getNome());
		System.out.printf(" - CPF.................: %s\n", funcionarioExcluir.getCpf());
		System.out.printf(" - Endereço............: %s\n", funcionarioExcluir.getEndereco());
		System.out.printf(" - Telefone............: %s\n", funcionarioExcluir.getTelefone());
		System.out.printf(" - Email...............: %s\n", funcionarioExcluir.getTelefone());

		char opcao = ler.nextLine().charAt(0);
		if (opcao == 's') {
			ArmazenamentoFuncionario.excluir(funcionarioExcluir);
		}
	}
}
