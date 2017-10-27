package controleestoque.Entidades;

import java.util.ArrayList;
import java.util.Date;

public class Venda {
	private long codigo;
	private Date data;
	private double valorTotal;
	private Vendedor vendedor;
	private Cliente cliente;
	private ArrayList<ItemVenda> itensVenda;

	public Venda(long codigo, Date data, double valorTotal, Vendedor vendedor, Cliente cliente) {
		super();
		this.codigo = codigo;
		this.data = data;
		this.valorTotal = valorTotal;
		this.vendedor = vendedor;
		this.cliente = cliente;
		this.itensVenda = new ArrayList<>();
	}

	public Venda(long codigo) {
		super();
		this.codigo = codigo;
	}

	public void atualizarValorTotal() {
		double valor = 0;
		for (ItemVenda iv : this.itensVenda) {
			valor += iv.getPrecoVenda() * iv.getQuantidade();
		}
		this.valorTotal = valor;
	}

	public void inserirItemVenda(ItemVenda item) {
		this.itensVenda.add(item);
		atualizarValorTotal();
	}

	public boolean removerItemVenda(ItemVenda item) {
		ItemVenda itemRemover = null;
		for (ItemVenda itemLista : this.itensVenda) {
			if (itemLista.getCodigo() == item.getCodigo()) {
				itemRemover = itemLista;
			}
		}

		if (itemRemover != null) {
			this.itensVenda.remove(itemRemover);
			atualizarValorTotal();
			return true;
		}

		return false;
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Vendedor getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public ArrayList<ItemVenda> getItensVenda() {
		return itensVenda;
	}

	public void setItensVenda(ArrayList<ItemVenda> itensVenda) {
		this.itensVenda = itensVenda;
	}

}
