package client;

import base.BaseHttpClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.Order;

import static io.restassured.RestAssured.given;

public class ClientOrder {
    private BaseHttpClient baseHttpClient = new BaseHttpClient();



    @Step("Создание заказа")
    public Response createOrderWithoutAuthorization(Order order) {
        return given()
                .spec(baseHttpClient.requestSpecification)
                .body(order)
                .when()
                .post("orders");
    }

    @Step("Создание заказа авторизованным пользователем")
    public Response createOrderWithAuthorization(Order order, String tokenClient) {
        return given()
                .spec(baseHttpClient.requestSpecification)
                .header("Authorization", tokenClient)
                .body(order)
                .when()
                .post("orders");
    }

    @Step("Создание заказа авторизованным пользователем без ингредиентов")
    public Response createOrderWithoutIngredient(String tokenClient) {
        return given()
                .spec(baseHttpClient.requestSpecification)
                .header("Authorization", tokenClient)
                .when()
                .post("orders");
    }

    @Step("Создание заказа авторизованным пользователем без авториации")
    public Response getOrderClientWithAuthorization(String tokenClient) {
        return given()
                .spec(baseHttpClient.requestSpecification)
                .header("Authorization", tokenClient)
                .when()
                .get("orders");
    }

    @Step("Получение заказа пользователем без авторизации")
    public Response getOrderClientWithoutAuthorization() {
        return given()
                .spec(baseHttpClient.requestSpecification)
                .when()
                .get("orders");
    }
}
