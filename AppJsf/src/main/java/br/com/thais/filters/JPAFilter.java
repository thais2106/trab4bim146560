package br.com.thais.filters;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Filtro para interceptar as requisições feita ao Faces Servlet. Pode ser
 * chamado antes da requisição e depois que a resposta foi gerada também.
 * 
 * @author Thaís
 * @since 8 de nov de 2016 - 20:22:58
 */

@WebFilter(servletNames = { "Faces Servlet" })
public class JPAFilter implements Filter {

	private EntityManagerFactory entityManagerFactory;
	private String persistence_unit_name = "unit_app";

	/*
	 * Construtor da classe
	 */
	public JPAFilter() {

	}

	/*
	 * Chamado quando o filtro já executou seu serviço e o entityManagerFactory
	 * pode ser finalizado. Fecha o entityManagerFactory (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		this.entityManagerFactory.close();
	}

	/*
	 * Filtragem das requisições e respostas. O EntityManager é criado e
	 * adicionado na requisição, inicia-se o Faces Servlet e a transação do
	 * EntityManager. Após a requisição ser executada, o EntityManager finaliza
	 * seu serviço. Caso algo dê errado, ele desfaz o processo. (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		EntityManager entityManager = this.entityManagerFactory.createEntityManager();
		request.setAttribute("entityManager", entityManager);
		entityManager.getTransaction().begin();
		chain.doFilter(request, response);

		try {
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
		} finally {
			entityManager.close();
		}
	}

	/*
	 * Chamado quando uma requisição é feita. Ao iniciar o filtro, é criado um
	 * entityManagerFactory com os parâmetros defenidos no arquivo
	 * persistance.xml, inciando a conexão com o banco. (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.entityManagerFactory = Persistence.createEntityManagerFactory(this.persistence_unit_name);
	}

}