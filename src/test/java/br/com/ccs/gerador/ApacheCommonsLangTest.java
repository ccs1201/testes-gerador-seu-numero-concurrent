package br.com.ccs.gerador;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

import static java.text.NumberFormat.getInstance;

class ApacheCommonsLangTest {

    @Test
    public void testGeracaoSemColisoes() throws ExecutionException, InterruptedException {
        var startAt = System.currentTimeMillis();
        final int numeroDeCombinacoes = 100_000;
        final var threads = Runtime.getRuntime().availableProcessors();
        final var futures = new CompletableFuture[threads];
        final var combinacoes = ConcurrentHashMap.newKeySet(numeroDeCombinacoes * threads);

        for (int t = 0; t < threads; t++) {
            futures[t] = CompletableFuture.runAsync(() -> {
                        for (int i = 0; i < numeroDeCombinacoes; i++) {
                            String combinacao = RandomStringUtils.randomAlphanumeric(20);
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
        var endAt = System.currentTimeMillis();
        System.out.println("Tempo total -> " + (endAt - startAt));

        Pattern pattern = Pattern.compile("[a-zA-Z0-9]{20}");

        combinacoes.parallelStream().forEach(object -> {
            Assertions.assertTrue(pattern.matcher(object.toString()).matches());
            System.out.println(object);
        });
    }
}