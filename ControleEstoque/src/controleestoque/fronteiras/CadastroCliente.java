package controleestoque.fronteiras;

import controleestoque.Armazenamento.ArmazenamentoCliente;
import controleestoque.Entidades.Cliente;
import controleestoque.Entidades.ClientePessoaFisica;
import controleestoque.Entidades.ClientePessoaJuridica;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class CadastroCliente extends Cadastro {

	private Scanner ler = new Scanner(System.in);

	@Override
	protected String obterMensagemSairMenu() {
		return "Voltar ao Menu Principal";

	}

	@Override
	protected String obterTituloMenu() {
		return "Opções de cadastro de Cliente";

	}

	@Override
	protected void inserir() {
		System.out.println("\nInserir novo registro de cliente.\n");

		System.out.print(" - Código: ");
		long codigo = ler.nextLong();
		ler.nextLine(); // <------------------- para consumir a quebra-de-linha!

		// campos para cliente pessoa fÃƒÂ­sica
		String nome = "";
		Date dataNascimento = Calendar.getInstance().getTime(); // <-- hoje
		char sexo = '.';
		long cpf = 0;

		// campos para cliente pessoa jurÃƒÂ­dica
		String nomeFantasia = "";
		String razaoSocial = "";
		long cnpj = 0;
		long inscricaoEstadual = 0;

		// obtÃƒÂ©m os dados especÃƒÂ­ficos
		char tipoPessoa;
		boolean tipoPessoaValido;
		do {
			System.out.print(" - Pessoa física (F) ou juríica (J)? ");
			tipoPessoa = ler.nextLine().toUpperCase().charAt(0);

			tipoPessoaValido = true;
			switch (tipoPessoa) {
			case 'F':
				System.out.print(" - Nome: ");
				nome = ler.nextLine();

				boolean dataValida;
				do {
					System.out.print(" - Data de nascimento (dd/mm/aaaa): ");
					String data = ler.nextLine();
					try {
						dataNascimento = DateFormat.getDateInstance().parse(data);
						dataValida = true;
					} catch (ParseException e) {
						System.out.println("VOCÊ  DIGITOU UMA DATA INVÁLIDA!");
						dataValida = false;
					}
				} while (!dataValida);

				System.out.print(" - Sexo (F=feminino;M=masculino): ");
				sexo = ler.nextLine().toUpperCase().charAt(0);

				System.out.print(" - CPF (somente os números): ");
				cpf = ler.nextLong();
				ler.nextLine();

				break;
			case 'J':
				System.out.print(" - Nome fantasia: ");
				nomeFantasia = ler.nextLine();

				System.out.print(" - RazÃ£o social: ");
				razaoSocial = ler.nextLine();

				System.out.print(" - CNPJ (somente os nÃºmeros): ");
				cnpj = ler.nextLong();
				ler.nextLine();

				System.out.print(" - InscriÃ§Ã£o estadual (somente os nÃºmeros): ");
				inscricaoEstadual = ler.nextLong();
				ler.nextLine();

				break;

			default:
				tipoPessoaValido = false;
				System.out.println("OPÃ‡ÃƒO DE TIPO DE PESSOA INVÃ�LIDA!");
			}
		} while (!tipoPessoaValido);

		System.out.print(" - EndereÃ§o: ");
		String endereco = ler.nextLine();

		System.out.print(" - Telefone: ");
		String telefone = ler.nextLine();

		System.out.print(" - Email: ");
		String email = ler.nextLine();

		Cliente novoCliente = null;
		switch (tipoPessoa) {
		case 'F':
			novoCliente = new ClientePessoaFisica(codigo, endereco, telefone, email, cpf, sexo, nome, dataNascimento);
			break;
		case 'J':
			novoCliente = new ClientePessoaJuridica(codigo, endereco, telefone, email, cnpj, inscricaoEstadual,
					nomeFantasia, razaoSocial);
			break;
		}
		if (novoCliente != null) {
			ArmazenamentoCliente.inserir(novoCliente);
		}
	}

	@Override
	protected void listar() {
		System.out.println("\nListagem de clientes registrados.\n");
		System.out.println(
				"+--------+--------------------------------+-----------------+--------------------+-----------------+");
		System.out.println(
				"| Código | Nome/Nome fantasia             | Física/Jurídica | CPF/CNPJ           | Telefone        |");
		System.out.println(
				"+--------+--------------------------------+-----------------+--------------------+-----------------+");
		for (Cliente c : ArmazenamentoCliente.getLista()) {
			if (c instanceof ClientePessoaFisica) {
				ClientePessoaFisica cpf = (ClientePessoaFisica) c;
				System.out.printf("| %6d | %-30s | %-15s | %18s | %15s |\n", cpf.getCodigo(), cpf.getNome(), "Física",
						cpf.getCpf(), cpf.getTelefone());
			} else if (c instanceof ClientePessoaJuridica) {
				ClientePessoaJuridica cnpj = (ClientePessoaJuridica) c;
				System.out.printf("| %6d | %-30s | %-15s | %18s | %15s |\n", cnpj.getCodigo(), cnpj.getNomeFantasia(),
						"Jurídica", CadastroFornecedor.formatarCnpj(cnpj.getCnpj()), cnpj.getTelefone());
			}
		}
		System.out.println(
				"+--------+--------------------------------+-----------------+--------------------+-----------------+");
	}

	@Override
	protected void alterar() {
		System.out.println("\nAlterar registro de cliente.\n");

		// obter o cÃ³digo do cliente a alterar
		System.out.print(" - CÃ³digo: ");
		long codigo = ler.nextLong();
		ler.nextLine();

		// procurar o cliente para alterar na lista de clientes
		Cliente c = new Cliente(codigo);
		Cliente clienteParaAlterar = ArmazenamentoCliente.buscar(c);

		// caso nÃ£o encontre, exibir mensagem de erro ao usuÃ¡rio
		if (clienteParaAlterar == null) {
			System.out.println("NÃƒO HÃ� CLIENTE CADASTRADO COM O CÃ“DIGO INFORMADO.");
			return;
		}

		char opcaoNome, opcaoDataNascimento, opcaoSexo, opcaoCpf, opcaoNomeFantasia, opcaoRazaoSocial, opcaoCnpj,
				opcaoInscricaoEstadual, opcaoEndereco, opcaoTelefone, opcaoEmail;
		String nome = "", nomeFantasia = "", razaoSocial = "", endereco = "", telefone = "", email = "";
		long cpf = 0, cnpj = 0, inscricaoEstadual = 0;
		char sexo = ' ';
		Date dataNascimento = Calendar.getInstance().getTime();

		// se cliente for pessoa fisica:
		if (clienteParaAlterar instanceof ClientePessoaFisica) {
			// tratar o cliente como pessoa fisica
			ClientePessoaFisica clientePF = (ClientePessoaFisica) clienteParaAlterar;

			// exibir nome
			System.out.println("\n - Nome: " + clientePF.getNome());
			// perguntar se quer alterar o nome
			System.out.print(" --> Alterar o nome? (s=sim/n=nÃ£o) ");
			opcaoNome = ler.nextLine().charAt(0);

			nome = clientePF.getNome();
			if (opcaoNome == 's') {
				System.out.print(" - Novo nome: ");
				nome = ler.nextLine();
			}

			// exibir data de nascimento
			DateFormat df = DateFormat.getDateInstance();
			String dataFormatada = df.format(clientePF.getDataNascimento());
			System.out.println("\n - Data de nascimento: " + dataFormatada);
			// perguntar se quer alterar a data de nascimento
			System.out.print(" --> Alterar a data de nascimento? (s=sim/n=nÃ£o) ");
			opcaoDataNascimento = ler.nextLine().charAt(0);

			dataNascimento = clientePF.getDataNascimento();
			if (opcaoDataNascimento == 's') {
				boolean dataValida;
				do {
					System.out.print(" - Nova data de nascimento (dd/mm/aaaa): ");
					String data = ler.nextLine();
					try {
						dataNascimento = DateFormat.getDateInstance().parse(data);
						dataValida = true;
					} catch (ParseException e) {
						System.out.println("VOCÃŠ DIGITOU UMA DATA INVÃ�LIDA!");
						dataValida = false;
					}
				} while (!dataValida);
			}

			// exibir sexo
			System.out.println("\n - Sexo: " + clientePF.getSexo());
			// perguntar se quer alterar o sexo
			System.out.print(" --> Alterar o sexo? (s=sim/n=nÃ£o) ");
			opcaoSexo = ler.nextLine().charAt(0);

			sexo = clientePF.getSexo();
			if (opcaoSexo == 's') {
				System.out.print(" - Novo sexo: ");
				sexo = ler.nextLine().charAt(0);
			}

			// exibir cpf
			System.out.println("\n - CPF: " + clientePF.getCpf());
			// perguntar se quer alterar o CPF
			System.out.print(" --> Alterar o CPF? (s=sim/n=nÃ£o) ");
			opcaoCpf = ler.nextLine().charAt(0);

			cpf = clientePF.getCpf();
			if (opcaoCpf == 's') {
				System.out.print(" - Novo CPF: ");
				cpf = ler.nextLong();
				ler.nextLine();
			}

		} else if (clienteParaAlterar instanceof ClientePessoaJuridica) {
			// tratar o cliente como pessoa juridica
			ClientePessoaJuridica clientePJ = (ClientePessoaJuridica) clienteParaAlterar;

			// exibir nome fantasia
			System.out.println("\n - Nome fantasia: " + clientePJ.getNomeFantasia());
			// perguntar se quer alterar o nome fantasia
			System.out.print(" --> Alterar o nome fantasia? (s=sim/n=nÃ£o) ");
			opcaoNomeFantasia = ler.nextLine().charAt(0);

			nomeFantasia = clientePJ.getNomeFantasia();
			if (opcaoNomeFantasia == 's') {
				System.out.print(" - Novo nome fantasia: ");
				nomeFantasia = ler.nextLine();
			}

			// exibir razao social
			System.out.println("\n - Razao social: " + clientePJ.getRazaoSocial());
			// perguntar se quer alterar a razao social
			System.out.print(" --> Alterar a razao social? (s=sim/n=nÃ£o) ");
			opcaoRazaoSocial = ler.nextLine().charAt(0);

			razaoSocial = clientePJ.getRazaoSocial();
			if (opcaoRazaoSocial == 's') {
				System.out.print(" - Nova razao social: ");
				razaoSocial = ler.nextLine();
			}

			// exibir cnpj
			System.out.println("\n - CNPJ: " + clientePJ.getCnpj());
			// perguntar se quer alterar o CNPJ
			System.out.print(" --> Alterar o CNPJ? (s=sim/n=nÃ£o) ");
			opcaoCnpj = ler.nextLine().charAt(0);

			cnpj = clientePJ.getCnpj();
			if (opcaoCnpj == 's') {
				System.out.print(" - Novo CNPJ: ");
				cnpj = ler.nextLong();
				ler.nextLine();
			}

			// exibir inscriÃ§ao estadual
			System.out.println("\n - InscriÃ§ao estadual: " + clientePJ.getInscricaoEstadual());
			// perguntar se quer alterar a inscricao estadual
			System.out.print(" --> Alterar a InscriÃ§ao Estadual? (s=sim/n=nÃ£o) ");
			opcaoInscricaoEstadual = ler.nextLine().charAt(0);

			inscricaoEstadual = clientePJ.getInscricaoEstadual();
			if (opcaoInscricaoEstadual == 's') {
				System.out.print(" - Nova InscriÃ§ao Estadual: ");
				inscricaoEstadual = ler.nextLong();
				ler.nextLine();
			}

		}

		// exibir endereÃ§o
		System.out.println("\n - EndereÃ§o: " + clienteParaAlterar.getEndereco());
		// perguntar se quer alterar o endereÃ§o
		System.out.print(" --> Alterar o endereÃ§o? (s=sim/n=nÃ£o) ");
		opcaoEndereco = ler.nextLine().charAt(0);

		endereco = clienteParaAlterar.getEndereco();
		if (opcaoEndereco == 's') {
			System.out.print(" - Novo endereÃ§o: ");
			endereco = ler.nextLine();
		}

		// exibir telefone
		System.out.println("\n - Telefone: " + clienteParaAlterar.getTelefone());
		// perguntar se quer alterar o telefone
		System.out.print(" --> Alterar o telefone? (s=sim/n=nÃ£o) ");
		opcaoTelefone = ler.nextLine().charAt(0);

		telefone = clienteParaAlterar.getTelefone();
		if (opcaoTelefone == 's') {
			System.out.print(" - Novo telefone: ");
			telefone = ler.nextLine();
		}

		// exibir email
		System.out.println("\n - Email: " + clienteParaAlterar.getEmail());
		// perguntar se quer alterar o email
		System.out.print(" --> Alterar o email? (s=sim/n=nÃ£o) ");
		opcaoEmail = ler.nextLine().charAt(0);

		email = clienteParaAlterar.getEmail();
		if (opcaoEmail == 's') {
			System.out.print(" - Novo email: ");
			email = ler.nextLine();
		}

		// confirmaÃ§Ã£o final!!!
		System.out.println("\nConfirma alteraÃ§Ã£o do cliente?");
		System.out.printf(" - CÃ³digo: %d\n", clienteParaAlterar.getCodigo());

		// exibir dados especificos de pessoa fisica:
		if (clienteParaAlterar instanceof ClientePessoaFisica) {
			System.out.printf(" - Nome..............: %s\n", nome);
			System.out.printf(" - Data de nascimento: %s\n", DateFormat.getDateInstance().format(dataNascimento));
			System.out.printf(" - Sexo..............: %s\n", sexo);
			System.out.printf(" - CPF...............: %d\n", cpf);
		}
		// exibir dados especificos de pessoa juridica:
		else if (clienteParaAlterar instanceof ClientePessoaJuridica) {
			System.out.printf(" - Nome fantasia.....: %s\n", nomeFantasia);
			System.out.printf(" - Razao social......: %s\n", razaoSocial);
			System.out.printf(" - CNPJ..............: %s\n", CadastroFornecedor.formatarCnpj(cnpj));
			System.out.printf(" - InscriÃ§ao estadual: %d\n", inscricaoEstadual);
		}

		// exibir dados gerais de cliente:
		System.out.printf(" - EndereÃ§o..........: %s\n", endereco);
		System.out.printf(" - Telefone..........: %s\n", telefone);
		System.out.printf(" - Email.............: %s\n", email);

		System.out.print(" --> (s=sim/n=nÃ£o) ");
		char opcao = ler.nextLine().charAt(0);
		if (opcao == 's') {
			Cliente clienteAlterado = null;
			if (clienteParaAlterar instanceof ClientePessoaFisica)
				clienteAlterado = new ClientePessoaFisica(codigo, endereco, telefone, email, cpf, sexo, nome,
						dataNascimento);
			else
				clienteAlterado = new ClientePessoaJuridica(codigo, endereco, telefone, email, cnpj, inscricaoEstadual,
						nomeFantasia, razaoSocial);

			ArmazenamentoCliente.alterar(clienteAlterado);
		}
	}

	@Override
	protected void excluir() {
		System.out.println("\nExcluir registro de cliente.\n");

		// obter o cÃ³digo do cliente a excluir
		System.out.print(" - Código do cliente a excluir: ");
		long codigo = ler.nextLong();
		ler.nextLine();

		// buscar dados do cliente para confirmaÃ§Ã£o de exclusÃ£o
		Cliente parametroBusca = new Cliente(codigo);
		Cliente clienteExcluir = ArmazenamentoCliente.buscar(parametroBusca);

		if (clienteExcluir == null) {
			System.out.println("NÃO HÁ CLIENTE CADASTRADO COM O CÓDIGO INFORMADO.");
			return;
		}

		if (clienteExcluir instanceof ClientePessoaFisica) {
			ClientePessoaFisica c = (ClientePessoaFisica) clienteExcluir;
			System.out.printf(" - Nome..............: %s\n", c.getNome());
			System.out.printf(" - Data de nascimento: %s\n",
					DateFormat.getDateInstance().format(c.getDataNascimento()));
			System.out.printf(" - Sexo..............: %s\n", c.getSexo());
			System.out.printf(" - CPF...............: %s\n", c.getCpf());
		} else if (clienteExcluir instanceof ClientePessoaJuridica) {
			ClientePessoaJuridica c = (ClientePessoaJuridica) clienteExcluir;
			System.out.printf(" - Nome fantasia.....: %s\n", c.getNomeFantasia());
			System.out.printf(" - RazÃ£o social......: %s\n", c.getRazaoSocial());
			System.out.printf(" - CNPJ..............: %s\n", CadastroFornecedor.formatarCnpj(c.getCnpj()));
			System.out.printf(" - InscriÃ§Ã£o estadual: %s\n", c.getInscricaoEstadual());
		}
		System.out.println(" - EndereÃ§o..........: " + clienteExcluir.getEndereco());
		System.out.println(" - Telefone..........: " + clienteExcluir.getTelefone());
		System.out.println(" - Email.............: " + clienteExcluir.getEmail());
		System.out.print("\n  --> Confirma exclusÃ£o? (s=sim/n=nÃ£o) ");

		char opcao = ler.nextLine().charAt(0);
		if (opcao == 's') {
			ArmazenamentoCliente.excluir(clienteExcluir);
		}
	}

}
