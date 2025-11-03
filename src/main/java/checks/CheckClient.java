package checks;

import base.BaseHttpClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.Client;
import org.apache.http.HttpStatus;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckClient{
    private BaseHttpClient baseHttpClient;


    @Step("Проверка статус кода 200")
    public void checkStatusCodeOK(Response response) {
         response.then().statusCode(HttpStatus.SC_OK);
    }

    @Step("Проверка статус кода 201")
    public void checkStatusCodeCreated(Response response) {
         response.then().statusCode(HttpStatus.SC_CREATED);
    }

    @Step("Проверка статус кода 401")
    public void checkStatusCodeUnauthorized(Response response) {
         response.then().statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Step("Проверка статус кода 404")
    public void checkStatusCodeNotFound(Response response) {
         response.then().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Step("Проверка успешного создания пользователя")
    public void checkSuccessCreateUser(Response response, Client client) {
        response.then()
                .body("success", is(true))
                .body("accessToken", notNullValue())
                .body("user", notNullValue())
                // Проверка вложенных полей
                .body("user.name", is(client.getName()))
                .body("user.email", is(client.getEmail()))
                // Проверка, что токены не пустые
                .body("accessToken", not(emptyString()))
                .body("refreshToken", not(emptyString()));
    }

    @Step("Проверка успешного удаления пользователя")
    public void checkSuccessDeleteUser(Response response) {
        assertTrue(response.then().extract().body().toString().contains("success"));
    }

    @Step("Проверка статус кода 403")
    public void checkStatusCodeForbidden(Response response) {
        response.then().statusCode(HttpStatus.SC_FORBIDDEN);

    }

    @Step("Проверка что в теле ответа корректные значения")
    public void checkResponseForbidden(Response response) {
        response.then().body("success", is(false))
                .body("message", is("User already exists"));
    }

    public void checkResponseErrorCreate(Response response) {
        response.then().body("success", is(false))
                .body("message", is("Email, password and name are required fields"));
    }

    public void checkSuccessLoginUser(Response response, Client client) {
        response.then()
                .body("success", is(true))
                .body("accessToken", notNullValue())
                .body("user", notNullValue())
                // Проверка вложенных полей
                .body("user.name", is(client.getName()))
                .body("user.email", is(client.getEmail()))
                // Проверка, что токены не пустые
                .body("accessToken", not(emptyString()))
                .body("refreshToken", not(emptyString()));
    }

    @Step("Проверка ошибки при некорректных данных при авторизации")
    public void checkErrorLoginClient(Response response) {
        response.then().body("success", is(false))
                .body("message", is("email or password are incorrect"));
    }

    @Step("Проверка успешности смены данны пользователя")
    public void checkSuccessChangeDataClient(Response response, Client client) {
        response.then().body("success", is(true))
                .body("user.email", is(client.getEmail()))
                .body("user.name", is(client.getName()));
    }

    @Step("Проверка корректной ошибки при смене данных не авторизованного пользователя")
    public void checkErrorUpdateDataClient(Response response) {
        response.then().body("success", is(false))
                .body("message", is("You should be authorised"));

    }

    @Step("Проверка корректности ошибки в случае если при смене используется занятый email адрес")
    public void checkErrorChangeDataClientBusyEmail(Response response, Client clientUpdate) {
        response.then().body("success", is(false))
                .body("message", is("User with such email already exists"));
    }
}
