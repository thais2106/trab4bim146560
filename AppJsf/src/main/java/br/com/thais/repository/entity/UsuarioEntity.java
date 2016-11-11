package br.com.thais.repository.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Entidade Usuario para persistir na tabela de usuários no banco de dados.
 * 
 * @author Thaís
 * @since 8 de nov de 2016 - 21:11:28
 */

@Table(name = "tb_usuario")
@Entity
@NamedQuery(name = "UsuarioEntity.findUser", query = "SELECT u FROM UsuarioEntity u WHERE u.usuario = :usuario AND u.senha = :senha")
public class UsuarioEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id_usuario")
	private String codigo;

	@Column(name = "ds_login")
	private String usuario;

	@Column(name = "ds_senha")
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