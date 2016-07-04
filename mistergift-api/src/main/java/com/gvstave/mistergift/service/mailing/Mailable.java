package com.gvstave.mistergift.service.mailing;

import java.util.Map;

public interface Mailable {

    void send(String expeditor, final String[] recipients, final Map<String, Object> data);

}
