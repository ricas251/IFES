/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controleestoque.Entidades;

/**
 *
 * @author 20162in005
 */
public class Produto {
	private long codigo;
	private String nome;
	private double preco;

	public Produto(long codigo, String nome, double preco) {
		this.codigo = codigo;
		this.nome = nome;
		this.preco = preco;
	}

	public Produto(long codigo) {
		this.codigo = codigo;
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

}
