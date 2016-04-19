package com.gvstave.mistergift.provider.api.cdiscount.connector;


import com.gvstave.mistergift.provider.api.Connector;
import com.gvstave.mistergift.provider.api.QueryType;
import com.gvstave.mistergift.provider.common.ParameterBag;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
@Service
public class CdiscountConnector implements Connector {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(CdiscountConnector.class);

    /** The environment. */
    @Inject
    private Environment env;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<JSONObject> fetch(QueryType queryType, ParameterBag parameterBag) {
        List<NameValuePair> values = parameterBag.getParameters();
        List<JSONObject> results = new LinkedList<>();

        try {
            String baseUrl = String.format("%s/%s", env.getProperty("cdiscount.api.access"), queryType.getKey());
            URI uri = new URIBuilder(baseUrl).addParameters(values).build();

            Content content = Request.Get(uri).execute().returnContent();
            String text = content.asString();

            if (ContentType.APPLICATION_JSON == content.getType()) {
                JSONArray array = new JSONArray(text);

                for (int i = 0; i < array.length(); i++) {
                    results.add(array.getJSONObject(i));
                }
            }

            throw new JSONException("Unable to jsonify response.");

        } catch (IOException | JSONException e) {
            LOGGER.error("Cannot parse Cdiscount", e);
        } catch (URISyntaxException e) {
            LOGGER.error("Cannot create URI with params={}", parameterBag, e);
        }

        return results;
    }

}
