package com.porto.ecor.mensageria.config;


import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.BackOffContext;
import org.springframework.retry.backoff.BackOffInterruptedException;
import org.springframework.retry.backoff.Sleeper;
import org.springframework.retry.backoff.SleepingBackOffPolicy;

import com.porto.ecor.mensageria.service.ExponentialBackoffService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomBackOffPolicy implements SleepingBackOffPolicy<CustomBackOffPolicy> {

    private final Sleeper sleeper;

    public CustomBackOffPolicy(Sleeper sleeper) {
        this.sleeper = sleeper;
    }

    private static class CustomBackOffContext implements BackOffContext {
        private static final long serialVersionUID = 1L;
        private int retryCount = 1;

        public int getRetryCount() {
            return retryCount;
        }

        public void incrementRetryCount() {
            this.retryCount++;
        }
    }

    @Override
    public BackOffContext start(RetryContext context) {
        log.info("Iniciando contexto de backoff para operação de retry.");
        return new CustomBackOffContext();
    }

    @Override
    public void backOff(BackOffContext backOffContext) throws BackOffInterruptedException {
        CustomBackOffContext context = (CustomBackOffContext) backOffContext;
        int attempt = context.getRetryCount();
        int waitTime = ExponentialBackoffService.calculateTotalWaitTime(attempt);
        log.info("Aguardando {} segundos antes da tentativa {}", waitTime, attempt);
        try {
            sleeper.sleep(waitTime * 1000L);
            context.incrementRetryCount();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Thread interrompida durante o backoff", e);
            throw new BackOffInterruptedException("Thread interrompida durante o backoff", e);
        }
    }

    @Override
    public CustomBackOffPolicy withSleeper(Sleeper sleeper) {
        return new CustomBackOffPolicy(sleeper);
    }
}
