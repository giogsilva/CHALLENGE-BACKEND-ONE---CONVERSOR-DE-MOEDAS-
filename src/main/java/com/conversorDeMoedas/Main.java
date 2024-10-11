package com.conversorDeMoedas;

import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final String API_KEY = "9f7dbb99fef4f3d85bc96f4f"; // Chave da API
    private static final String[] MOEDAS_SUPORTADAS = { // Moedas suportadas
            "BRL", "USD", "CAD", "EUR", "GBP", "MXN", "ARS"
    };

    private static final Map<String, String> NOME_MOEDAS = Map.of( // Nome das moedas
            "BRL", "Real Brasileiro",
            "USD", "Dólar Americano",
            "CAD", "Dólar Canadense",
            "EUR", "Euro",
            "GBP", "Libra Esterlina",
            "MXN", "Peso Mexicano",
            "ARS", "Peso Argentino"
    );

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Scanner para entrada de dados
        System.out.println("Bem-Vindo ao conversor de moedas!");
        exibirMoedasSuportadas(); // Exibe as moedas suportadas

        while (true) {
            String moedaBase = solicitarMoeda(scanner, "Digite a moeda de origem (ex: USD): "); // Moeda de origem
            String moedaDestino = solicitarMoeda(scanner, "Digite a moeda para conversão (ex: BRL): "); // Moeda para conversão
            double valorParaConverter = solicitarValor(scanner); // Valor a ser convertido

            try {
                MoedaResponse moedaResponse = APIimporter.getMoedaResponse(moedaBase); // Chamada da API
                realizarConversao(moedaResponse, moedaDestino, valorParaConverter); // Realiza a conversão
            } catch (Exception e) {
                System.out.println("Erro ao processar a requisição: " + e.getMessage()); // Mensagem de erro
            }

            if (!continuarConversao(scanner)) { // Pergunta se deseja continuar
                break; // Sai do loop se a resposta for não
            }
        }

        System.out.println("Obrigado! Aplicação concluída."); // Mensagem de despedida
        scanner.close(); // Fecha o scanner
    }

    private static void exibirMoedasSuportadas() {
        System.out.println("Moedas suportadas:");
        for (String sigla : MOEDAS_SUPORTADAS) {
            System.out.println(sigla + " - " + NOME_MOEDAS.get(sigla)); // Exibe a sigla e o nome da moeda
        }
    }

    private static String solicitarMoeda(Scanner scanner, String mensagem) {
        String moeda;
        while (true) {
            System.out.print(mensagem); // Exibe mensagem para o usuário
            moeda = scanner.nextLine().toUpperCase(); // Lê a moeda em maiúsculas
            if (isMoedaValida(moeda)) {
                return moeda; // Retorna a moeda se for válida
            } else {
                System.out.println("Moeda inválida. Tente novamente."); // Mensagem de erro
            }
        }
    }

    private static double solicitarValor(Scanner scanner) {
        while (true) {
            System.out.print("Digite o valor que deseja converter: "); // Mensagem para solicitar valor
            if (scanner.hasNextDouble()) {
                double valor = scanner.nextDouble(); // Lê o valor a ser convertido
                scanner.nextLine(); // Limpa o buffer
                return valor; // Retorna o valor
            } else {
                System.out.println("Valor inválido. Tente novamente."); // Mensagem de erro
                scanner.nextLine(); // Limpa o buffer
            }
        }
    }

    private static void realizarConversao(MoedaResponse moedaResponse, String moedaDestino, double valorParaConverter) {
        Map<String, Double> taxasConversao = moedaResponse.getConversionRates(); // Obtém as taxas de conversão

        if (taxasConversao.containsKey(moedaDestino)) {
            double taxaConversao = taxasConversao.get(moedaDestino); // Taxa de conversão
            double valorConvertido = valorParaConverter * taxaConversao; // Cálculo do valor convertido
            System.out.println("A taxa de conversão de " + moedaResponse.getResult() + " para " + moedaDestino + " é: " + taxaConversao);
            System.out.println("O valor convertido é: " + valorConvertido); // Mostra o valor convertido
        } else {
            System.out.println("A moeda para conversão não é válida."); // Mensagem de erro se a moeda não for válida
        }
    }

    private static boolean continuarConversao(Scanner scanner) {
        System.out.print("Deseja realizar outra conversão? Digite sim ou nao: "); // Pergunta se o usuário deseja continuar
        String resposta = scanner.nextLine().trim().toLowerCase(); // Lê a resposta do usuário
        return resposta.equals("sim"); // Retorna verdadeiro se a resposta for sim
    }

    private static boolean isMoedaValida(String moeda) {
        for (String moedaSuportada : MOEDAS_SUPORTADAS) { // Verifica se a moeda está entre as suportadas
            if (moedaSuportada.equals(moeda)) {
                return true; // Retorna verdadeiro se a moeda for válida
            }
        }
        return false; // Retorna falso se a moeda não for válida
    }
}
