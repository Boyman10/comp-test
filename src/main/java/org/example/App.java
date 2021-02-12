package org.example;

import org.example.entities.properties.HttpApiProperties;
import org.example.utilities.StatsApi;
import org.example.utilities.http.HttpApiClient;
import org.example.utilities.properties.PropertiesReader;

import java.io.File;
import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {

        StatsApi api = new StatsApi();

        HttpApiProperties info = PropertiesReader.loadProp();
        HttpApiClient client = new HttpApiClient(info, api);

        BulkCompanyProcessor parser = new BulkCompanyProcessor(client, info.limit);
        parser.run(new File("src/main/resources/companies.json"));

        System.out.println("---------------------------------------");
        System.out.println("Country, # Companies, $ Average funding");
        System.out.println(api.getStats());
        System.out.println("---------------------------------------");

    }
}
