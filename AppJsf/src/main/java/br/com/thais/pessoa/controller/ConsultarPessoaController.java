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
 * Classe controller para a consulta de registros na tabela tb_pessoa (PessoaEntity)
 * @author Thaís 
 * @since  14 de nov de 2016 - 21:54:22
 */
@Named(value = "consultarPessoaController")
@ViewScoped
public class ConsultarPessoaController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	transient private PessoaModel pessoaModel;

	@Produces
	private List<PessoaModel> pessoas;

	@Inject
	transient private PessoaRepository pessoaRepository;

	public List<PessoaModel> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<PessoaModel> pessoas) {
		this.pessoas = pessoas;
	}

	public PessoaModel getPessoaModel() {
		return pessoaModel;
	}

	public void setPessoaModel(PessoaModel pessoaModel) {
		this.pessoaModel = pessoaModel;
	}

	/*
	 * Método post construct que carrega todas as pessoas cadastradas na inicialização da classe.
	 */
	@PostConstruct
	public void init() {
		this.pessoas = pessoaRepository.GetPessoas();
	}

}