package br.com.ccs.gerador;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static java.text.NumberFormat.getInstance;

class GeradorSeuNumeroUtilThreadSafeTest {

    @Test
    public void testGeracaoSemColisoes() throws ExecutionException, InterruptedException {
        final int numeroDeCombinacoes = 100_000;
        final var threads = Runtime.getRuntime().availableProcessors();
        final var futures = new CompletableFuture[threads];
        final var combinacoes = ConcurrentHashMap.newKeySet(numeroDeCombinacoes * threads);

        for (int t = 0; t < threads; t++) {
            futures[t] = CompletableFuture.runAsync(() -> {
                        for (int i = 0; i < numeroDeCombinacoes; i++) {
                            String combinacao = GeradorSeuNumeroUtilThreadSafe.gerarIdentificadorSeuNumero();
                            combinacoes.add(combinacao);

                            if (i % 10_000 == 0) {
                                System.out.println("\n >>>>>> " + Thread.currentThread() + " Combinações Geradas: " + getInstance().format(i));
                            }
                        }
                    },
                    Executors.newVirtualThreadPerTaskExecutor());
        }

        CompletableFuture.allOf(futures).get();
        System.out.println("Total combinações geradas: " + getInstance().format(combinacoes.size()));
        Assertions.assertEquals(numeroDeCombinacoes * futures.length, combinacoes.size());
    }

}