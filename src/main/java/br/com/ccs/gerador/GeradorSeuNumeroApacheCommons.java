package br.com.ccs.gerador;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.logging.Logger;

@UtilityClass
public class GeradorSeuNumeroApacheCommons {
    private static final short COMPRIMENTO_IDENTIFICADOR_SEU_NUMERO = 20;

    public String gerarIdentificadorSeuNumero() {
        try {
            return RandomStringUtils.randomAlphanumeric(COMPRIMENTO_IDENTIFICADOR_SEU_NUMERO);
        } catch (Exception e) {
            Logger.getLogger(GeradorSeuNumeroApacheCommons.class.getName()).severe(e.getMessage());
            throw new RuntimeException(e);
        }

    }
}
