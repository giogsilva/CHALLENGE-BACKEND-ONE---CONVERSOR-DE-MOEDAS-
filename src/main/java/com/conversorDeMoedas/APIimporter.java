package com.conversorDeMoedas;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;

public class APIimporter {
    public static MoedaResponse getMoedaResponse(String baseCurrency) throws Exception {
        String apiKey = "9f7dbb99fef4f3d85bc96f4f";
        String urlString = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + baseCurrency;

        try {
            URI uri = new URI(urlString); // Cria uma URI a partir da string
            HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection(); // Abre a conexão
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Erro na resposta da API. Código de status: " + conn.getResponseCode());
            }

            InputStream inputStream = conn.getInputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            MoedaResponse moedaResponse = objectMapper.readValue(inputStream, MoedaResponse.class);
            conn.disconnect();
            return moedaResponse;
        } catch (URISyntaxException e) {
            throw new RuntimeException("Erro na criação da URI: " + e.getMessage(), e);
        }
    }
}
