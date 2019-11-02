package ru.krogot88.demorest.util;


import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * User: Сашок  Date: 02.11.2019 Time: 17:58
 */
public class Translator {

    public void sendGet() throws Exception {

        String url = "https://dictionary.yandex.net/api/v1/dicservice.json/lookup?key=dict.1.1.20191102T111827Z.a508174c28f9b8a4.ab3d686701ac32855169df99fc3bcc537e62fe2a&lang=en-ru&text=six";

        HttpURLConnection httpClient =
                (HttpURLConnection) new URL(url).openConnection();

        // optional default is GET
        httpClient.setRequestMethod("GET");
        //add request header
        httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(httpClient.getInputStream()))) {

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            System.out.println(response.toString());
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            Gson g = new Gson();
            WordYandex wordYandex = g.fromJson(response.toString(),WordYandex.class);
            System.out.println(wordYandex);
        }
    }
}
