package bc.ce.wcaquino;

import java.util.Date;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.servicos.LocacaoService;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {

	@Test
	public void test() {
		// Cenário
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuário 1");
		Filme filme = new Filme("Filme 1", 2, 5.0);

		// Ação
		Locacao locacao = service.alugarFilme(usuario, filme);

		// Verificação
		MatcherAssert.assertThat(locacao.getValor(), Matchers.is(Matchers.equalTo(5.0)));
		MatcherAssert.assertThat(locacao.getValor(), Matchers.is(Matchers.not(6.0)));
		MatcherAssert.assertThat(DataUtils.isMesmaData(
				locacao.getDataLocacao(), new Date()), Matchers.is(true));
		MatcherAssert.assertThat(
				DataUtils.isMesmaData(locacao.getDataRetorno(),
						DataUtils.obterDataComDiferencaDias(1)),
				Matchers.is(true));

	}
}