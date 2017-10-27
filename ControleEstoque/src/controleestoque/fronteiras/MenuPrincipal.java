/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controleestoque.fronteiras;

import java.util.Scanner;

/**
 *
 * @author CARLOSRICAS251
 */
public class MenuPrincipal {
	private static final int OPCAO_CADASTRO_CLIENTE = 1;
	private static final int OPCAO_CADASTRO_FORNECEDOR = 2;
	private static final int OPCAO_CADASTRO_PRODUTO = 3;
	private static final int OPCAO_CADASTRO_FUNCIONARIO = 4;
	private static final int OPCAO_CADASTRO_COMPRA = 5;
	private static final int OPCAO_CADASTRO_VENDA = 6;
	private static final int OPCAO_TERMINAR_PROGRAMA = 7;

	public void exibirMenu() {
		Scanner ler = new Scanner(System.in);
		int opcao = 0;
		while (opcao != OPCAO_TERMINAR_PROGRAMA) {
			System.out.println("\n\nSEJA BEM VINDO!");
			System.out.println("_______________________________________________________________________________");
			System.out.println("\n\nMenu Principal:");
			System.out.println(" 1 - Cliente");
			System.out.println(" 2 - Fornecedor");
			System.out.println(" 3 - Produto");
			System.out.println(" 4 - Funcionário");
			System.out.println(" 5 - Compra");
			System.out.println(" 6 - Venda");
			System.out.println(" 7 - Terminar programa");
			System.out.print("---> Digite o número da opção desejada e tecle ENTER: ");

			opcao = ler.nextInt();
			processarOpcaoUsuario(opcao);
		}
		System.out.println("\nPrograma encerrado.\n");
		System.out.println("\nTENHA UM BOM TRABALHO");
		ler.close();
	}

	private void processarOpcaoUsuario(int opcao) {
		switch (opcao) {
		case OPCAO_CADASTRO_CLIENTE:
			CadastroCliente cadastroCliente = new CadastroCliente();
			cadastroCliente.exibirMenu();
			break;
		case OPCAO_CADASTRO_FORNECEDOR:
			CadastroFornecedor cadastroFornecedor = new CadastroFornecedor();
			cadastroFornecedor.exibirMenu();
			break;
		case OPCAO_CADASTRO_PRODUTO:
			CadastroProduto cadastroProduto = new CadastroProduto();
			cadastroProduto.exibirMenu();
			break;
		case OPCAO_CADASTRO_FUNCIONARIO:
			CadastroFuncionario cadastroFuncionario = new CadastroFuncionario();
			cadastroFuncionario.exibirMenu();
			break;
		case OPCAO_CADASTRO_COMPRA:
			CadastroCompra cadastroCompra = new CadastroCompra();
			cadastroCompra.exibirMenu();
			break;
		case OPCAO_CADASTRO_VENDA:
			CadastroVenda cadastroVenda = new CadastroVenda();
			cadastroVenda.exibirMenu();
			break;
		default:
			if (opcao != OPCAO_TERMINAR_PROGRAMA) {
				System.out.println("\nVOCÊ DIGITOU UMA OPÇÃO INVÁLIDA!");
			}
		}
	}
}
