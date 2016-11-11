package br.com.thais.model;

import java.io.Serializable;

/**
 * Classe UsuarioModel, usada para capturar os dados da tela e configurar em um
 * objeto UsuarioEntity.
 * 
 * @author Thaís
 * @since 8 de nov de 2016 - 21:21:24
 */

public class UsuarioModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codigo;
	private String usuario;
	private String senha;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
