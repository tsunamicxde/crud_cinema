package com.tsunamicxde.crud_cinema.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.tsunamicxde.crud_cinema.model.Movie;

import java.io.IOException;

public class MovieIdSerializer extends JsonSerializer<Movie> {

    @Override
    public void serialize(Movie movie, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (movie != null) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", movie.getId());
            jsonGenerator.writeEndObject();
        } else {
            jsonGenerator.writeNull();
        }
    }
}
