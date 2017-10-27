package controleestoque.Armazenamento;

import java.util.ArrayList;

import controleestoque.Entidades.ItemVenda;

public class ArmazenamentoItemVenda {
	private static ArrayList<ItemVenda> LISTA_ITEM_VENDA;

	public static ArrayList<ItemVenda> getLista() {
		return LISTA_ITEM_VENDA;
	}

	public static void iniciarListaItemVenda() {
		if (LISTA_ITEM_VENDA == null) {
			LISTA_ITEM_VENDA = new ArrayList<>();
		} else {
			LISTA_ITEM_VENDA.clear();
		}
	}

	public static void inserir(ItemVenda i) {
		LISTA_ITEM_VENDA.add(i);
	}

	public static boolean alterar(ItemVenda i) {
		ItemVenda itemVendaAlterar = null;
		for (ItemVenda item : LISTA_ITEM_VENDA) {
			if (item.getCodigo() == i.getCodigo()) {
				itemVendaAlterar = item;
				break;
			}
		}

		if (itemVendaAlterar != null) {
			itemVendaAlterar.setVenda(i.getVenda());
			itemVendaAlterar.setProduto(i.getProduto());
			itemVendaAlterar.setPrecoVenda(i.getPrecoVenda());
			itemVendaAlterar.setQuantidade(i.getQuantidade());
			return true;
		}
		return false;
	}

	public static boolean excluir(ItemVenda i) {
		ItemVenda itemVendaExcluir = buscar(i);
		if (itemVendaExcluir != null) {
			LISTA_ITEM_VENDA.remove(itemVendaExcluir);
			return true;
		}
		return false;
	}

	public static ItemVenda buscar(ItemVenda i) {
		for (ItemVenda item : LISTA_ITEM_VENDA) {
			if (item.getCodigo() == i.getCodigo()) {
				return item;
			}
		}
		return null;
	}
}
