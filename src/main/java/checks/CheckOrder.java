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
                .body("name" , notNullValue())
                .body("order.number", notNullValue());
    }


    public void checkStatusCodeOk(Response response) {
        response.then().statusCode(HttpStatus.SC_OK);
    }

    public void checkStatusCodeBadRequest(Response response) {
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    public void checkStatusCodeServerError(Response response) {
        response.then().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    public void checkFailedCreateOrder(Response response) {
        response.then().body("success", is(false))
                .body("message" , is("Ingredient ids must be provided"));
    }
}
