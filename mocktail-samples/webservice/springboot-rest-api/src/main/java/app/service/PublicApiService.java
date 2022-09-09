package app.service;

import org.springframework.web.client.RestTemplate;

public class PublicApiService {
    public RestTemplate restTemplate;

    public PublicApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public String getDogBreeds() {
        return restTemplate.getForObject("https://dog.ceo/api/breeds/list/all", String.class);
    }
}