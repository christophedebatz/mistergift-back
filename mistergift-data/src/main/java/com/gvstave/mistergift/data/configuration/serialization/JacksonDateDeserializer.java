package com.gvstave.mistergift.data.configuration.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.gvstave.mistergift.data.configuration.formatter.ToISO8601;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public class JacksonDateDeserializer extends JsonDeserializer<Date> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Date deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext) throws IOException, JsonProcessingException {
        String date = jsonparser.getText();
        try {
            return new ToISO8601().parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

}
