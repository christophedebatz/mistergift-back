package com.gvstave.mistergift.data.configuration.formatter;

import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.databind.util.ISO8601Utils;

import java.text.FieldPosition;
import java.util.Date;
import java.util.TimeZone;

public class ToISO8601 extends ISO8601DateFormat {

    /**
     *
     * @param date
     * @param toAppendTo
     * @param fieldPosition
     * @return
     */
    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        String value = ISO8601Utils.format(date, true, TimeZone.getTimeZone("Europe/Paris"));
        toAppendTo.append(value);
        return toAppendTo;
    }
}
