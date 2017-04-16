package com.gmail.zendarva.mm.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ClassDeserializer extends JsonDeserializer<Class>{
    @Override
    public Class deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        String className = p.getValueAsString();
        try {
            Class clazz = Class.forName(className);
            return clazz;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
