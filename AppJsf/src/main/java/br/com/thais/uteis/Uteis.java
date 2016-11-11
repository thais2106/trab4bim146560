package br.com.thais.uteis;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

/**
 * Classe com método usados com frequência pelo sistema.
 * 
 * @author Thaís
 * @since 8 de nov de 2016 - 20:54:14
 */

public class Uteis {

	/*
	 * Recupera o EntityManager criado no filtro JPAFilter.
	 */
	public static EntityManager JpaEntityManager() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		return (EntityManager) request.getAttribute("entityManager");
	}

	/*
	 * Mostra mensagem normal
	 */
	public static void Mensagem(String mensagem) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage("Alerta", mensagem));
	}

	/*
	 * Mostra mensagem de alerta/atenção
	 */
	public static void MensagemAtencao(String mensagem) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção:", mensagem));
	}

	/*
	 * Mostra mensagem informativa
	 */
	public static void MensagemInfo(String mensagem) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", mensagem));
	}

}