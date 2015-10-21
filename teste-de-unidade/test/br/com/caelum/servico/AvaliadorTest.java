package br.com.caelum.servico;

import static org.junit.Assert.assertEquals;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class AvaliadorTest {

	private Avaliador avaliador;

	@Before
	public void setUp() {
		this.avaliador = new Avaliador();
		System.out.println("Iniciando teste.");
	}

	@After
	public void finaliza() {
		System.out.println("Finalizando.");
	}

	@Test
	public void deveCalcularValoresLancesOrdemCrescente() {

		Usuario joao = new Usuario("João");
		Usuario maria = new Usuario("Maria");
		Usuario pedro = new Usuario("Pedro");

		Leilao leilao = new Leilao("Primeiro leilão");

		leilao.propoe(new Lance(joao, 500.0));
		leilao.propoe(new Lance(maria, 300.0));
		leilao.propoe(new Lance(pedro, 250.0));

		avaliador.avalia(leilao);

		Double menorValorEsperado = 250.0;
		Double maiorValorEsperado = 500.0;

		assertThat(avaliador.getMenorValor(), equalTo(menorValorEsperado));
		assertThat(avaliador.getMaiorValor(), equalTo(maiorValorEsperado));

	}

	@Test(expected = RuntimeException.class)
	public void naoDeveAvaliarLeilaoSemLances() {

		Leilao leilao = new CriadorDeLeilao().para("Notebook Vostro 5470").constroi();

		avaliador.avalia(leilao);
	}

	@Test
	public void deveCalcularAMedia() {

		Usuario joao = new Usuario("João");
		Usuario maria = new Usuario("Maria");
		Usuario pedro = new Usuario("Pedro");

		Leilao leilao = new Leilao("Primeiro leilão");

		leilao.propoe(new Lance(joao, 500.0));
		leilao.propoe(new Lance(maria, 300.0));
		leilao.propoe(new Lance(pedro, 250.0));

		avaliador.avalia(leilao);

		Double mediaValoresEsperado = 350.0;

		assertEquals(mediaValoresEsperado, avaliador.getMediaValores());

	}

	@Test
	public void deveEntenderLeilaoComUmLance() {

		Usuario joao = new Usuario("João");

		Leilao leilao = new Leilao("Primeiro leilão");

		leilao.propoe(new Lance(joao, 500.0));

		avaliador.avalia(leilao);

		assertEquals(500, avaliador.getMaiorValor(), 0.00001);
		assertEquals(500, avaliador.getMenorValor(), 0.00001);

	}

	@Test
	public void deveEntenderValoresRandomicos() {

		Usuario joao = new Usuario("João");
		Usuario maria = new Usuario("Maria");
		Usuario pedro = new Usuario("Pedro");

		Leilao leilao = new Leilao("Primeiro leilão");

		leilao.propoe(new Lance(joao, 500.0));
		leilao.propoe(new Lance(maria, 300.0));
		leilao.propoe(new Lance(pedro, 250.0));
		leilao.propoe(new Lance(joao, 700.0));
		leilao.propoe(new Lance(maria, 800.0));
		leilao.propoe(new Lance(pedro, 950.0));
		leilao.propoe(new Lance(joao, 150.0));
		leilao.propoe(new Lance(maria, 50.0));
		leilao.propoe(new Lance(pedro, 170.0));

		avaliador.avalia(leilao);

		Double menorValorEsperado = 50.0;
		Double maiorValorEsperado = 950.0;

		assertEquals(menorValorEsperado, avaliador.getMenorValor());
		assertEquals(maiorValorEsperado, avaliador.getMaiorValor());

	}

	@Test
	public void deveCalcularValoresLancesOrdemDecrescente() {

		Usuario joao = new Usuario("João");
		Usuario maria = new Usuario("Maria");
		Usuario pedro = new Usuario("Pedro");

		Leilao leilao = new Leilao("Primeiro leilão");

		leilao.propoe(new Lance(joao, 200.0));
		leilao.propoe(new Lance(maria, 450.0));
		leilao.propoe(new Lance(pedro, 700.0));

		avaliador.avalia(leilao);

		Double menorValorEsperado = 200.0;
		Double maiorValorEsperado = 700.0;

		assertEquals(menorValorEsperado, avaliador.getMenorValor());
		assertEquals(maiorValorEsperado, avaliador.getMaiorValor());

	}

	@Test
	public void deveAcharTresMaiores() {

		Usuario joao = new Usuario("João");
		Usuario maria = new Usuario("Maria");
		Usuario pedro = new Usuario("Pedro");

		Leilao leilao = new Leilao("Primeiro leilão");

		leilao.propoe(new Lance(joao, 500.0));
		leilao.propoe(new Lance(maria, 300.0));
		leilao.propoe(new Lance(pedro, 250.0));
		leilao.propoe(new Lance(joao, 700.0));
		leilao.propoe(new Lance(maria, 800.0));
		leilao.propoe(new Lance(pedro, 950.0));
		leilao.propoe(new Lance(joao, 150.0));
		leilao.propoe(new Lance(maria, 50.0));
		leilao.propoe(new Lance(pedro, 170.0));

		avaliador.avalia(leilao);

		assertEquals(3, avaliador.getTresMaiores().size());

		assertThat(avaliador.getTresMaiores(), hasItems(new Lance(pedro, 950.0), new Lance(maria, 800.0), new Lance(joao, 700.0)));

//		assertEquals(950, avaliador.getTresMaiores().get(0).getValor(), 0.0001);
//		assertEquals(800, avaliador.getTresMaiores().get(1).getValor(), 0.0001);
//		assertEquals(700, avaliador.getTresMaiores().get(2).getValor(), 0.0001);

	}

	@Test
	public void deveAcharDoisMaiores() {

		Usuario joao = new Usuario("João");
		Usuario maria = new Usuario("Maria");
		//		Usuario pedro = new Usuario("Pedro");

		Leilao leilao = new Leilao("Primeiro leilão");

		leilao.propoe(new Lance(joao, 500.0));
		leilao.propoe(new Lance(maria, 300.0));

		avaliador.avalia(leilao);

		assertEquals(2, avaliador.getTresMaiores().size());
		assertEquals(500, avaliador.getTresMaiores().get(0).getValor(), 0.0001);
		assertEquals(300, avaliador.getTresMaiores().get(1).getValor(), 0.0001);

	}

	@Test
	public void testaMatematicaComMaiorQueTrinta() {
		MatematicaMaluca mat = new MatematicaMaluca();

		assertEquals(160, mat.contaMaluca(40));
	}

	@Test
	public void testaMatematicaComMaiorQueDez() {
		MatematicaMaluca mat = new MatematicaMaluca();

		assertEquals(90, mat.contaMaluca(30));
	}

	@Test
	public void testaMatematicaComMenorQueDez() {
		MatematicaMaluca mat = new MatematicaMaluca();

		assertEquals(10, mat.contaMaluca(5));
	}

	@Test
	public void naoDeveAdicionarAListaDoFiltroDeLances() {

		FiltroDeLances filtro = new FiltroDeLances();

		Usuario joao = new Usuario("João");
		Usuario maria = new Usuario("Maria");
		Usuario pedro = new Usuario("Pedro");

		Leilao leilao = new Leilao("Primeiro leilão");

		leilao.propoe(new Lance(joao, 500.0));
		leilao.propoe(new Lance(maria, 300.0));
		leilao.propoe(new Lance(pedro, 250.0));
		leilao.propoe(new Lance(joao, 700.0));
		leilao.propoe(new Lance(maria, 800.0));
		leilao.propoe(new Lance(pedro, 950.0));
		leilao.propoe(new Lance(joao, 150.0));
		leilao.propoe(new Lance(maria, 50.0));
		leilao.propoe(new Lance(pedro, 170.0));

		assertEquals(0, filtro.filtra(leilao.getLances()).size());

	}

	@Test
	public void deveAdicionarAListaDoFiltroDeLances() {

		FiltroDeLances filtro = new FiltroDeLances();

		Usuario joao = new Usuario("João");
		Usuario maria = new Usuario("Maria");
		Usuario pedro = new Usuario("Pedro");

		Leilao leilao = new Leilao("Primeiro leilão");

		leilao.propoe(new Lance(joao, 2000.0));
		leilao.propoe(new Lance(maria, 300.0));
		leilao.propoe(new Lance(pedro, 25000.0));

		assertEquals(2, filtro.filtra(leilao.getLances()).size());

	}

}
