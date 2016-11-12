package br.com.thais.repository;

import java.time.LocalDateTime;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.thais.model.PessoaModel;
import br.com.thais.repository.entity.PessoaEntity;
import br.com.thais.repository.entity.UsuarioEntity;
import br.com.thais.uteis.Uteis;

/**
 * Classe PessoaRepository que captura os dados da tabela tb_pessoa e repassa para as outras classes e vice-versa
 * 
 * @author Thaís 
 * @since  12 de nov de 2016 - 17:30:01
 */
public class PessoaRepository {
	 
	@Inject
	PessoaEntity pessoaEntity;
	EntityManager entityManager;
 
	/*
	 * Persiste um novo registro PessoaEntity no banco de dados pelo EntityManager da classe Uteis
	 */
	public void SalvarNovoRegistro(PessoaModel pessoaModel){
 
		entityManager =  Uteis.JpaEntityManager();
 
		pessoaEntity = new PessoaEntity();
		pessoaEntity.setDataCadastro(LocalDateTime.now());
		pessoaEntity.setEmail(pessoaModel.getEmail());
		pessoaEntity.setEndereco(pessoaModel.getEndereco());
		pessoaEntity.setNome(pessoaModel.getNome());
		pessoaEntity.setOrigemCadastro(pessoaModel.getOrigemCadastro());
		pessoaEntity.setSexo(pessoaModel.getSexo());
 
		UsuarioEntity usuarioEntity = entityManager.find(UsuarioEntity.class, pessoaModel.getUsuarioModel().getCodigo()); 
 
		pessoaEntity.setUsuarioEntity(usuarioEntity);
		entityManager.persist(pessoaEntity);
	}
}
