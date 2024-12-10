package ru.shalagin;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.shalagin.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Communication {

    private final RestTemplate restTemplate;

    private final String URL = "http://94.198.50.185:7081/api/users";

    private HttpHeaders headersWithCookies;

    //************************************************************
//    Список URL для операций и типы запросов:
//
//    Получение всех пользователей - …/api/users ( GET )
//    Добавление пользователя - …/api/users ( POST )
//    Изменение пользователя - …/api/users ( PUT )
//    Удаление пользователя - …/api/users /{id} ( DELETE )

//    API по URL - http://94.198.50.185:7081/api/users
    //************************************************************

    public HttpHeaders getHeaders(ResponseEntity responseEntity) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<String> cookies = responseEntity.getHeaders().get("Set-Cookie");
        headers.set("Cookie",cookies.stream().collect(Collectors.joining(";")));
        System.out.println("Cookies:");
        cookies.stream().forEach((System.out::println));
        return headers;
    }

    //    Получение всех пользователей - …/api/users ( GET )
    public List<User> getUsersList() {
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<User>>() { });
        List<User> listUsers = responseEntity.getBody();

        this.headersWithCookies = getHeaders(responseEntity);

        return listUsers;
    }

    //    Добавление пользователя - …/api/users ( POST )
    public String create(User user) {
        HttpEntity<User> entity = new HttpEntity<>(user, headersWithCookies);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, entity,String.class);
        System.out.println("New user was created!");
        return responseEntity.getBody();
    }

    //    Изменение пользователя - …/api/users ( PUT )
    public String update (User user) {
        HttpEntity<User> entity = new HttpEntity<>(user, headersWithCookies);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, entity,String.class);
        System.out.println("User was updated!");
        return responseEntity.getBody();
    }


    //    Удаление пользователя - …/api/users /{id} ( DELETE )
    public String delete(Long id) {
        HttpEntity<User> entity = new HttpEntity<>(headersWithCookies);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL + "/" + id, HttpMethod.DELETE, entity,String.class);
        System.out.println("User was deleted!");
        return responseEntity.getBody();
    }

}
