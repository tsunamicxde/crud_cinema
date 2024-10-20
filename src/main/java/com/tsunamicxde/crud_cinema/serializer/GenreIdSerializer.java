package com.tsunamicxde.crud_cinema.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.tsunamicxde.crud_cinema.model.Genre;

import java.io.IOException;

public class GenreIdSerializer extends JsonSerializer<Genre> {

    @Override
    public void serialize(Genre genre, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (genre != null) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", genre.getId());
            jsonGenerator.writeStringField("name", genre.getName());
            jsonGenerator.writeEndObject();
        } else {
            jsonGenerator.writeNull();
        }
    }
}
