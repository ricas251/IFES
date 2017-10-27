package controleestoque.Armazenamento;

import java.util.ArrayList;
import controleestoque.Entidades.Compra;
import controleestoque.Entidades.Entidade;
import controleestoque.Entidades.ItemCompra;

public class ArmazenamentoCompra extends Armazenamento {


	private static ArmazenamentoCompra INSTANCE;
	
	public static ArmazenamentoCompra getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ArmazenamentoCompra();
		}
		return INSTANCE;
	}
	
	
	

	@Override
	public  boolean alterar(Entidade e) {
		Compra c = (Compra) e;
		Compra compraAlterar =(Compra) buscar(c);
		if (compraAlterar != null) {
			compraAlterar.setComprador(c.getComprador());
			compraAlterar.setData(c.getData());
			compraAlterar.setFornecedor(c.getFornecedor());
			compraAlterar.setValorTotal(c.getValorTotal());
			ArrayList<ItemCompra> itensCompra = compraAlterar.getItensCompra();
			itensCompra.clear();
			itensCompra.addAll(c.getItensCompra());
			return true;
		}
		return false;
	}


}
