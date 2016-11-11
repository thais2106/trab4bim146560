package br.univel.thais.repository;

import java.io.Serializable;

import javax.persistence.Query;

import br.com.thais.model.UsuarioModel;
import br.com.thais.repository.entity.UsuarioEntity;
import br.com.thais.uteis.Uteis;

/**
 * Repository: Camada de negócio que complementa o Model, recupera os objetos do
 * banco e repassa para as outras classes.
 * 
 * @author Thaís
 * @since 10 de nov de 2016 - 19:45:49
 */

public class UsuarioRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Cria query para recuperar o usuário no banco
	 */
	public UsuarioEntity ValidaUsuario(UsuarioModel usuarioModel) {

		try {
			Query query = Uteis.JpaEntityManager().createNamedQuery("UsuarioEntity.findUser");
			query.setParameter("usuario", usuarioModel.getUsuario());
			query.setParameter("senha", usuarioModel.getSenha());
			return (UsuarioEntity) query.getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}