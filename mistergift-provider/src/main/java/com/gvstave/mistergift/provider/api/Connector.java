package com.gvstave.mistergift.provider.api;

import com.gvstave.mistergift.provider.common.ParameterBag;
import org.codehaus.jettison.json.JSONObject;

import java.util.List;

public interface Connector {

    List<JSONObject> fetch(QueryType queryType, ParameterBag parameterBag);

}
