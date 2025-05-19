package app.Auth;

import lombok.Getter;
import lombok.Setter;
import app.entity.User;

@Getter
@Setter
public class Login extends User {

	private String username;
	private String password;
	
	  public String getUsername() {
        return username;
    }

    // Setter para username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter para password
    public String getPassword() {
        return password;
    }

    // Setter para password
    public void setPassword(String password) {
        this.password = password;
    }

}