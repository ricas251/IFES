
package controleestoque;

import controleestoque.fronteiras.MenuPrincipal;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;

import controleestoque.Armazenamento.ArmazenamentoCliente;
import controleestoque.Armazenamento.ArmazenamentoCompra;
import controleestoque.Armazenamento.ArmazenamentoFornecedor;
import controleestoque.Armazenamento.ArmazenamentoFuncionario;
import controleestoque.Armazenamento.ArmazenamentoProduto;
import controleestoque.Armazenamento.ArmazenamentoVenda;
import controleestoque.Entidades.Cliente;
import controleestoque.Entidades.ClientePessoaFisica;
import controleestoque.Entidades.ClientePessoaJuridica;
import controleestoque.Entidades.Comprador;
import controleestoque.Entidades.Fornecedor;
import controleestoque.Entidades.Funcionario;
import controleestoque.Entidades.Produto;
import controleestoque.Entidades.Vendedor;

public class ControleEstoque {

	public static void main(String[] args) {
		
		ArmazenamentoProduto.iniciarListaProduto();
		
		ArmazenamentoFornecedor.iniciarListaProduto();
		
		ArmazenamentoCliente.iniciarListaCliente();
		
		ArmazenamentoFuncionario.iniciarListaFuncionario();
		
		ArmazenamentoCompra.getInstance().iniciarLista();
		
		ArmazenamentoVenda.iniciarListaVenda();
		
		cadastrarDadosParaTestes();
		MenuPrincipal menu = new MenuPrincipal();
		menu.exibirMenu();
	}

	private static void cadastrarDadosParaTestes() {
		ArrayList<Produto> listaProduto = ArmazenamentoProduto.getListaProduto();
		ArrayList<Fornecedor> listaFornecedor = ArmazenamentoFornecedor.getListaFornecedor();
		ArrayList<Cliente> listaCliente = ArmazenamentoCliente.getLista();
		ArrayList<Funcionario> listaFuncionario = ArmazenamentoFuncionario.getListaFuncionario();

		listaProduto.add(new Produto(1, "Banana", 2.5));
		listaProduto.add(new Produto(2, "Melão", 5.4));
		listaProduto.add(new Produto(3, "Maçã", 3.5));
		listaProduto.add(new Produto(4, "Abacaxi", 3.0));
		listaProduto.add(new Produto(5, "Melancia", 6.0));

		listaFornecedor.add(new Fornecedor(1, "Bananas EP", "Bananas EP Ltda",
				"Rua 123, n.34 - Bairro do Limoeiro - Indaiatuba - SP", 12938910001L, 12938190, "(28)3245-0981",
				"bananas@bananas.com.br"));
		listaFornecedor.add(new Fornecedor(2, "Horti-Verde", "Comercial Horti-Verde Ltda-Me",
				"Rua 124, n.23 - Bairro do Limoeiro - Indaiatuba - SP", 129389101321L, 12938123, "(28)3245-0000",
				"hortiverde@hortiverde.com.br"));
		listaFornecedor.add(new Fornecedor(3, "Resplendor", "Comercial Resplendor Ltda-Me",
				"Rua 145, n.65 - Bairro do Limoeiro - Indaiatuba - SP", 124674256895L, 12918390, "(28)3442-0011",
				"resplendor@resplendor.com.br"));

		try {
			listaCliente.add(
					new ClientePessoaFisica(1, "Rua Alberto Santos Dumont, 198 - Bairro das Arraias - Marataízes - ES",
							"(28)99987-0911", "matusalem@matusa.com.br", 10625478954L, 'M', "Matusalem da Silva",
							DateFormat.getDateInstance().parse("02/05/1931")));
			listaCliente.add(new ClientePessoaJuridica(2,
					"Rua Alberto Santos Dumont, 198 - Bairro das Arraias - Marataízes - ES", "(28)3245-0012",
					"fruvit@fruvit.com.br", 145214587552L, 125478598, "Fruvit", "Fruvit Frutas Vitoria Ltda"));
			listaCliente.add(new ClientePessoaFisica(3,
					"Rua Amphilóphio Boaventura Sant'Anna, 11 - Bairro dos Cações - Marataízes - ES", "(28)99920-0123",
					"uatissom@orkut.com.br", 14582657485L, 'M', "Uatissom dos Santos",
					DateFormat.getDateInstance().parse("30/07/1997")));
		} catch (ParseException e) {
			// nada a fazer...
		}

		listaFuncionario.add(new Vendedor(1, "Carlos", 14051814655L, "Rua tio ze", "2899909645", "carlos@gmail.com"));
		listaFuncionario.add(new Comprador(2, "Jose", 14051815645L, "Rua tio carlos", "28946456486", "jose@gmail.com"));
		listaFuncionario
				.add(new Vendedor(3, "Lucas", 14065432585L, "Rua Francisco de paula", "2899904698", "lucas@gmail.com"));
		listaFuncionario
				.add(new Comprador(4, "Jorge", 14055645465L, "Rua tio carlos", "2894644468", "jorge@gmail.com"));
	}

}
