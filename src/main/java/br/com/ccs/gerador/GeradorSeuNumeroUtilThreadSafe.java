package br.com.ccs.gerador;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;
import java.util.logging.Logger;

@UtilityClass
public class GeradorSeuNumeroUtilThreadSafe {
    private static final String CARACTERES_ALFANUMERICOS = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final short COMPRIMENTO_IDENTIFICADOR_SEU_NUMERO = 20;

    public String gerarIdentificadorSeuNumero() {
        var identificador = new StringBuilder(COMPRIMENTO_IDENTIFICADOR_SEU_NUMERO);
        try {
            var random = SecureRandom.getInstanceStrong();

            for (var i = 0; i < COMPRIMENTO_IDENTIFICADOR_SEU_NUMERO; i++) {
                var index = random.nextInt(CARACTERES_ALFANUMERICOS.length());
                identificador.append(CARACTERES_ALFANUMERICOS.charAt(index));
            }
            return identificador.toString();
        } catch (Exception e) {
            Logger.getLogger(GeradorSeuNumeroUtilThreadSafe.class.getName()).severe(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
