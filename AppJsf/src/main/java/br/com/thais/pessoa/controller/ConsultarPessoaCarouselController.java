package br.com.thais.pessoa.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.thais.model.PessoaModel;
import br.com.thais.repository.PessoaRepository;

/**
 * Classe controller para consulta em carousel
 * @author Thaís 
 * @since  25 de nov de 2016 - 19:55:46
 */
@Named(value="consultarPessoaCarouselController")
@ViewScoped
public class ConsultarPessoaCarouselController implements Serializable {
 
	private static final long serialVersionUID = 1L;
 
	@Inject transient
	private PessoaRepository pessoaRepository;
 
	@Produces 
	private List<PessoaModel> pessoas;
 
	/*
	 * Método chamado pelo consultarPessoaCarousel.xhtml, retorna a lista de pessoas consultadas
	 */
	public List<PessoaModel> getPessoas() {
		return pessoas;
	}
	
	/*
	 * Método chamado após construção do objeto, consulta as pessoas cadastradas chamando o
	 * método do respository.
	 */
	@PostConstruct
	private void init(){
		this.pessoas = pessoaRepository.GetPessoas();
	} 
}
