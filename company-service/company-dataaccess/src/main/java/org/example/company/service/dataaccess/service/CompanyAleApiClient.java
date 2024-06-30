package org.example.company.service.dataaccess.service;


import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CompanyAleApiClient {
    private final WebClient webClient;

    private String aleoUrl = "https://aleo.com/pl/firmy";

    public CompanyAleApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(aleoUrl).build();
    }

    String getSite(String taxNumber) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("phrase", taxNumber).build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
