/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controleestoque.Entidades;

import java.util.Date;

/**
 *
 * @author 20162in005
 */
public class ClientePessoaFisica extends Cliente {
	private long cpf;
	private char sexo;
	private String nome;
	private Date dataNascimento;

	public ClientePessoaFisica(long codigo, String endereco, String telefone, String email, long cpf, char sexo,
			String nome, Date dataNascimento) {
		super(codigo, endereco, telefone, email);
		this.cpf = cpf;
		this.sexo = sexo;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
	}

	public long getCpf() {
		return cpf;
	}

	public void setCpf(long cpf) {
		this.cpf = cpf;
	}

	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

}
