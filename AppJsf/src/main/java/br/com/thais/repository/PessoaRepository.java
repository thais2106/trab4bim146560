package br.com.thais.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.thais.model.PessoaModel;
import br.com.thais.model.UsuarioModel;
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
	
	
	/*
	 * Método que consulta todas as pessoas cadastradas em tb_pessoa através da query PessoaEntity.findAll
	 * declarada na entidade PessoaEntity.
	 */
	public List<PessoaModel> GetPessoas() {

		List<PessoaModel> pessoasModel = new ArrayList<PessoaModel>();

		entityManager = Uteis.JpaEntityManager();
		Query query = entityManager.createNamedQuery("PessoaEntity.findAll");

		@SuppressWarnings("unchecked")
		Collection<PessoaEntity> pessoasEntity = (Collection<PessoaEntity>) query.getResultList();

		PessoaModel pessoaModel = null;

		for (PessoaEntity pessoaEntity : pessoasEntity) {

			pessoaModel = new PessoaModel();
			pessoaModel.setCodigo(pessoaEntity.getCodigo());
			pessoaModel.setDataCadastro(pessoaEntity.getDataCadastro());
			pessoaModel.setEmail(pessoaEntity.getEmail());
			pessoaModel.setEndereco(pessoaEntity.getEndereco());
			pessoaModel.setNome(pessoaEntity.getNome());

			if (pessoaEntity.getOrigemCadastro().equals("X"))
				pessoaModel.setOrigemCadastro("XML");
			else
				pessoaModel.setOrigemCadastro("INPUT");

			if (pessoaEntity.getSexo().equals("M"))
				pessoaModel.setSexo("Masculino");
			else
				pessoaModel.setSexo("Feminino");

			UsuarioEntity usuarioEntity = pessoaEntity.getUsuarioEntity();
			
			UsuarioModel usuarioModel = new UsuarioModel();
			usuarioModel.setUsuario(usuarioEntity.getUsuario());

			pessoaModel.setUsuarioModel(usuarioModel);
			pessoasModel.add(pessoaModel);
		}
		return pessoasModel;
	}
	
	/*
	 * Consulta e captura um registro pessoa do banco de dados
	 */
	private PessoaEntity GetPessoa(int codigo){
		entityManager =  Uteis.JpaEntityManager();
		return entityManager.find(PessoaEntity.class, codigo);
	}
 
	/*
	 * Altera um registro pessoa no banco de dados
	 */
	public void AlterarRegistro(PessoaModel pessoaModel){
		entityManager =  Uteis.JpaEntityManager();
 
		PessoaEntity pessoaEntity = this.GetPessoa(pessoaModel.getCodigo());
		pessoaEntity.setEmail(pessoaModel.getEmail());
		pessoaEntity.setEndereco(pessoaModel.getEndereco());
		pessoaEntity.setNome(pessoaModel.getNome());
		pessoaEntity.setSexo(pessoaModel.getSexo());
 
		entityManager.merge(pessoaEntity);
	}
	
	/*
	 * Exclui um registro pessoa do banco de dados
	 */
	public void ExcluirRegistro(int codigo) {
		entityManager = Uteis.JpaEntityManager();
		PessoaEntity pessoaEntity = this.GetPessoa(codigo);
		entityManager.remove(pessoaEntity);
	}
	
	public Hashtable<String, Integer> GetOrigemPessoa() {
		Hashtable<String, Integer> hashtableRegistros = new Hashtable<String, Integer>();
		entityManager = Uteis.JpaEntityManager();
		
		Query query = entityManager.createNamedQuery("PessoaEntity.GroupByOrigemCadastro");

		@SuppressWarnings("unchecked")
		Collection<Object[]> collectionRegistros = (Collection<Object[]>) query.getResultList();

		for (Object[] objects : collectionRegistros) {
			String tipoPessoa = (String) objects[0];
			int totalDeRegistros = ((Number) objects[1]).intValue();
			if (tipoPessoa.equals("X"))
				tipoPessoa = "XML";
			else
				tipoPessoa = "INPUT";
			hashtableRegistros.put(tipoPessoa, totalDeRegistros);
		}
		return hashtableRegistros;
	}
}
