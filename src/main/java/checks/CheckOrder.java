package checks;

import base.BaseHttpClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import static org.hamcrest.Matchers.*;

public class CheckOrder {
    public BaseHttpClient baseHttpClient;


    @Step("Проверка успешности создания заказа")
    public void checkSuccessCreateOrder(Response response) {
        response.then().body("success", is(true))
                .body("name", notNullValue())
                .body("order.number", notNullValue());
    }

    @Step("Проверка статус кода 200")
    public void checkStatusCodeOk(Response response) {
        response.then().statusCode(HttpStatus.SC_OK);
    }

    @Step("Проверка статус кода 400")
    public void checkStatusCodeBadRequest(Response response) {
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Step("Проверка статус кода 500")
    public void checkStatusCodeServerError(Response response) {
        response.then().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Step("Проверка корректной ошибки при создании заказа если не переданы ингредиенты")
    public void checkFailedCreateOrder(Response response) {
        response.then().body("success", is(false))
                .body("message", is("Ingredient ids must be provided"));
    }


    @Step("Проверка статус кода 401")
    public void checkStatusCodeUnauthorized(Response response) {
        response.then().statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Step("Проверка корректной ошибки при получении данных заказа не авторизованным пользователем")
    public void checkFailedGetOrder(Response response) {
        response.then().body("success", is(false))
                .body("message", is("You should be authorised"));
    }

    @Step("Проверка корректности ответа при получении заказа для конкретного пользователя")
    public void checkSuccessGetOrder(Response response) {
        response.then().body("success", is(true))
                .body("orders", notNullValue())
                .body("orders", hasSize(3))
                .body("total", notNullValue())
                .body("totalToday", notNullValue());
    }
}
