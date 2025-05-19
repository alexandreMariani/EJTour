//AuthenticationService.java
package app.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import app.Security.JwtServiceGenerator;
import app.entity.User;
import app.repository.UserRepository;

@Service
public class LoginService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtServiceGenerator jwtService;
	@Autowired
	private AuthenticationManager authenticationManager;


	
	public String logar(Login login) {

		String token = this.gerarToken(login);
		return token;

	}



	public String gerarToken(Login login) {
		
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						login.getUsername(),
						login.getPassword()
						)
				);

		User user = userRepository.findByEmail(login.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		String jwtToken = jwtService.generateToken(user);
		return jwtToken;
	}


}
