package controleestoque.Armazenamento;

import controleestoque.Entidades.Cliente;
import controleestoque.Entidades.ClientePessoaFisica;
import controleestoque.Entidades.ClientePessoaJuridica;
import java.util.ArrayList;

public class ArmazenamentoCliente {

	private static ArrayList<Cliente> LISTA_CLIENTE;

	public static ArrayList<Cliente> getLista() {
		return LISTA_CLIENTE;
	}

	public static void iniciarListaCliente() {
		LISTA_CLIENTE = new ArrayList<>();
	}

	public static void inserir(Cliente c) {
		LISTA_CLIENTE.add(c);
	}

	public static boolean alterar(Cliente c) {
		Cliente clienteParaAlterar = null;
		for (Cliente cli : LISTA_CLIENTE) {
			if (cli.getCodigo() == c.getCodigo()) {
				clienteParaAlterar = cli;
				break;
			}
		}
		if (clienteParaAlterar != null) {
			clienteParaAlterar.setEmail(c.getEmail());
			clienteParaAlterar.setEndereco(c.getEndereco());
			clienteParaAlterar.setTelefone(c.getTelefone());

			if (clienteParaAlterar instanceof ClientePessoaFisica) {
				ClientePessoaFisica pfAlterar = (ClientePessoaFisica) clienteParaAlterar;
				ClientePessoaFisica pfParametro = (ClientePessoaFisica) c;

				pfAlterar.setNome(pfParametro.getNome());
				pfAlterar.setDataNascimento(pfParametro.getDataNascimento());
				pfAlterar.setSexo(pfParametro.getSexo());
				pfAlterar.setCpf(pfParametro.getCpf());

			} else if (clienteParaAlterar instanceof ClientePessoaJuridica) {
				ClientePessoaJuridica pjAlterar = (ClientePessoaJuridica) clienteParaAlterar;
				ClientePessoaJuridica pjParametro = (ClientePessoaJuridica) c;

				pjAlterar.setNomeFantasia(pjParametro.getNomeFantasia());
				pjAlterar.setRazaoSocial(pjParametro.getRazaoSocial());
				pjAlterar.setCnpj(pjParametro.getCnpj());
				pjAlterar.setInscricaoEstadual(pjParametro.getInscricaoEstadual());
			}

			return true;
		}
		return false;
	}

	public static boolean excluir(Cliente c) {
		Cliente clienteParaExcluir = null;
		for (Cliente cli : LISTA_CLIENTE) {
			if (cli.getCodigo() == c.getCodigo()) {
				clienteParaExcluir = cli;
				break;
			}
		}
		if (clienteParaExcluir != null) {
			LISTA_CLIENTE.remove(clienteParaExcluir);
			return true;
		}
		return false;
	}

	public static Cliente buscar(Cliente c) {
		Cliente clienteProcurado = null;
		for (Cliente cli : LISTA_CLIENTE) {
			if (cli.getCodigo() == c.getCodigo()) {
				clienteProcurado = cli;
				break;
			}
		}
		return clienteProcurado;
	}

}
