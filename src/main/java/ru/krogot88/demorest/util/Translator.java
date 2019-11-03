package ru.krogot88.demorest.util;


import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import ru.krogot88.demorest.util.yandex.PartOfSpeech;
import ru.krogot88.demorest.util.yandex.WordYandexJSON;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;

/**
 * User: Сашок  Date: 02.11.2019 Time: 17:58
 */
@Component
public class Translator {

    @Autowired
    private MessageSource ms;

    private List<String> sendGet(String englishWord) throws  Exception {
        List<String>  translates = new LinkedList<>();
        String url =ms.getMessage("yandex.url",null, Locale.getDefault()) +
                "?" +
                        "key=" +
                        ms.getMessage("yandex.key",null, Locale.getDefault()) +
                    "&" +
                        "lang=" +
                        ms.getMessage("yandex.lang",null, Locale.getDefault()) +
                    "&" +
                        "text=" + englishWord;

        HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(httpClient.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            Gson g = new Gson();
            WordYandexJSON wordYandexJSON = g.fromJson(response.toString(), WordYandexJSON.class);

            int maxSize = 0;
            for (PartOfSpeech ps: wordYandexJSON.def) {
                if(ps.tr.size() > maxSize) {
                    maxSize = ps.tr.size();
                }
            }
            for(int i = 0; i < maxSize;i++) {
                for(PartOfSpeech ps : wordYandexJSON.def) {
                    if(ps.tr.size() > i) {
                        translates.add(ps.tr.get(i).text);
                    }
                }
            }
        }
        return translates;
    }

    public void getWordsFromYandex() {
        PrintWriter printWriter = null;
        try {
           printWriter = new PrintWriter(ResourceUtils.getFile("classpath:sql/5001.txt"));
        } catch (IOException e) {    e.printStackTrace();        }

        LinkedHashSet<String> engWords = getSetOfEngWords();
        Iterator<String> it = engWords.iterator();

        while (it.hasNext()) {
            try {
                String engWord = it.next();
                List<String> translates = sendGet(engWord);
                printWriter.println("INSERT INTO public.words(name, translate) VALUES ('" + engWord + "','" + translates + "');");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        printWriter.close();
    }

    private LinkedHashSet<String> getSetOfEngWords() {

        List<String> list = null;
        try {
            list = Files.readAllLines(ResourceUtils.getFile("classpath:sql/5000.txt").toPath());
        } catch (IOException e) {
            System.out.println("file not found 5000.txt");
        }
        LinkedHashSet<String> set= new LinkedHashSet<>();
        for(String line1 : list) {
            String trimmedLine = line1.trim();
            trimmedLine = trimmedLine.replaceAll("\u00A0", "");
            set.add(trimmedLine);
        }
        return set;
    }
}
