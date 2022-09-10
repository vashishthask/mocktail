package in.malonus.mocktail.springboot.app.controller;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;

import in.malonus.mocktail.metadata.MethodMocktail;
import in.malonus.mocktail.springboot.app.service.PublicApiService;

class PublicApiTest {
    private TestRestTemplate restTemplate = new TestRestTemplate();
    private PublicApiService service;

    @Test
    void test() {
        MethodMocktail methodMocktail = new MethodMocktail();
        methodMocktail.setUp(this);
        service = new PublicApiService(restTemplate.getRestTemplate());
        String dogBreeds = service.getDogBreeds();
        assertNotNull(dogBreeds);
    }
}
