package br.com.thais.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.thais.model.UsuarioModel;

/**
 * Filtro que verifica se o usuário está logado no sistema, disponibilizando
 * acesso às páginas do sistema.
 * 
 * @author Thaís
 * @since 10 de nov de 2016 - 21:25:45
 */
@WebFilter("/sistema/*")
public class AutenticacaoFilter implements Filter {

	/*
	 * Construtor da classe
	 */
	public AutenticacaoFilter() {

	}

	/*
	 * Chamado quando o filtro já executou seu serviço. Neste caso, não há
	 * implementação.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {

	}

	/*
	 * Verifica se há usuário logado na sessão do sistema, se tiver, ele dá
	 * acesso às páginas, senão, ele retorna para a página de autentição
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpSession httpSession = ((HttpServletRequest) request).getSession();
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		if (httpServletRequest.getRequestURI().indexOf("index.xhtml") <= -1) {
			UsuarioModel usuarioModel = (UsuarioModel) httpSession.getAttribute("usuarioAutenticado");

			if (usuarioModel == null) {
				httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/index.xhtml");

			} else {
				chain.doFilter(request, response);
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	/*
	 * Chamado ao iniciar o filtro. Neste caso, não há implementação.
	 *
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

	}

}