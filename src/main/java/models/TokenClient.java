package models;


import lombok.Data;

import java.util.ArrayList;

@Data
public class TokenClient {
    boolean success;
    String accessToken;
    String refreshToken;
    ArrayList<String> user;
}
