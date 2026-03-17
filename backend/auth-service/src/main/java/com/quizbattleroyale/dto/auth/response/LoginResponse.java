package com.quizbattleroyale.dto.auth.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String id;
    private String email;
    private String username;
    private String jwtToken;

    public LoginResponse(String id, String email, String username, String jwtToken) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.jwtToken = jwtToken;
    }
}
