package org.example;

import org.example.utility.ApiClient;
import org.example.utility.ApiInfo;
import org.example.utility.JsonParser;
import org.example.utility.PropertiesReader;

import java.io.File;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {

        ApiInfo info = PropertiesReader.loadProp();
        System.out.println(info);

        JsonParser parser = new JsonParser();
        parser.parseJson(new File("src/main/resources/companies.json"));

        ApiClient client = new ApiClient();
        parser.subscribe(client);

        //   ApiClient.getJson(info.getFullUri()); // Get element from QUEUE !!!


    }
}
