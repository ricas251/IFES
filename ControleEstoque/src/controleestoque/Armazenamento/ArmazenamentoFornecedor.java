package controleestoque.Armazenamento;

import java.util.ArrayList;
import controleestoque.Entidades.Fornecedor;

public class ArmazenamentoFornecedor {

	private static ArrayList<Fornecedor> LISTA_FORNECEDOR;

	public static ArrayList<Fornecedor> getListaFornecedor() {
		return LISTA_FORNECEDOR;
	}

	public static void iniciarListaProduto() {
		LISTA_FORNECEDOR = new ArrayList<>();
	}

	public static void inserirFornecedor(Fornecedor f) {
		LISTA_FORNECEDOR.add(f);
	}

	public static boolean alterar(Fornecedor f) {
		Fornecedor fornecedorParaAlterar = null;
		for (Fornecedor forn : LISTA_FORNECEDOR) {
			if (forn.getCodigo() == f.getCodigo()) {
				fornecedorParaAlterar = forn;
				break;
			}
		}
		if (fornecedorParaAlterar != null) {
			fornecedorParaAlterar.setNomeFantasia(f.getNomeFantasia());
			fornecedorParaAlterar.setRazaoSocial(f.getRazaoSocial());
			fornecedorParaAlterar.setEndereco(f.getEndereco());
			fornecedorParaAlterar.setCnpj(f.getCnpj());
			fornecedorParaAlterar.setInscricaoEstadual(f.getInscricaoEstadual());
			fornecedorParaAlterar.setTelefone(f.getTelefone());
			fornecedorParaAlterar.setEmail(f.getEmail());
			return true;
		}
		return false;
	}

	public static boolean excluir(Fornecedor f) {
		Fornecedor fornecedorParaExcluir = null;
		for (Fornecedor forn : LISTA_FORNECEDOR) {
			if (forn.getCodigo() == f.getCodigo()) {
				fornecedorParaExcluir = forn;
				break;
			}
		}
		if (fornecedorParaExcluir != null) {
			LISTA_FORNECEDOR.remove(fornecedorParaExcluir);
			return true;
		}
		return false;
	}

	public static Fornecedor buscar(Fornecedor f) {
		Fornecedor fornecedorProcurado = null;
		for (Fornecedor forn : LISTA_FORNECEDOR) {
			if (forn.getCodigo() == f.getCodigo()) {
				fornecedorProcurado = forn;
				break;
			}
		}
		return fornecedorProcurado;
	}

}
