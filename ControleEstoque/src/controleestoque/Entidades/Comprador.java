/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controleestoque.Entidades;

/**
 *
 * @author 20162in005
 */
public final class Comprador extends Funcionario {

	public Comprador(long codigo, String nome, long cpf, String endereco, String telefone, String email) {
		super(codigo, nome, cpf, endereco, telefone, email);
	}

	public Comprador(long codigo) {
		super(codigo);
	}

}
