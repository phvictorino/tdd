package br.com.caelum.leilao.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Leilao {

	private String descricao;
	private List<Lance> lances;

	public Leilao(String descricao) {
		this.descricao = descricao;
		this.lances = new ArrayList<Lance>();
	}

	public void propoe(Lance lance) {
		if (lance.getValor() <= 0) {
			throw new IllegalArgumentException("Não é possível propor um lance com valor menor ou igual a 0!");
		}
		
		if (lances.isEmpty() || podeFazerLance(lance)) {
			lances.add(lance);
		}
	}

	private boolean podeFazerLance(Lance lance) {
		return !getUltimoLance().getUsuario().equals(lance.getUsuario()) && qtdLancesDo(lance.getUsuario()) < 5;
	}

	private int qtdLancesDo(Usuario usuario) {
		int total = 0;

		for (Lance l : lances) {
			if (l.getUsuario().equals(usuario))
				total++;
		}
		return total;
	}

	private Lance getUltimoLance() {
		return lances.get(lances.size() - 1);
	}

	public void dobraLance(Usuario usuario) {
		Lance ultimo = ultimoLanceDo(usuario);
		propoe(new Lance(usuario, ultimo.getValor()*2));
	}

	private Lance ultimoLanceDo(Usuario usuario) {
		Lance ultimo = null;
		for (Lance lance : lances) {
			if (lance.getUsuario().equals(usuario)) ultimo = lance; 
		}
		return ultimo;
	}

	public String getDescricao() {
		return descricao;
	}

	public List<Lance> getLances() {
		return Collections.unmodifiableList(lances);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((lances == null) ? 0 : lances.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Leilao other = (Leilao) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (lances == null) {
			if (other.lances != null)
				return false;
		} else if (!lances.equals(other.lances))
			return false;
		return true;
	}

}
