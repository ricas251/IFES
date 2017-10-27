package controleestoque.Armazenamento;

import java.util.ArrayList;

import controleestoque.Entidades.Produto;

public class ArmazenamentoProduto {

	private static ArrayList<Produto> LISTAPRODUTO;

	public static ArrayList<Produto> getListaProduto() {
		return LISTAPRODUTO;
	}

	public static void iniciarListaProduto() {
		LISTAPRODUTO = new ArrayList<>();
	}

	public static void inserirProduto(Produto p) {
		LISTAPRODUTO.add(p);

	}

	public static boolean alterarProduto(Produto p) {
		Produto produtoParaAlterar = null;
		for (Produto prod : LISTAPRODUTO) {
			if (prod.getCodigo() == p.getCodigo()) {
				produtoParaAlterar = prod;
				break;
			}
		}
		if (produtoParaAlterar != null) {
			produtoParaAlterar.setNome(p.getNome());
			produtoParaAlterar.setPreco(p.getPreco());
			return true;
		}
		return false;
	}

	public static boolean excluirProduto(Produto p) {
		Produto produtoParaExcluir = null;
		for (Produto prod : LISTAPRODUTO) {
			if (prod.getCodigo() == p.getCodigo()) {
				produtoParaExcluir = prod;
				break;
			}
		}
		if (produtoParaExcluir != null) {
			LISTAPRODUTO.remove(produtoParaExcluir);
			return true;
		}
		return false;
	}

	public static Produto buscarProduto(Produto p) {
		Produto produtoProcurado = null;
		for (Produto prod : LISTAPRODUTO) {
			if (prod.getCodigo() == p.getCodigo()) {
				produtoProcurado = prod;
				break;
			}
		}
		return produtoProcurado;
	}

}
