import lombok.experimental.UtilityClass;

import java.util.Random;
import java.util.logging.Logger;

@UtilityClass
public class GeradorSeuNumeroUtil {
    private static final String CARACTERES_ALFANUMERICOS = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final short COMPRIMENTO_IDENTIFICADOR_SEU_NUMERO = 20;


    public String gerarIdentificadorSeuNumero() {
        var identificador = new StringBuilder(COMPRIMENTO_IDENTIFICADOR_SEU_NUMERO);
        try {
            var random = new Random(COMPRIMENTO_IDENTIFICADOR_SEU_NUMERO);//SecureRandom.getInstanceStrong();

            for (var i = 0; i < COMPRIMENTO_IDENTIFICADOR_SEU_NUMERO; i++) {
                var index = random.nextInt(CARACTERES_ALFANUMERICOS.length());
                identificador.append(CARACTERES_ALFANUMERICOS.charAt(index));
            }
            return identificador.toString();
        } catch (Exception e) {
            Logger.getLogger(GeradorSeuNumeroUtil.class.getName()).severe(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
