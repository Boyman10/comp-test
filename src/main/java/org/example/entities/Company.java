package org.example.entities;

import com.google.gson.annotations.SerializedName;

import java.net.URI;

public class Company {

    @SerializedName("homepage_url")
    public URI homepageUrl;
    @SerializedName("total_money_raised")
    public Float totalMoneyRaised;

    public String name;
    public Server server;

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + "', " +
                "homepageUrl='" + getDomainName() + '\'' +
                ", totalMoneyRaised='" + totalMoneyRaised + '\'' +
                ", country='" + server.country + '\'' +
                '}';
    }

    public String getDomainName() {
        if (this.homepageUrl != null) {
            String hostname = this.homepageUrl.getHost();

            if (hostname != null) {
                return hostname.startsWith("www.") ? hostname.substring(4) : hostname;
            } else {
                return this.homepageUrl.toString();
            }
        } else {
            return null;
        }

    }
}
