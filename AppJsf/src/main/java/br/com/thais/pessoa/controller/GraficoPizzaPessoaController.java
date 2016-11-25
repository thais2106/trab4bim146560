package br.com.thais.pessoa.controller;

import java.util.Hashtable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.PieChartModel;

import br.com.thais.repository.PessoaRepository;

/**
 * Classe do gráfico de pizza para a entidade Pessoa
 * @author Thaís 
 * @since  25 de nov de 2016 - 20:10:44
 */
@Named(value="graficoPizzaPessoaController")
@RequestScoped
public class GraficoPizzaPessoaController {
 
	@Inject
	private PessoaRepository pessoaRepository;
 
 
	private PieChartModel pieChartModel;
 
	public PieChartModel getPieChartModel() {
		return pieChartModel;
	}
	
	/*
	 * Método chamado após a construção do objeto, monta o gráfico
	 */
	@PostConstruct
	public void init(){
		this.pieChartModel = new PieChartModel();
		this.MontaGrafico();
	}
 
	/*
	 * Método que consulta os dados e monta o gráfico na tela
	 */
	private void MontaGrafico(){
		Hashtable<String, Integer> hashtableRegistros = pessoaRepository.GetOrigemPessoa();
		hashtableRegistros.forEach((chave,valor) -> {
			pieChartModel.set(chave, valor);
		});
		pieChartModel.setTitle("Total de Pessoas cadastrado por Tipo");
		pieChartModel.setShowDataLabels(true);
		pieChartModel.setLegendPosition("e");
	}
}
