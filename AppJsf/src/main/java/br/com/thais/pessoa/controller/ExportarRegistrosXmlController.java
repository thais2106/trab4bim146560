package br.com.thais.pessoa.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.thais.model.PessoaModel;
import br.com.thais.repository.PessoaRepository;

/**
 * Classe para geração e download de arquivo
 * @author Thaís 
 * @since  25 de nov de 2016 - 20:30:30
 */
@Named(value = "exportarRegistrosXmlController")
@RequestScoped
public class ExportarRegistrosXmlController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	transient PessoaRepository pessoaRepository;

	private StreamedContent arquivoDownload;

	/*
	 * Método que realiza o download do arquivo XML
	 */
	public StreamedContent getArquivoDownload() {
		this.DownlaodArquivoXmlPessoa();
		return arquivoDownload;
	}

	/*
	 * Método que gera arquivo XML para exportação.
	 */
	private File GerarXmlPessoas() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		List<PessoaModel> pessoasModel = pessoaRepository.GetPessoas();
		Element elementPessoas = new Element("Pessoas");
		Document documentoPessoas = new Document(elementPessoas);

		pessoasModel.forEach(pessoa -> {
			Element elementPessoa = new Element("Pessoa");
			elementPessoa.addContent(new Element("codigo").setText(pessoa.getCodigo().toString()));
			elementPessoa.addContent(new Element("nome").setText(pessoa.getNome()));
			elementPessoa.addContent(new Element("sexo").setText(pessoa.getSexo()));

			String dataCadastroFormatada = pessoa.getDataCadastro().format(dateTimeFormatter);

			elementPessoa.addContent(new Element("dataCadastro").setText(dataCadastroFormatada));
			elementPessoa.addContent(new Element("email").setText(pessoa.getEmail()));
			elementPessoa.addContent(new Element("endereco").setText(pessoa.getEndereco()));
			elementPessoa.addContent(new Element("origemCadastro").setText(pessoa.getOrigemCadastro()));
			elementPessoa.addContent(new Element("usuarioCadastro").setText(pessoa.getUsuarioModel().getUsuario()));

			elementPessoas.addContent(elementPessoa);
		});

		XMLOutputter xmlGerado = new XMLOutputter();
		try {
			String nomeArquivo = "pessoas_".concat(java.util.UUID.randomUUID().toString()).concat(".xml");
			File arquivo = new File("C:\\Users\\Thaís\\git\\trab4bim146560\\banco\\".concat(nomeArquivo));
			FileWriter fileWriter = new FileWriter(arquivo);
			xmlGerado.output(documentoPessoas, fileWriter);
			return arquivo;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/*
	 * Método que prepara o arquivo para download
	 */
	public void DownlaodArquivoXmlPessoa() {
		File arquivoXml = this.GerarXmlPessoas();
		InputStream inputStream;

		try {
			inputStream = new FileInputStream(arquivoXml.getPath());
			arquivoDownload = new DefaultStreamedContent(inputStream, "application/xml", arquivoXml.getName());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
