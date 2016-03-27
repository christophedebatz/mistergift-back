package com.gvstave.mistergift.provider.api;

import java.util.Map;

public interface Connector {

    String fetch(Map<String, String> parameters);

}
