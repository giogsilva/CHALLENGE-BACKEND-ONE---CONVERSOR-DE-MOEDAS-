package com.conversorDeMoedas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MoedaResponse {
    @JsonProperty("result")
    private String result;

    @JsonProperty("conversion_rates")
    private final Map<String, Double> conversionRates = new HashMap<>(); // Inicializar com um HashMap vazio

    public String getResult() {
        return result != null ? result : "Resultado não disponível"; // Mensagem padrão se result for null
    }

    public Map<String, Double> getConversionRates() {
        return conversionRates; // Retorna o mapa de taxas de conversão
    }

    // Remove os métodos set, pois não são usados
}
