package br.com.thais.pessoa.controller;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.primefaces.model.UploadedFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
	
	private UploadedFile file;
	
	/*
	 * Captura arquivo XML
	 */
	public UploadedFile getFile() {
		return file;
	}
 
	/*
	 * Recebe arquivo XML
	 */
	public void setFile(UploadedFile file) {
		this.file = file;
	}

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
	
	/*
	 * Método que processa um arquivo XML através do componente fileUpload do PrimeFaces. 
	 * Os valores do arquivo XML são capturados e repassados para um objeto PessoaModel, 
	 * que é salvo pela classe PessoaRepository.
	 */
	public void UploadRegistros() {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			
			if (this.file.getFileName().equals("")) {
				Uteis.MensagemAtencao("Nenhum arquivo selecionado!");
				return;
			}

			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(this.file.getInputstream());
			Element element = doc.getDocumentElement();
			NodeList nodes = element.getChildNodes();

			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element elementPessoa = (Element) node;

					String nome = elementPessoa.getElementsByTagName("nome").item(0).getChildNodes().item(0)
							.getNodeValue();
					String sexo = elementPessoa.getElementsByTagName("sexo").item(0).getChildNodes().item(0)
							.getNodeValue();
					String email = elementPessoa.getElementsByTagName("email").item(0).getChildNodes().item(0)
							.getNodeValue();
					String endereco = elementPessoa.getElementsByTagName("endereco").item(0).getChildNodes().item(0)
							.getNodeValue();

					PessoaModel newPessoaModel = new PessoaModel();
					newPessoaModel.setUsuarioModel(this.usuarioController.GetUsuarioSession());
					newPessoaModel.setEmail(email);
					newPessoaModel.setEndereco(endereco);
					newPessoaModel.setNome(nome);
					newPessoaModel.setOrigemCadastro("X");
					newPessoaModel.setSexo(sexo);
					pessoaRepository.SalvarNovoRegistro(newPessoaModel);
				}
			}

			Uteis.MensagemInfo("Registros cadastrados com sucesso!");

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}