package com.gvstave.mistergift.service.geoip;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;

/**
 * It allow client geolocalizing.
 */
@Service
public class GeolocationService {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(GeolocationService.class);

    /** The geolocation externa API endpoint service. */
    private static final String SERVICE_SERVER_URL = "http://api.ipinfodb.com/v3/ip-city/?key=%s&ip=%s&format=json";

    /** The environment. */
    @Inject
    private Environment environment;

    /**
     * Returns the client IP address.
     *
     * @param request The servlet request.
     * @return The client IP.
     */
    public String requestClientIp(HttpServletRequest request) {
        System.setProperty("java.net.preferIPv4Stack" , "true");
        String ipAddress = request.getHeader("X-FORWARDED-FOR");

        // try getting header from nging proxy pass
        if (ipAddress == null) {
            ipAddress = request.getHeader("X-Real-IP");
        }

        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        return ipAddress;
    }

    /**
     * Returns the client city.
     *
     * @param ip The client ip.
     * @return The city.
     * @throws IOException If output stream is corrupted.
     */
    public Optional<GeolocationResult> requestClientGeolocation(String ip) throws IOException {
        Objects.requireNonNull(ip);
        LOGGER.debug("Ip Address is " + ip);

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
                return Optional.of(
                    new GeolocationResult(
                        rootNode.path("countryName").asText(),
                        rootNode.path("regionName").asText(),
                        rootNode.path("cityName").asText()
                    )
                );
            }
        }

        return Optional.empty();
    }

}
