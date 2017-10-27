package controleestoque.Armazenamento;

import java.util.ArrayList;
import controleestoque.Entidades.Funcionario;

public class ArmazenamentoFuncionario {
	private static ArrayList<Funcionario> LISTA_FUNCIONARIO;

	public static ArrayList<Funcionario> getListaFuncionario() {
		return LISTA_FUNCIONARIO;
	}

	public static void iniciarListaFuncionario() {
		LISTA_FUNCIONARIO = new ArrayList<>();
	}

	public static void inserir(Funcionario f) {
		LISTA_FUNCIONARIO.add(f);
	}

	public static boolean alterar(Funcionario f) {
		Funcionario funcionarioParaAlterar = null;
		for (Funcionario func : LISTA_FUNCIONARIO) {
			if (func.getCodigo() == f.getCodigo()) {
				funcionarioParaAlterar = func;
				break;
			}
		}
		if (funcionarioParaAlterar != null) {
			funcionarioParaAlterar.setNome(f.getNome());
			funcionarioParaAlterar.setCpf(f.getCpf());
			funcionarioParaAlterar.setEndereco(f.getEndereco());
			funcionarioParaAlterar.setTelefone(f.getTelefone());
			funcionarioParaAlterar.setEmail(f.getEmail());

			return true;
		}
		return false;
	}

	public static boolean excluir(Funcionario f) {
		Funcionario funcionarioParaExcluir = null;
		for (Funcionario fun : LISTA_FUNCIONARIO) {
			if (fun.getCodigo() == f.getCodigo()) {
				funcionarioParaExcluir = fun;
				break;
			}
		}
		if (funcionarioParaExcluir != null) {
			LISTA_FUNCIONARIO.remove(funcionarioParaExcluir);
			return true;
		}
		return false;
	}

	public static Funcionario buscar(Funcionario f) {
		Funcionario funcionarioProcurado = null;
		for (Funcionario func : LISTA_FUNCIONARIO) {
			if (func.getCodigo() == f.getCodigo()) {
				funcionarioProcurado = func;
				break;
			}
		}
		return funcionarioProcurado;
	}

}
