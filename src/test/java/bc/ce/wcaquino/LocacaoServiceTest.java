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
import br.ce.wcaquino.excetpions.FilmeSemEstoqueException;
import br.ce.wcaquino.excetpions.LocadoraException;
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
	@Test(expected = FilmeSemEstoqueException.class)
	public void testLocacao_filmeSemEstoque() throws Exception {
		// Cenário
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuário 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		// Ação
		Locacao locacao = service.alugarFilme(usuario, filme);

	}

	// TESTE USUARIO COM A FORMA ROBUSTA
	@Test
	public void testeLocacao_usuarioVazio() throws FilmeSemEstoqueException {
		// Cenário
		LocacaoService service = new LocacaoService();
		Filme filme = new Filme("Filme 2", 1, 4.0);

		// Ação
		try {
			Locacao locacao = service.alugarFilme(null, filme);
			Assert.fail();
		} catch (LocadoraException e) {
			MatcherAssert.assertThat(e.getMessage(), Matchers.is("Usuário vazio"));
		}
	}

	// TESTE FILME COM A FORMA NOVA
	@Test
	public void testeLocacao_filmeVazio() throws FilmeSemEstoqueException, LocadoraException {
		// Cenário
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuário 1");

		// Ação
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");

		Locacao locacao = service.alugarFilme(usuario, null);

	}

}