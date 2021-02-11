package org.example.utilities;

import org.example.entities.Company;
import org.example.entities.Server;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StatsApiTest {

    private StatsApi api;
    private Server server;

    @BeforeEach
    public void init() {
        api = new StatsApi();
        server = new Server();
        server.country = "France";

        Company comp = new Company();
        comp.totalMoneyRaised = 10.0;
        comp.server = server;
        Company comp1 = new Company();
        comp1.totalMoneyRaised = 11.0;
        comp1.server = server;
        Company comp2 = new Company();
        comp2.totalMoneyRaised = 12.0;
        comp2.server = server;

        api.addCompany(comp);
        api.addCompany(comp1);
        api.addCompany(comp2);
    }

    @Test
    public void test_compute_average_funds() {
        double result = StatsApi.computeAverage(10, 2, 8);
        Assertions.assertEquals(9, result);
    }

    @Test
    public void test_compute_average_funds_multiple() {
        double result = StatsApi.computeAverage(10, 4, 8);
        Assertions.assertEquals(9.5, result);
    }

    @Test
    public void test_compute_average_funds_multiple_rounded() {
        double result = StatsApi.computeAverage(12.25f, 4, 12);
        Assertions.assertEquals(12.19, result);
    }

    @Test
    public void test_add_company_null_funds() {
        Company comp = new Company();
        comp.totalMoneyRaised = 0.0;
        comp.server = server;
        api.addCompany(comp);
        Assertions.assertEquals("France,3,11.0\n", api.getStats());
    }
}