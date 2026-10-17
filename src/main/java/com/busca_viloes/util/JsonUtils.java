package com.busca_viloes.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Converte um Objeto Java em texto JSON (para enviar na resposta HTTP)
    public static String toJson(Object object) throws Exception {
        return objectMapper.writeValueAsString(object);
    }

    // Converte um JSON recebido do Insomnia em Objeto Java
    public static <T> T fromJson(InputStream inputStream, Class<T> clazz) throws Exception {
        return objectMapper.readValue(inputStream, clazz);
    }
}