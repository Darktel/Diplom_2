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


public class TestCreateOrder {
    private ClientIngredient clientIngredient = new ClientIngredient();
    private final Faker faker = new Faker();
    private final ClientClient clientClient = new ClientClient();
    private Client client;
    private List<String> orderIngredient;
    private ClientOrder clientOrder = new ClientOrder();
    private CheckOrder checkOrder = new CheckOrder();
    private List<String> brokenIngredient = new ArrayList<>();

    @BeforeEach
    @DisplayName("Подготовка пользователя для тестов и ингредиентов")
    public void setUp() {
        orderIngredient = new ArrayList<>();
        client = new Client(faker.name().firstName(), faker.internet().emailAddress(), faker.internet().password());
        Ingredient allIngredient = clientIngredient.getIngredientList();
        orderIngredient.add(allIngredient.getData().get(1).get_id());
        orderIngredient.add(allIngredient.getData().get(4).get_id());
        orderIngredient.add(allIngredient.getData().get(2).get_id());
        orderIngredient.add(allIngredient.getData().get(5).get_id());
    }

    @AfterEach
    @DisplayName("Удаление пользователя")
    public void tearDown() {
        clientClient.deleteClient(clientClient.getTokenClient(client));
    }

    @Test
    @DisplayName("Проверка создания заказа, авторизованным пользователем.")
    public void TestCreateOrderWithAuthorizationPassed() {
        clientClient.createClient(client);
        Order order = new Order(orderIngredient);
        Response response = clientOrder.createOrderWithAuthorization(order, clientClient.getTokenClient(client));
        checkOrder.checkStatusCodeOk(response);
        checkOrder.checkSuccessCreateOrder(response);
    }

    @Test
    @DisplayName("Проверка создания заказа, не авторизованным пользователем")
    public void TestCreateOrderWithoutAuthorizationFalse() {
        Order order = new Order(orderIngredient);
        Response response = clientOrder.createOrderWithoutAuthorization(order);
        checkOrder.checkStatusCodeOk(response);
        checkOrder.checkSuccessCreateOrder(response);
    }

    @Test
    @DisplayName("Проверка создания заказа, авторизованным пользователем без ингредиентов.")
    public void TestCreateOrderWithoutIngredient() {
        clientClient.createClient(client);
        Response response = clientOrder.createOrderWithoutIngredient(clientClient.getTokenClient(client));
        checkOrder.checkStatusCodeBadRequest(response);
        checkOrder.checkFailedCreateOrder(response);
    }

    @Test
    @DisplayName("Проверка создания заказа, авторизованным пользователем.")
    public void TestCreateOrderBrokenIngredient() {
        clientClient.createClient(client);
        brokenIngredient = List.of("61c0c5a71d1f82001bdaaa9996e", "61c0c5a71d1f82001bda999f", "61c0c5a71d1f82001bdaa999");
        Order order = new Order(brokenIngredient);
        Response response = clientOrder.createOrderWithAuthorization(order, clientClient.getTokenClient(client));
        checkOrder.checkStatusCodeServerError(response);
    }

}
