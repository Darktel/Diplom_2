import client.CheckClient;
import client.ClientClient;
import io.restassured.response.Response;
import models.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import net.datafaker.Faker;

public class TestCreateClient {
    private final ClientClient apiClient = new ClientClient();
    private final CheckClient checkClient = new CheckClient();
    private final Faker faker = new Faker();
    private Client client;

    @BeforeEach
    public void setUp() {
        client = new Client(faker.name().firstName(), faker.internet().emailAddress(), faker.internet().password());

    }

    @Test
    @DisplayName("Проверка успешного создания пользователя")
    public void testCreateClient() {
        Response response = apiClient.createClient(client);
        checkClient.checkStatusCodeOK(response);
        checkClient.checkSuccessCreateUser(response, client);
    }

    @Test
    @DisplayName("Проверка ошибки при повторном создании пользователя")
    public void testCreateClientDuplicate() {
        apiClient.createClient(client);
        Response response = apiClient.createClient(client);
        checkClient.checkStatusCodeForbidden(response);
        checkClient.checkResponseForbidden(response);
    }

    @AfterEach
    public void tearDown() {
        apiClient.deleteClient(apiClient.getTokenClient(client));
    }
}
