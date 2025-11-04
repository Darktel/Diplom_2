package models;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
public class Client {
    private final String name;
    private final String email;
    private final String password;
}
