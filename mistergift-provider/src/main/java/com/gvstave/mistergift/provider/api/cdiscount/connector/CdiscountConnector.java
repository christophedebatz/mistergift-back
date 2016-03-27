package com.gvstave.mistergift.provider.api.cdiscount.connector;


import com.gvstave.mistergift.provider.Util.HttpClient;
import com.gvstave.mistergift.provider.api.Connector;

import java.util.Map;

public class CdiscountConnector implements Connector {

    @Override
    public String fetch(Map<String, String> parameters) {
        HttpClient client = new HttpClient();
        //client.doGet()

        return null;
    }
}
