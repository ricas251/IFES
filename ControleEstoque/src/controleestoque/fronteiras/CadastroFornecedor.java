package controleestoque.fronteiras;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;

import controleestoque.Armazenamento.ArmazenamentoFornecedor;
import controleestoque.Entidades.Fornecedor;;

public class CadastroFornecedor {

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
			System.out.println("\n\nOpções do cadastro de fornecedores:");
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
		System.out.println("\nInserir novo registro de fornecedor.\n");
		System.out.print(" - Código............: ");
		long codigo = ler.nextLong();
		ler.nextLine(); // <------------------- para consumir a quebra-de-linha!
		System.out.print(" - Nome fantasia.....: ");
		String nomeFantasia = ler.nextLine();
		System.out.print(" - Razão social......: ");
		String razaoSocial = ler.nextLine();
		System.out.print(" - Endereço..........: ");
		String endereco = ler.nextLine();
		System.out.print(" - CNPJ..............: ");
		long cnpj = ler.nextLong();
		System.out.print(" - Inscrição estadual: ");
		long inscricaoEstadual = ler.nextLong();
		ler.nextLine(); // <------------------- para consumir a quebra-de-linha!
		System.out.print(" - Telefone..........: ");
		String telefone = ler.nextLine();
		System.out.print(" - Email.............: ");
		String email = ler.nextLine();

