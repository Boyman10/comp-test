package org.example.utilities.json;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MoneyDeserializerTest {

    private MoneyDeserializer deserializer;

    @BeforeEach
    public void init() {
        deserializer = new MoneyDeserializer();
    }

    @Test
    public void test_get_index_on_currency() {
        int index = deserializer.getIndexOfCurrency("$15200");
        Assertions.assertEquals(1, index);
    }

    @Test
    public void test_get_index_on_missing_currency() {
        int index = deserializer.getIndexOfCurrency("15200");
        Assertions.assertEquals(0, index);
    }

    @Test
    public void test_get_index_on_unknown_currency() {
        int index = deserializer.getIndexOfCurrency("P15200");
        Assertions.assertEquals(0, index);
    }

    @Test
    public void test_formatFunds$() {
        Double funds = deserializer.formatFunds("$15200");
        Assertions.assertEquals(15200, funds);
    }

    @Test
    public void test_formatFunds€() {
        Double funds = deserializer.formatFunds("€15B");
        Assertions.assertEquals(15_000_000_000L, funds);
    }
}