package org.example.entities;

public class StatDTO {

    public int companies;
    public double averageFunds;

    public StatDTO(int companies, double averageFunds) {
        this.companies = companies;
        this.averageFunds = averageFunds;
    }

    @Override
    public String toString() {
        return String.join(",", String.valueOf(companies), String.valueOf(averageFunds));
    }
}
