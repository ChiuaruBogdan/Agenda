package org.fasttrackit.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ObjectMapperConfiguration {


//    static below so that it can  execute first
    private static ObjectMapper objectMapper = new ObjectMapper();


    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}