import client.CheckClient;
import client.ClientClient;
import io.restassured.response.Response;
import models.Client;
import net.datafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestLoginClient {
    private final ClientClient apiClient = new ClientClient();
    private final CheckClient checkClient = new CheckClient();
    private final Faker faker = new Faker();
    private Client client;

    @BeforeEach
    public void setUp() {
        client = new Client(faker.name().firstName(), faker.internet().emailAddress(), faker.internet().password());
        apiClient.createClient(client);
    }

    @Test
    @DisplayName("Проверка успешной авторизации пользователя")
    public void testLoginClient() {
        Response response = apiClient.loginClient(client);
        checkClient.checkStatusCodeOK(response);
        checkClient.checkSuccessLoginUser(response, client);
    }

    @Test
    @DisplayName("Проверка ошибки авторизации пользователя без email")
    public void testLoginClientWithoutLogin() {
//      Переопределение кредов для авторизации.
        Client ctestLoginClientWithoutEmail = new Client(client.getName(), null, client.getPassword());
        Response response = apiClient.loginClient(ctestLoginClientWithoutEmail);
        checkClient.checkStatusCodeUnauthorized(response);
        checkClient.checkErrorLoginClient(response);
    }

    @Test
    @DisplayName("Проверка ошибки авторизации пользователя без пароля")
    public void testLoginClientWithoutPassword() {
//      Переопределение кредов для авторизации.
        Client ctestLoginClientWithoutPassword = new Client(client.getName(), client.getEmail(), null);
        Response response = apiClient.loginClient(ctestLoginClientWithoutPassword);
        checkClient.checkStatusCodeUnauthorized(response);
        checkClient.checkErrorLoginClient(response);
    }

    @AfterEach
    public void tearDown() {
        apiClient.deleteClient(apiClient.getTokenClient(client));
    }

}
