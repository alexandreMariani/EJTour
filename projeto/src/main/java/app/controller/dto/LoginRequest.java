package app.controller.dto;

/**
 * DTO para receber as credenciais de login (email e senha) via requisição POST.
 */
public class LoginRequest {
        private String email;
        private String password;

        // Getters e Setters OBRIGATÓRIOS para o @RequestBody funcionar
        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        // Construtor padrão OBRIGATÓRIO (vazio)
        public LoginRequest() {
        }
}
