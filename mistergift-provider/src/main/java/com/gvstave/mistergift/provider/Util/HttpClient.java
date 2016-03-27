package com.gvstave.mistergift.provider.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class HttpClient {

    /**
     *
     * @param url
     * @return
     */
    public String doGet(String url) {
        Objects.requireNonNull(url);
        HttpURLConnection connection;

        try {
            String inputLine;

            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

        } catch (IOException e) {

        }

        return null;
    }
}
