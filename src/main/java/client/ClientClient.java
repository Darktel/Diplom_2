package client;

import base.BaseHttpClient;
import io.restassured.response.Response;
import models.Client;
import models.TokenClient;

import static io.restassured.RestAssured.given;


public class ClientClient {
    private BaseHttpClient baseHttpClient = new BaseHttpClient();

    public Response createClient(Client clientData) {
           return given()
                   .spec(baseHttpClient.requestSpecification)
                   .body(clientData)
                   .when()
                   .post("auth/register");
    }

    public String getTokenClient(Client clientData) {
        return given()
                .spec(baseHttpClient.requestSpecification)
                .body(clientData)
                .when()
                .post("auth/login")
                .then()
                .extract()
                .jsonPath()
                .getString("accessToken");
    }

    public Response loginClient(Client clientData) {
        return given()
                .spec(baseHttpClient.requestSpecification)
                .body(clientData)
                .when()
                .post("auth/login");
    }

    public Response deleteClient(String accessToken ) {
        if (accessToken != null) {
            return given()
                    .spec(baseHttpClient.requestSpecification)
                    .header("Authorization", accessToken)
                    .when()
                    .delete("auth/user");
        }
        return null;
    }

    public Response changeDataClient(String tokenClient, Client client) {
        return given()
                .spec(baseHttpClient.requestSpecification)
                .header("Authorization", tokenClient)
                .body(client)
                .when()
                .put("auth/user");
    }
}
