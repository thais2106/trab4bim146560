package br.com.thais.pessoa.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.thais.model.PessoaModel;
import br.com.thais.repository.PessoaRepository;
import br.com.thais.usuario.controller.UsuarioController;
import br.com.thais.uteis.Uteis;

/**
 * Classe controller para salvar um registro Pessoa
 * 
 * @author Thaís 
 * @since  12 de nov de 2016 - 17:36:02
 */
@Named(value = "cadastrarPessoaController")
@RequestScoped
public class CadastrarPessoaController {

	@Inject
	PessoaModel pessoaModel;

	@Inject
	UsuarioController usuarioController;

	@Inject
	PessoaRepository pessoaRepository;

	/* 
	 * Método getter, que captura PessoaModel
	 */
	public PessoaModel getPessoaModel() {
		return pessoaModel;
	}

	/*
	 * Método setter, que configura PessoaModel
	 */
	public void setPessoaModel(PessoaModel pessoaModel) {
		this.pessoaModel = pessoaModel;
	}

	/*
	 * Salva um registro Pessoa através de input. É capturado a sessão do usuário pelo usuarioController
	 * e informada a origem do cadastro, no caso, sendo repassado um I para informar que é via input.
	 * O objeto é repassado para a classe PessoaRepository para ser persistido.
	 */
	public void SalvarNovaPessoa() {
		pessoaModel.setUsuarioModel(this.usuarioController.GetUsuarioSession());
		pessoaModel.setOrigemCadastro("I");
		pessoaRepository.SalvarNovoRegistro(this.pessoaModel);

		this.pessoaModel = null;
		Uteis.MensagemInfo("Registro cadastrado com sucesso");
	}
}
