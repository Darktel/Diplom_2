import checks.CheckClient;
import client.ClientClient;
import io.restassured.response.Response;
import models.Client;
import net.datafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestCreateBrokenClient {
    private final ClientClient apiClient = new ClientClient();
    private final CheckClient checkClient = new CheckClient();
    private final Faker faker = new Faker();
    private Client clientWithOutFirstName;
    private Client clientWithOutEmailAddress;
    private Client clientWithOutPassword;

    @BeforeEach
    @DisplayName("Подготовка пользователей для тестов")
    public void setUp() {
        clientWithOutFirstName = new Client(null, faker.internet().emailAddress(), faker.internet().password());
        clientWithOutEmailAddress = new Client(faker.name().firstName(),null, faker.internet().password());
        clientWithOutPassword = new Client(faker.name().firstName(), faker.internet().emailAddress(), null);
    }

    @Test
    @DisplayName("Проверка ошибки при создании пользователя без имени")
    public void testCreateClientWithoutFirstName() {
        Response response = apiClient.createClient(clientWithOutFirstName);
        checkClient.checkStatusCodeForbidden(response);
        checkClient.checkResponseErrorCreate(response);
    }

    @Test
    @DisplayName("Проверка ошибки при создании пользователя без email")
    public void testCreateClientWithoutEmailAddress() {
        Response response = apiClient.createClient(clientWithOutEmailAddress);
        checkClient.checkStatusCodeForbidden(response);
        checkClient.checkResponseErrorCreate(response);
    }

    @Test
    @DisplayName("Проверка ошибки при создании пользователя без пароля")
    public void testCreateClientWithoutPassword() {
        Response response = apiClient.createClient(clientWithOutPassword);
        checkClient.checkStatusCodeForbidden(response);
        checkClient.checkResponseErrorCreate(response);
    }

    @AfterEach
    @DisplayName("Удаление пользователей")
    public void tearDown() {
        apiClient.deleteClient(apiClient.getTokenClient(clientWithOutFirstName));
        apiClient.deleteClient(apiClient.getTokenClient(clientWithOutEmailAddress));
        apiClient.deleteClient(apiClient.getTokenClient(clientWithOutPassword));

    }
}
