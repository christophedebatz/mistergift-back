package com.gvstave.mistergift.service.geoip;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;

/**
 * It allows client geolocalization.
 */
@Service
public class GeolocationService {

    /** The geolocation externa API endpoint service. */
    private static final String SERVICE_SERVER_URL = "http://api.ipinfodb.com/v3/ip-city/?key=%s&ip=%s&format=json";

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(GeolocationService.class);

    /** The env. */
    @Inject
    private Environment environment;

    /**
     * Returns the client city.
     *
     * @param ip The client ip.
     * @return The city.
     */
    public Optional<GeolocationResult> requestClientGeolocation (String ip) {
        Objects.requireNonNull(ip);
        LOGGER.debug("Ip Address is " + ip);

        try {
            URL url = new URL(String.format(SERVICE_SERVER_URL, environment.getProperty("service.ipinfodb.key"), ip));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                ObjectMapper objectMapper = new ObjectMapper();
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                byte[] jsonData = response.toString().getBytes();
                JsonNode rootNode = objectMapper.readTree(jsonData);

                if (rootNode.size() > 0) {
                    String countryName = rootNode.path("countryName").asText();
                    String regionName = rootNode.path("regionName").asText();
                    String cityName = rootNode.path("cityName").asText();
                    return Optional.of(new GeolocationResult(countryName, regionName, cityName));
                }
            }
        } catch (Exception e) {
            LOGGER.error("Unable to get user IP.", e);
        }

        return Optional.empty();
    }

}
