package org.example.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.test.dto.AuthResponseDTO;
import org.example.test.dto.MusicResponseDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GetAllMusicIT {
    final RequestSpecification idServiceSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8001")
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    final RequestSpecification musicServiceSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8002")
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    @Test
    void shouldNotAuthWithInvalidCreds() {
        // 1. Нужно получить token
        // 2. С этим токеном сходить и получить список муз.композиций
        final Response authResponse = RestAssured
                .given() // предустановки - URL, headers, ...
                .spec(idServiceSpec)
                .body(
                        // language=JSON
                        """
                        {
                          "login": "vasya",
                          "password": "secret"
                        }
                        """
                )
                .when().post("/auth")
                .then().statusCode(400).extract().response();
    }

    @Test
    void shouldGetAllMusicWithAuthentication() {
        // 1. Нужно получить token
        // 2. С этим токеном сходить и получить список муз.композиций
        final Response authResponse = RestAssured
                .given() // предустановки - URL, headers, ...
                .spec(idServiceSpec)
                .body(
                        // language=JSON
                        """
                                {
                                  "login": "vasya",
                                  "password": "password"
                                }
                                """
                )
                .when().post("/auth")
                .then().statusCode(200).extract().response();

        final AuthResponseDTO authResponseDTO = authResponse.jsonPath()
                .getObject("$", AuthResponseDTO.class);

        final String token = authResponseDTO.getToken();

        // 1. Token
        // 2. GetAll -> []
        // 3. Create ->
        // 4. GetAll -> [{}]
        // 5. GetById -> {}/404
        // 6. Update -> {}/404
        // 7. Delete -> 200
        // 8. GetAll -> []
        // 9. GetById -> 404

        final Response musicGetAllResponse = RestAssured
                .given() // предустановки - URL, headers, ...
                .spec(musicServiceSpec)
                .header("X-Token", token)
                // 405 - HTTP (Method Not Allowed)
                .when().get("/music")
                .then().statusCode(200).extract().response();

        final List<MusicResponseDTO> tracks = musicGetAllResponse.jsonPath().getObject("$", new TypeRef<>() {
        });

        assertEquals(0, tracks.size());
    }
}
