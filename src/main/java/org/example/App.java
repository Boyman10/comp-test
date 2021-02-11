package org.example;

import org.example.utilities.StatsApi;
import org.example.utilities.http.HttpApiClient;
import org.example.utilities.json.BulkJsonParser;
import org.example.utilities.properties.HttpApiProperties;
import org.example.utilities.properties.PropertiesReader;

import java.io.File;
import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {

        StatsApi api = new StatsApi();

        HttpApiProperties info = PropertiesReader.loadProp();
        HttpApiClient client = new HttpApiClient(info, api);

        BulkJsonParser parser = new BulkJsonParser(client);
        parser.run(new File("src/main/resources/companies.json"));

        System.out.println("---------------------------------------");
        System.out.println("Country, # Companies, $ Average funding");
        System.out.println(api.getStats());
        System.out.println("---------------------------------------");

    }
}
