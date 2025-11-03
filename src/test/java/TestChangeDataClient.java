import client.CheckClient;
import client.ClientClient;
import io.restassured.response.Response;
import models.Client;
import net.datafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestChangeDataClient {
    private final ClientClient apiClient = new ClientClient();
    private final CheckClient checkClient = new CheckClient();
    private final Faker faker = new Faker();
    private Client client;
    private Client client2;
    private Client clientUpdate;

    @BeforeEach
    public void setUp() {
        client = new Client(faker.name().firstName(), faker.internet().emailAddress(), faker.internet().password());
        client2 =  new Client(faker.name().firstName(), faker.internet().emailAddress(), faker.internet().password());
        apiClient.createClient(client);
        apiClient.createClient(client2);
    }

    @Test
    @DisplayName("Проверка успешного изменения данных пользователя")
    public void testChangeDataClient() {
        clientUpdate = new Client(client.getName()+"123", "123"+faker.internet().emailAddress(), client.getPassword()+"123");
        Response response = apiClient.changeDataClient(apiClient.getTokenClient(client), clientUpdate);
        checkClient.checkStatusCodeOK(response);
        checkClient.checkSuccessChangeDataClient(response, clientUpdate);
    }

    @Test
    @DisplayName("Проверка ошибки при изменении данных пользователя на существующий email")
    public void testChangeDataClientBusyEmail() {
        clientUpdate = new Client(client.getName()+"123", client2.getEmail(), client.getPassword());
        Response response = apiClient.changeDataClient(apiClient.getTokenClient(client), clientUpdate);
        checkClient.checkStatusCodeForbidden(response);
        checkClient.checkErrorChangeDataClientBusyEmail(response, clientUpdate);
    }

    @Test
    @DisplayName("Проверка успешного изменения данных пользователя")
    public void testChangeDataClientWithoutLogin() {
        clientUpdate = new Client(client.getName()+"123", "123"+faker.internet().emailAddress(), client.getPassword());
        Response response = apiClient.changeDataClientWithoutLogin(clientUpdate);
        checkClient.checkStatusCodeUnauthorized(response);
        checkClient.checkErrorUpdateDataClient(response);
    }

    @AfterEach
    public void tearDown() {
        apiClient.deleteClient(apiClient.getTokenClient(client));
        apiClient.deleteClient(apiClient.getTokenClient(clientUpdate));
        apiClient.deleteClient(apiClient.getTokenClient(client2));
    }

}

