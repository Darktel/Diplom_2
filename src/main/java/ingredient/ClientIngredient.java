package ingredient;

import base.BaseHttpClient;
import io.qameta.allure.Step;
import models.Ingredient;

import static io.restassured.RestAssured.given;

public class ClientIngredient {
    private BaseHttpClient baseHttpClient = new BaseHttpClient();

    @Step("Получение списка ингредиентов")
    public Ingredient getIngredientList() {
        return given()
                .spec(baseHttpClient.requestSpecification)
                .get("ingredients")
                .body().as(Ingredient.class);

    }
}