		Fornecedor novoFornecedor = new Fornecedor(codigo, nomeFantasia, razaoSocial, endereco, cnpj, inscricaoEstadual,
				telefone, email);
		ArmazenamentoFornecedor.inserirFornecedor(novoFornecedor);
	}

	private void listar() {
		System.out.println("\nListagem de fornecedores registrados.\n");
		System.out.println(
				"+--------+--------------------------------+--------------------+-----------+-------------------+");
		System.out.println(
				"| Código | Nome Fantasia                  | CNPJ               | Insc.Est. | Telefone          |");
		System.out.println(
				"+--------+--------------------------------+--------------------+-----------+-------------------+");
		for (Fornecedor f : ArmazenamentoFornecedor.getListaFornecedor()) {
			System.out.printf("| %6d | %-30s | %18s | %9d | %17s |\n", f.getCodigo(), f.getNomeFantasia(),
					formatarCnpj(f.getCnpj()), f.getInscricaoEstadual(), f.getTelefone());
		}
		System.out.println(
				"+--------+--------------------------------+--------------------+-----------+-------------------+");
	}

	public static String formatarCnpj(long cnpj) {
		String cnpjFormatado = "";

		// formatar o cnpj
		NumberFormat nf = new DecimalFormat("00000000000000");
		StringBuilder sb = new StringBuilder(nf.format(cnpj));
		sb.insert(2, '.');
		sb.insert(6, '.');
		sb.insert(10, '/');
		sb.insert(15, '-');
		cnpjFormatado = sb.toString();

		return cnpjFormatado;
	}

	private void alterar() {
		System.out.println("\nAlterar registro de produto.\n");

		// obter o código do produto a alterar
		System.out.print(" - Código: ");
		long codigo = ler.nextLong();
		ler.nextLine();

		// procurar o fornecedor para alterar na lista de produtos
		Fornecedor f = new Fornecedor(codigo);
		Fornecedor fornecedorParaAlterar = ArmazenamentoFornecedor.buscar(f);

		// caso nÃ£o encontre, exibir mensagem de erro ao usuÃ¡rio
		if (fornecedorParaAlterar == null) {
			System.out.println("NÃO HÁ FORNECEDOR CADASTRADO COM O CÓDIGO INFORMADO.");
			return;
		}

		// exibir nome
		System.out.println("\n - Nome Fantasia: " + fornecedorParaAlterar.getNomeFantasia());
		// perguntar se quer alterar o nome
		System.out.print(" --> Alterar o nome fantasia? (s=sim/n=não) ");
		char opcaoNome = ler.nextLine().charAt(0);

		String nomeFantasia = fornecedorParaAlterar.getNomeFantasia();
		if (opcaoNome == 's') {
			System.out.print(" - Novo nome fantasia: ");
			nomeFantasia = ler.nextLine();
		}

		System.out.print(" --> Alterar o razao social? (s=sim/n=não) ");
		char opcaoRazaoSocial = ler.nextLine().charAt(0);
		String razaoSocial = fornecedorParaAlterar.getRazaoSocial();
		if (opcaoRazaoSocial == 's') {
			System.out.print(" - Nova Razão Social: ");
			razaoSocial = ler.nextLine();
		}

		System.out.print(" --> Alterar o endereço? (s=sim/n=não) ");
		char opcaoEndereco = ler.nextLine().charAt(0);
		String endereco = fornecedorParaAlterar.getRazaoSocial();
		if (opcaoEndereco == 's') {
			System.out.print(" - Novo endereço: ");
			endereco = ler.nextLine();
		}

		System.out.print(" --> Alterar o CNPJ? (s=sim/n=não) ");
		char opcaoCNPJ = ler.nextLine().charAt(0);
		long cnpj = fornecedorParaAlterar.getCnpj();
		if (opcaoCNPJ == 's') {
			System.out.print(" - Novo CNPJ: ");
			cnpj = ler.nextLong();
		}

		System.out.print(" --> Alterar a Inscricao Estadual? (s=sim/n=não) ");
		char opcaoInscricaoEstadual = ler.nextLine().charAt(0);
		long inscricaoEstadual = fornecedorParaAlterar.getInscricaoEstadual();
		if (opcaoInscricaoEstadual == 's') {
			System.out.print(" - Nova inscrição estadual: ");
			inscricaoEstadual = ler.nextLong();
		}

		System.out.print(" --> Alterar o telefone? (s=sim/n=não) ");
		char opcaoTelefone = ler.nextLine().charAt(0);
		String telefone = fornecedorParaAlterar.getTelefone();
		if (opcaoTelefone == 's') {
			System.out.print(" - Novo telefone: ");
			telefone = ler.nextLine();
		}

		System.out.print(" --> Alterar o email? (s=sim/n=não) ");
		char opcaoEmail = ler.nextLine().charAt(0);
		String email = fornecedorParaAlterar.getTelefone();
		if (opcaoEmail == 's') {
			System.out.print(" - Novo email: ");
			email = ler.nextLine();
		}

		// confirmaÃ§Ã£o final!!!
		System.out.println("\nConfirmar alteração do fornecedor?");
		System.out.printf(" - Código..............: %d\n", fornecedorParaAlterar.getCodigo());
		System.out.printf(" - Nome Fantasia.......: %s\n", nomeFantasia);
		System.out.printf(" - Razão Social........: %s\n", razaoSocial);
		System.out.printf(" - Endereço............: %s\n", endereco);
		System.out.printf(" - CNPJ................: %n\n", cnpj);
		System.out.printf(" - Inscrição Estadual..: %n\n", inscricaoEstadual);
		System.out.printf(" - Telefone............: %s\n", telefone);
		System.out.printf(" - Email...............: %s\n", email);

		System.out.print(" --> (s=sim/n=nÃ£o) ");
		char opcao = ler.nextLine().charAt(0);
		if (opcao == 's') {
			Fornecedor fornecedorAlterado = new Fornecedor(codigo, nomeFantasia, razaoSocial, endereco, cnpj,
					inscricaoEstadual, telefone, email);
			ArmazenamentoFornecedor.alterar(fornecedorAlterado);
		}
	}

	private void excluir() {
		System.out.println("\nExcluir registro de fornecedor.\n");

		// obter o cÃ³digo do produto a excluir
		System.out.print(" - Código do fornecedor a excluir: ");
		long codigo = ler.nextLong();
		ler.nextLine();

		// buscar dados do produto para confirmaÃ§Ã£o de exclusÃ£o
		Fornecedor parametroBusca = new Fornecedor(codigo);
		Fornecedor fornecedorExcluir = ArmazenamentoFornecedor.buscar(parametroBusca);

		if (fornecedorExcluir == null) {
			System.out.println("NÃO HÁ PRODUTO CADASTRADO COM O CÓDIGO INFORMADO.");
			return;
		}

		System.out.println("\nConfirmar exclusão do fornecedor?");
		System.out.printf(" - Código...............: %d\n", fornecedorExcluir.getCodigo());
		System.out.printf(" - Nome Fantasia........: %s\n", fornecedorExcluir.getNomeFantasia());
		System.out.printf(" - Razão Social.........: %s\n", fornecedorExcluir.getRazaoSocial());
		System.out.printf(" - Endereço..: .........: %s\n", fornecedorExcluir.getEndereco());
		System.out.printf(" - CNPJ.................: %d\n", fornecedorExcluir.getCnpj());
		System.out.printf(" - Inscrição Estadual...: %d\n", fornecedorExcluir.getInscricaoEstadual());
		System.out.printf(" - Telefone.............: %s\n", fornecedorExcluir.getTelefone());
		System.out.printf(" - Email................: %s\n", fornecedorExcluir.getEmail());
		System.out.print("\n  --> Confirma exclusão? (s=sim/n=nÃ£o) ");

		char opcao = ler.nextLine().charAt(0);
		if (opcao == 's') {
			ArmazenamentoFornecedor.excluir(fornecedorExcluir);
		}
	}
}
