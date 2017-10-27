package controleestoque.Armazenamento;

import java.util.ArrayList;

import controleestoque.Entidades.Entidade;

public abstract class Armazenamento {

	private ArrayList<Entidade> lista;
	public  ArrayList getLista() {
		return lista;
	}
	public  void iniciarLista() {
		lista = new ArrayList<>();
	}
	public  void inserir(Entidade e) {
		lista.add(e);
	}
	public abstract boolean alterar (Entidade e);
	public  boolean excluir (Entidade e) {
		Entidade entidadeExcluir = (Entidade) buscar (e);
		if(entidadeExcluir != null) {
			lista.remove(entidadeExcluir);
			return true;
		}
		return false;
	}
	public Object buscar (Entidade e) {
		for (Entidade entidade : lista) {
			if (entidade.getCodigo() == e.getCodigo()) {
				return entidade;
			}
		}return null;
	}
	
}
