package app.controller.dto;

public record LoginRequest(
        String email,
        String password) {
}
