//package com.porto.ecor.mensageria.service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//import org.junit.jupiter.api.Test;
//
//import com.porto.ecor.mensageria.exception.ExponentialBackoffServiceException;
//
//public class ExponentialBackoffServiceTest {
//	
//	@Test
//    public void testeCalcularTempoTotalDeEsperaParaPrimeiraTentativa() {
//        assertEquals(60, ExponentialBackoffService.calculateTotalWaitTime(1),
//            "O tempo de espera para a primeira retentativa deve ser 60 segundos.");
//    }
//	
//    @Test
//    public void testeCalcularTempoTotalDeEsperaParaSegundaTentativa() {
//        assertEquals(180, ExponentialBackoffService.calculateTotalWaitTime(2),
//            "O tempo de espera para a terceira retentativa deve ser 180 segundos.");
//    }
//
//    @Test
//    public void testeCalcularTempoTotalDeEsperaParaTerceiraTentativa() {
//        assertEquals(360, ExponentialBackoffService.calculateTotalWaitTime(3),
//            "O tempo de espera para a terceira retentativa deve ser 360 segundos.");
//    }
//    
//    @Test
//    public void testeCalcularTempoTotalDeEsperaParaQuartaTentativa() {
//        assertEquals(1560, ExponentialBackoffService.calculateTotalWaitTime(4),
//            "O tempo de espera para a terceira retentativa deve ser 1560 segundos.");
//    }
//    
//    @Test
//    public void testeCalcularTempoTotalDeEsperaParaQuintaTentativa() {
//        assertEquals(3060, ExponentialBackoffService.calculateTotalWaitTime(5),
//            "O tempo de espera para a terceira retentativa deve ser 3060 segundos.");
//    }
//    
//    @Test
//    public void testeCalcularTempoTotalDeEsperaParaSextaTentativa() {
//        assertEquals(4860, ExponentialBackoffService.calculateTotalWaitTime(6),
//            "O tempo de espera para a terceira retentativa deve ser 4860 segundos.");
//    }
//
//    @Test
//    public void testeCalcularTempoTotalDeEsperaParaSetimaTentativa() {
//        assertEquals(6960, ExponentialBackoffService.calculateTotalWaitTime(7),
//            "O tempo de espera para a sétima retentativa deve ser 3060 segundos.");
//    }
//    
//    @Test
//    public void testeCalcularTempoTotalDeEsperaParaOitavaTentativa() {
//        assertEquals(11760, ExponentialBackoffService.calculateTotalWaitTime(8),
//            "O tempo de espera para a sétima retentativa deve ser 11760 segundos.");
//    }
//    
//    @Test
//    public void testeCalcularTempoTotalDeEsperaParaNonaTentativa() {
//        assertEquals(17160, ExponentialBackoffService.calculateTotalWaitTime(9),
//            "O tempo de espera para a sétima retentativa deve ser 3060 segundos.");
//    }
//
//    @Test
//    public void testeCalcularTempoTotalDeEsperaParaDecimaTentativa() {
//        assertEquals(23160, ExponentialBackoffService.calculateTotalWaitTime(10),
//            "O tempo de espera para a décima retentativa deve ser 11760 segundos.");
//    }
//    
//    @Test
//    public void testeCalcularTempoTotalDeEsperaParaDecimaPrimeiraTentativa() {
//        assertEquals(36360, ExponentialBackoffService.calculateTotalWaitTime(11),
//            "O tempo de espera para a sétima retentativa deve ser 36360 segundos.");
//    }
//
//    @Test
//    public void testeCalcularTempoTotalDeEsperaParaDecimaSegundaTentativa() {
//        assertEquals(50760, ExponentialBackoffService.calculateTotalWaitTime(12),
//            "O tempo de espera para a décima segunda retentativa deve ser 50760 segundos.");
//    }
//    
//    @Test
//    public void testeCalcularTempoTotalDeEsperaParaDecimaTerceiraTentativa() {
//        assertEquals(66360, ExponentialBackoffService.calculateTotalWaitTime(13),
//            "O tempo de espera para a décima segunda retentativa deve ser 66960 segundos.");
//    }
//
//    @Test
//    public void testeTentativaIlegalMenorQueUm() {
//        assertThrows(ExponentialBackoffServiceException.class, () -> ExponentialBackoffService.calculateTotalWaitTime(0),
//            "Deve lançar uma IllegalArgumentException para retentativas menores que 1.");
//    }
//    
//}
