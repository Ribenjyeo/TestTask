package ru.ribenjyeo.saoWEB.service.jwt;

public class JwtAuthResponse {

    private static final long serialVersionUID = 1L;

    private final String token;

    public JwtAuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}
