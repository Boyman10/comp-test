package org.example.entities;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class StatDTO {

    public int companies;
    public double averageFunds;

    public StatDTO(int companies, double averageFunds) {
        this.companies = companies;
        this.averageFunds = averageFunds;
    }

    public String getFormatFunds() {

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setDecimalSeparator('.');
        symbols.setGroupingSeparator(' ');
        String pattern = "#,##0.###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);

        return decimalFormat.format(Math.round(averageFunds * 100) / 100.0);
    }

    @Override
    public String toString() {
        return String.join(",", String.valueOf(companies), getFormatFunds());
    }
}
