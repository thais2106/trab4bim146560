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
	
	/*
	 * Carrega as informações de um registro pessoa que será editado
	 */
	public void Editar(PessoaModel pessoaModel){
		pessoaModel.setSexo(pessoaModel.getSexo().substring(0, 1));
		this.pessoaModel = pessoaModel;
	}
 
	/*
	 * Método que atualiza o registro alterado e recarrega a lista de registros
	 */
	public void AlterarRegistro(){
		this.pessoaRepository.AlterarRegistro(this.pessoaModel);	
		this.init();
	}
	
	/*
	 * Método que exclui um registro pessoa do banco de dados e atualiza dataTable
	 * Ele é chamado pelo consultarPessoa.xhtml. Este método repassa o PessoalModel para o 
	 * Repository excluir
	 */
	public void ExcluirPessoa(PessoaModel pessoaModel) {
		this.pessoaRepository.ExcluirRegistro(pessoaModel.getCodigo());
		this.pessoas.remove(pessoaModel);
	}

}