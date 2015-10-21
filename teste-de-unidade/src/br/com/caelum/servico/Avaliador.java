package br.com.caelum.servico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;

public class Avaliador {
	
	private Double maiorValorLancado = Double.NEGATIVE_INFINITY;
	private Double menorValorLancado = Double.POSITIVE_INFINITY;
	private Integer quantidadeLances = 0;
	private Double somaValoresLancados = Double.MIN_NORMAL;
	List<Lance> maiores;

	public void avalia(Leilao leilao) {
		
		if (leilao.getLances().size() == 0) {
			throw new RuntimeException("Não é possível avaliar um leilão sem lances!");
		}
		
		quantidadeLances = leilao.getLances().size();
		for (Lance l : leilao.getLances()) {
			somaValoresLancados += l.getValor();
			if (l.getValor() > maiorValorLancado) maiorValorLancado = l.getValor();
			if (l.getValor() < menorValorLancado) menorValorLancado  = l.getValor();
		}
		pegaMaiores(leilao);
	}
	
	public void pegaMaiores(Leilao leilao) {
		maiores = new ArrayList<Lance>(leilao.getLances());
		Collections.sort(maiores, new Comparator<Lance>() {

			public int compare(Lance o1, Lance o2) {
				if (o1.getValor() < o2.getValor()) return 1;
				if (o1.getValor() > o2.getValor()) return -1;
				return 0;
			}
			
		});
		maiores = maiores.subList(0, maiores.size() > 3 ? 3 : maiores.size());
	}
	
	public List<Lance> getTresMaiores() {
		return this.maiores;
	}
	
	public Double getMaiorValor() {
		return maiorValorLancado;
	}
	
	public Double getMenorValor() {
		return menorValorLancado;
	}
	
	public Double getMediaValores() {
		return somaValoresLancados / quantidadeLances;
	}

}
