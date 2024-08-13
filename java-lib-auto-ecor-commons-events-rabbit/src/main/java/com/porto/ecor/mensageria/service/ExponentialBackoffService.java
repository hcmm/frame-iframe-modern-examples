package com.porto.ecor.mensageria.service;

import com.porto.ecor.mensageria.exception.ExponentialBackoffServiceException;

public class ExponentialBackoffService {
   
	private static final int INITIAL_BASE = 1;
    private static final int SECOND_BASE = 2;
    private static final int THIRD_BASE = 4;
    private static final int FOURTH_BASE = 8;
    
    /**
     * Calcula Tempo Determinado de Espera com a politica de Exponencial BackOff
     *
     * @param attempt número da tentativa
     * @return o tempo total de espera acumulado até a tentativa especificada, em segundos.
     * @throws ExponentialBackoffServiceException se o número da tentativa for menor que 1.
     */
    public static int calculateTotalWaitTime(int attempt) {
        if (attempt < 1) {
            throw new ExponentialBackoffServiceException("Retentativa deve ser maior que 0.");
        }

        int totalWaitTime = 0;

        for (int i = 1; i <= attempt; i++) {
            int base = getBaseForAttempt(i);
            totalWaitTime += i * base;
        }

        return totalWaitTime;
    }
    
    /**
     * Determina o valor da base dependendo do número da tentativa.
     *
     * @param attempt número da tentativa
     * @return valor da base de tempo para a tentativa especificada
     */
    private static int getBaseForAttempt(int attempt) {
        if (attempt >= 11) {
            return FOURTH_BASE;
        } else if (attempt >= 8) {
            return THIRD_BASE;
        } else if (attempt >= 4) {
            return SECOND_BASE;
        } else {
            return INITIAL_BASE;
        }
    }
}
