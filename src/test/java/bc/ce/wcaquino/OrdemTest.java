package bc.ce.wcaquino;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

// ANOTAÇÃO PARA EXECUTAR OS TESTES EM ORDEM ALFABÉTICA
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrdemTest {

    public static int contador = 0;

    @Test
    public void iniciar(){
        contador = 1;
    }

    @Test
    public void verificar(){
        Assert.assertEquals(1, contador);
    }
    
}
