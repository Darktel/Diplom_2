import checks.CheckOrder;
import client.ClientClient;
import client.ClientOrder;
import ingredient.ClientIngredient;
import io.restassured.response.Response;
import models.Client;
import models.Ingredient;
import models.Order;
import net.datafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestGetOrder {
    private ClientClient clientClient = new ClientClient();
    private ClientIngredient clientIngredient = new ClientIngredient();
    private ClientOrder clientOrder  = new ClientOrder();
    private CheckOrder checkOrder = new CheckOrder();
    private Faker faker = new Faker();
    private Client client;
    private Order order;
    private Ingredient allIngredient;
    private List<String> orderIngredient  = new ArrayList<>();

    @BeforeEach
    @DisplayName("Подготовка пользователя для тестов и списка ингредиентов в заказе")
    public void setUp() {
        client = new Client(faker.name().firstName(), faker.internet().emailAddress(), faker.internet().password());
        clientClient.createClient(client);
        allIngredient = clientIngredient.getIngredientList();
        orderIngredient.add(allIngredient.getData().get(1).get_id());
        orderIngredient.add(allIngredient.getData().get(4).get_id());
        orderIngredient.add(allIngredient.getData().get(5).get_id());
        order = new Order(orderIngredient);
        clientOrder.createOrderWithAuthorization(order, clientClient.getTokenClient(client));
        clientOrder.createOrderWithAuthorization(order, clientClient.getTokenClient(client));
        clientOrder.createOrderWithAuthorization(order, clientClient.getTokenClient(client));
    }

    @AfterEach
    @DisplayName("Удаление пользователя")
    public void tearDown() {
        clientClient.deleteClient(clientClient.getTokenClient(client));
    }

    @Test
    @DisplayName("Проверка получения списка заказов авторизованным пользователем")
    public void TestGetOrder() {
        Response response = clientOrder.getOrderClientWithAuthorization(clientClient.getTokenClient(client));
        checkOrder.checkStatusCodeOk(response);
        checkOrder.checkSuccessGetOrder(response);

    }

    @Test
    @DisplayName("Проверка получения списка заказов пользователем без авторизации")
    public void TestGetOrderWithoutAuthorization() {
        Response response = clientOrder.getOrderClientWithoutAuthorization();
        checkOrder.checkStatusCodeUnauthorized(response);
        checkOrder.checkFailedGetOrder(response);
    }




}
