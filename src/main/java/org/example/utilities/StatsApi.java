package org.example.utilities;

import org.example.entities.Company;
import org.example.entities.StatDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * Takes care of the printout - We call the computeAverage method each time we processed a company
 */
public class StatsApi {

    private final Map<String, StatDTO> stats = new HashMap<>();

    public static double computeAverage(double average, int number, double additionalFund) {
        return Math.round(((average * (number - 1) + additionalFund) / number) * 100.0) / 100.0;
    }

    public void addCompany(Company company) {
        if (company.totalMoneyRaised != null && company.totalMoneyRaised > 0) {
            if (stats.containsKey(company.server.country)) {
                StatDTO currentStat = stats.get(company.server.country);
                currentStat.companies++;
                currentStat.averageFunds = computeAverage(currentStat.averageFunds, currentStat.companies, company.totalMoneyRaised);

            } else {
                stats.put(company.server.country, new StatDTO(1, company.totalMoneyRaised));
            }
        }
    }

    public String getStats() {
        StringBuilder result = new StringBuilder();
        stats.forEach((key, value) -> result.append(String.join(",", key, value.toString())).append("\n"));

        return result.toString();
    }
}
