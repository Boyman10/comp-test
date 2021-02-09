package org.example.utilities.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.entities.Server;

public class JsonParser {

    public static Server parseServer(String json) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, Server.class);
    }
}
