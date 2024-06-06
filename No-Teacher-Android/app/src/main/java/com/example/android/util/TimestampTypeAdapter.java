package com.example.android.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @Author : zhang
 * @Date : Created in 2024/5/27 16:42
 * @Decription :
 */

public class TimestampTypeAdapter extends TypeAdapter<Timestamp> {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    @Override
    public void write(JsonWriter out, Timestamp value) throws IOException {
        out.value(value == null ? null : dateFormat.format(value));
    }

    @Override
    public Timestamp read(JsonReader in) throws IOException {
        try {
            return in == null ? null : new Timestamp(dateFormat.parse(in.nextString()).getTime());
        } catch (ParseException e) {
            throw new IOException(e);
        }
    }
}