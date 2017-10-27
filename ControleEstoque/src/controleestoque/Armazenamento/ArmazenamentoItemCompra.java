package controleestoque.Armazenamento;

import java.util.ArrayList;

import controleestoque.Entidades.ItemCompra;

public class ArmazenamentoItemCompra {

	private static ArrayList<ItemCompra> LISTA_ITEM_COMPRA;

	public static ArrayList<ItemCompra> getLista() {
		return LISTA_ITEM_COMPRA;
	}

	public static void iniciarListaItemCompra() {
		if (LISTA_ITEM_COMPRA == null) {
			LISTA_ITEM_COMPRA = new ArrayList<>();
		} else {
			LISTA_ITEM_COMPRA.clear();
		}
	}

	public static void inserir(ItemCompra i) {
		LISTA_ITEM_COMPRA.add(i);
	}

	public static boolean alterar(ItemCompra i) {
		ItemCompra itemCompraAlterar = null;
		for (ItemCompra item : LISTA_ITEM_COMPRA) {
			if (item.getCodigo() == i.getCodigo()) {
				itemCompraAlterar = item;
				break;
			}
		}

		if (itemCompraAlterar != null) {
			itemCompraAlterar.setCompra(i.getCompra());
			itemCompraAlterar.setProduto(i.getProduto());
			itemCompraAlterar.setPrecoCompra(i.getPrecoCompra());
			itemCompraAlterar.setQuantidade(i.getQuantidade());
			return true;
		}
		return false;
	}

	public static boolean excluir(ItemCompra i) {
		ItemCompra itemCompraExcluir = buscar(i);
		if (itemCompraExcluir != null) {
			LISTA_ITEM_COMPRA.remove(itemCompraExcluir);
			return true;
		}
		return false;
	}

	public static ItemCompra buscar(ItemCompra i) {
		for (ItemCompra item : LISTA_ITEM_COMPRA) {
			if (item.getCodigo() == i.getCodigo()) {
				return item;
			}
		}
		return null;
	}
}
