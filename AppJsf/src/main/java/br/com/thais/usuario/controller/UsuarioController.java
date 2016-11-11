package br.com.thais.usuario.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import br.com.thais.model.UsuarioModel;
import br.com.thais.repository.entity.UsuarioEntity;
import br.com.thais.uteis.Uteis;
import br.univel.thais.repository.UsuarioRepository;

/**
 * Classe que controla a autenticação do Usuário
 * 
 * @author Thaís
 * @since 10 de nov de 2016 - 20:49:10
 */

// Anotação que transforma a classe em um bean gerenciado pelo CDI
@Named(value = "usuarioController")
// Definindo escopo de sessão
@SessionScoped
public class UsuarioController implements Serializable {

	private static final long serialVersionUID = 1L;

	// Injeção de dependência
	@Inject
	private UsuarioModel usuarioModel;

	@Inject
	private UsuarioRepository usuarioRepository;

	@Inject
	private UsuarioEntity usuarioEntity;

	/*
	 * Getter da classe UsuarioModel
	 */
	public UsuarioModel getUsuarioModel() {
		return usuarioModel;
	}

	/*
	 * Setter da classe UsuarioModel
	 */
	public void setUsuarioModel(UsuarioModel usuarioModel) {
		this.usuarioModel = usuarioModel;
	}

	/*
	 * Retorna o usuário logado no sistema
	 */
	public UsuarioModel GetUsuarioSession() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return (UsuarioModel) facesContext.getExternalContext().getSessionMap().get("usuarioAutenticado");
	}

	/*
	 * Finaliza a sessão do usuário e retorna para a página de login
	 */
	public String Logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/index.xhtml?faces-redirect=true";
	}

	/*
	 * Método que autentica o usuário e senha informados e efetua login ou
	 * retorna mensagem de erro.
	 */
	public String EfetuarLogin() {

		if (StringUtils.isEmpty(usuarioModel.getUsuario()) || StringUtils.isBlank(usuarioModel.getUsuario())) {
			Uteis.Mensagem("Favor, informar o login!");
			return null;
		} else if (StringUtils.isEmpty(usuarioModel.getSenha()) || StringUtils.isBlank(usuarioModel.getSenha())) {
			Uteis.Mensagem("Favor, informar a senha!");
			return null;
		} else {
			usuarioEntity = usuarioRepository.ValidaUsuario(usuarioModel);

			if (usuarioEntity != null) {
				usuarioModel.setSenha(null);
				usuarioModel.setCodigo(usuarioEntity.getCodigo());

				FacesContext facesContext = FacesContext.getCurrentInstance();
				facesContext.getExternalContext().getSessionMap().put("usuarioAutenticado", usuarioModel);

				return "sistema/home?faces-redirect=true";
			} else {
				Uteis.Mensagem("Não foi possível efetuar o login com esse usuário e senha!");
				return null;
			}
		}

	}

}