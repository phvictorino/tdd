package br.com.caelum.servico;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.caelum.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class LeilaoTest {

	@BeforeClass
	public static void testandoBeforeClass() {
		System.out.println("before class");
	}

	@AfterClass
	public static void testandoAfterClass() {
		System.out.println("after class");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void naoDeveProporLanceComValorZerado() {
		
		Usuario pedro = new Usuario("Pedro");
		
		Leilao leilao = new CriadorDeLeilao().para("Notebook Dell Vostro 5470").lance(pedro, -0).constroi();
		
		Avaliador avaliador = new Avaliador();
		
		avaliador.avalia(leilao);
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void naoDeveProporLanceComValorNegativo() {
		
		Usuario pedro = new Usuario("Pedro");
		
		Leilao leilao = new CriadorDeLeilao().para("Notebook Dell Vostro 5470").lance(pedro, -10).constroi();
		
		Avaliador avaliador = new Avaliador();
		
		avaliador.avalia(leilao);
		
	}

	@Test
	public void deveAdicionarLance() {

		Usuario pedro = new Usuario("Pedro");

		Leilao leilao = new CriadorDeLeilao().para("Notebook Dell Vostro 5470").lance(pedro, 3000.0).constroi();

		assertEquals(1, leilao.getLances().size());

	}

	//	@Test
	public void naoDeveAdicionarLance() {

		Leilao leilao = new CriadorDeLeilao().para("Notebook Dell Vostro 5470").constroi();

		assertEquals(0, leilao.getLances().size());

	}

	@Test
	public void deveAdicionarVariosLances() {

		Usuario pedro = new Usuario("Pedro");
		Usuario carlos = new Usuario("Carlos");
		Usuario magdiel = new Usuario("Magdiel");

		Leilao leilao = new CriadorDeLeilao().para("Notebook Dell Vostro 5470").lance(pedro, 3000.0).lance(magdiel, 1000.0).lance(carlos, 500.0).constroi();

		assertEquals(3, leilao.getLances().size());

	}

	@Test
	public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {

		Leilao leilao = new Leilao("Notebook Dell Vostro 5470");

		Usuario pedro = new Usuario("Pedro");

		leilao.propoe(new Lance(pedro, 3000.0));
		leilao.propoe(new Lance(pedro, 1000.0));

		assertEquals(1, leilao.getLances().size());

	}

	@Test
	public void naoDeveAceitarMaisQueCincoLancesDoMesmoUsuario() {

		Leilao leilao = new Leilao("Notebook Dell Vostro 5470");

		Usuario pedro = new Usuario("Pedro");
		Usuario magdiel = new Usuario("Magdiel");

		leilao.propoe(new Lance(pedro, 3000.0));
		leilao.propoe(new Lance(magdiel, 1000.0));

		leilao.propoe(new Lance(pedro, 3000.0));
		leilao.propoe(new Lance(magdiel, 1000.0));

		leilao.propoe(new Lance(pedro, 3000.0));
		leilao.propoe(new Lance(magdiel, 1000.0));

		leilao.propoe(new Lance(pedro, 3000.0));
		leilao.propoe(new Lance(magdiel, 1000.0));

		leilao.propoe(new Lance(pedro, 3000.0));
		leilao.propoe(new Lance(magdiel, 1000.0));

		leilao.propoe(new Lance(pedro, 3000.0));

		assertEquals(10, leilao.getLances().size());

	}

	@Test
	public void deveDobrarUltimoLanceDado() {

		Leilao leilao = new Leilao("Notebook Dell Vostro 5470");

		Usuario pedro = new Usuario("Pedro");
		Usuario magdiel = new Usuario("Magdiel");

		leilao.propoe(new Lance(pedro, 3000.0));
		leilao.propoe(new Lance(magdiel, 1000.0));

		leilao.propoe(new Lance(pedro, 2000.0));
		leilao.propoe(new Lance(magdiel, 1000.0));

		leilao.dobraLance(pedro);

		assertEquals(4000.0, leilao.getLances().get(4).getValor(), 0.00001);

	}

	@Test
	public void deveDobrarUltimoLanceDadoNoMaximoCincoLancesPorUsuario() {

		Leilao leilao = new Leilao("Notebook Dell Vostro 5470");

		Usuario pedro = new Usuario("Pedro");
		Usuario magdiel = new Usuario("Magdiel");

		leilao.propoe(new Lance(pedro, 3000.0));
		leilao.propoe(new Lance(magdiel, 1500.0));

		leilao.propoe(new Lance(pedro, 2000.0));
		leilao.propoe(new Lance(magdiel, 2000.0));

		leilao.propoe(new Lance(pedro, 6000.0));
		leilao.propoe(new Lance(magdiel, 8000.0));

		leilao.propoe(new Lance(pedro, 3500.0));
		leilao.propoe(new Lance(magdiel, 4500.0));

		leilao.dobraLance(pedro);
		leilao.dobraLance(magdiel);

		leilao.propoe(new Lance(pedro, 2000.0));
		leilao.propoe(new Lance(magdiel, 1000.0));

		assertEquals(10, leilao.getLances().size());
		assertEquals(7000.0, leilao.getLances().get(8).getValor(), 0.00001);
		assertEquals(9000.0, leilao.getLances().get(9).getValor(), 0.00001);

	}

}
