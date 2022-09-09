package app.service;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class BookRestService {
    private RestTemplate restTemplate;
    
    private String host;
    private int port;
    public BookRestService(RestTemplate restTemplate, String host, int port) {
        this.restTemplate = restTemplate;
        this.host = host;
        this.port = port;
    }
    
    public <T> T postBook(String url, Object request, Class<T> responseType, Map<String, ?> urlVariables ){
        return restTemplate.postForObject(host + port + url, request, responseType,
                urlVariables);
    }
    
    public <T> T getBookForId(String url, long bookId, Class<T> responseType ){
        return restTemplate.getForObject(host + port + "/book/" + bookId, responseType);
    }
    
    public void deleteBook(String url, String bookId, Map<String, ?> urlVariables ){
        restTemplate.delete(host + port + "/book/" + bookId, urlVariables);
    }
    
    public <T> T updateBook(String url, HttpMethod method, long bookId, HttpEntity<?> requestEntity, Class<T> responseType
            , Map<String, ?> urlVariables){
        return  restTemplate
                .exchange(host + port + url + bookId, method, requestEntity, responseType, urlVariables)
                .getBody();
    }
}
