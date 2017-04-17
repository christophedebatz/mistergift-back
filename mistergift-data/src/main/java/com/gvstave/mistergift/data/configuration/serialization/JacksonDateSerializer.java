package com.gvstave.mistergift.data.configuration.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.gvstave.mistergift.data.configuration.formatter.ToISO8601;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public class JacksonDateSerializer extends JsonSerializer<Date> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeString(new ToISO8601().format(value));
        }
    }

}
