package bc.ce.wcaquino;

import java.util.Date;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.servicos.LocacaoService;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {


	// RULE
	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	
	// MÉTODOS DE TESTES
	@Test
	public void testLocacao() throws Exception {
		// Cenário
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuário 1");
		Filme filme = new Filme("Filme 1", 2, 5.0);

		// Ação
		Locacao locacao = service.alugarFilme(usuario, filme);

		// Verificação
		error.checkThat(
				locacao.getValor(),
				Matchers.is(Matchers.equalTo(5.0)));
		error.checkThat(
				DataUtils.isMesmaData(locacao.getDataLocacao(),
						new Date()),
				Matchers.is(true));
		error.checkThat(
				DataUtils.isMesmaData(locacao.getDataRetorno(),
						DataUtils.obterDataComDiferencaDias(1)),
				Matchers.is(true));

	}


	// FORMA DE EXCESSÃO ELEGANTE
	@Test(expected = Exception.class)
	public void testLocacao_filmeSemEstoque() throws Exception {
		// Cenário
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuário 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		// Ação
		Locacao locacao = service.alugarFilme(usuario, filme);

	}


	// FORMA DE EXCESSÃO ROBUSTA
	@Test
	public void testLocacao_filmeSemEstoque2() {
		// Cenário
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuário 1");
		Filme filme = new Filme("Filme 1", 2, 5.0);

		// Ação
		try {
			Locacao locacao = service.alugarFilme(usuario, filme);
			Assert.fail("Deveria ter lançado uma excessão");
		} catch (Exception e) {
			MatcherAssert.assertThat(e.getMessage(), Matchers.is("Filme sem estoque"));
		}

	}


	// FORMA DE EXCESSÃO NOVA
	@Test
	public void testLocacao_filmeSemEstoque3() throws Exception {
		// Cenário
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuário 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		exception.expect(Exception.class);
		exception.expectMessage("Filme sem estoque");

		// Ação
		Locacao locacao = service.alugarFilme(usuario, filme);


	}
}