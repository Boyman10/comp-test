package org.example.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StatDTOTest {

    @Test
    public void test_getFormatFunds() {
        StatDTO dto = new StatDTO(10, 1_500_000_000);
        Assertions.assertEquals("1 500 000 000", dto.getFormatFunds());
    }

    @Test
    public void test_getFormatFunds_decimal() {
        StatDTO dto = new StatDTO(10, 1_500_000_000.20);
        Assertions.assertEquals("1 500 000 000.2", dto.getFormatFunds());
    }

    @Test
    public void test_getFormatFunds_rounded() {
        StatDTO dto = new StatDTO(10, 1_500_000_000.205);
        Assertions.assertEquals("1 500 000 000.21", dto.getFormatFunds());
    }
}