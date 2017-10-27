package controleestoque.Armazenamento;

import java.util.ArrayList;

import controleestoque.Entidades.Venda;
import controleestoque.Entidades.ItemVenda;

public class ArmazenamentoVenda {

	private static ArrayList<Venda> LISTA_VENDA;

	public static ArrayList<Venda> getListaVenda() {
		return LISTA_VENDA;
	}

	public static void iniciarListaVenda() {
		LISTA_VENDA = new ArrayList<>();
	}

	public static void inserir(Venda c) {
		LISTA_VENDA.add(c);
	}

	public static boolean alterar(Venda c) {
		Venda vendaAlterar = buscar(c);
		if (vendaAlterar != null) {
			vendaAlterar.setData(c.getData());
			vendaAlterar.setVendedor(c.getVendedor());
			vendaAlterar.setValorTotal(c.getValorTotal());
			vendaAlterar.setCliente(c.getCliente());
			ArrayList<ItemVenda> itensVenda = vendaAlterar.getItensVenda();
			itensVenda.clear();
			itensVenda.addAll(c.getItensVenda());
			return true;
		}
		return false;
	}

	public static boolean excuir(Venda c) {
		Venda vendaExcluir = buscar(c);
		if (vendaExcluir != null) {
			LISTA_VENDA.remove(vendaExcluir);
			return true;
		}
		return false;
	}

	public static Venda buscar(Venda c) {
		for (Venda venda : LISTA_VENDA) {
			if (venda.getCodigo() == c.getCodigo()) {
				return venda;
			}
		}
		return null;
	}
}