/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controleestoque.Entidades;

/**
 *
 * @author 20162in005
 */
public class Fornecedor {

	private long codigo;
	private String nomeFantasia;
	private String razaoSocial;
	private String endereco;
	private long cnpj;
	private long inscricaoEstadual;
	private String telefone;
	private String email;

	public Fornecedor(long codigo) {
		this.codigo = codigo;
	}

	public Fornecedor(long codigo, String nomeFantasia, String razaoSocial, String endereco, long cnpj,
			long inscricaoEstadual, String telefone, String email) {
		this.codigo = codigo;
		this.nomeFantasia = nomeFantasia;
		this.razaoSocial = razaoSocial;
		this.endereco = endereco;
		this.cnpj = cnpj;
		this.inscricaoEstadual = inscricaoEstadual;
		this.telefone = telefone;
		this.email = email;
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public long getCnpj() {
		return cnpj;
	}

	public void setCnpj(long cnpj) {
		this.cnpj = cnpj;
	}

	public long getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(long inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
