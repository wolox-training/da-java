package wolox.training.security;
import java.util.ArrayList;
import java.util.List;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;


    public CustomAuthenticationProvider() {
    }

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = userRepository.findByUserName(name);
        if(user == null || !user.validatePassword(password)) {
            throw new BadCredentialsException("Invalid credentials");
        }else {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));
            return new UsernamePasswordAuthenticationToken(name, password, grantedAuthorities);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
            UsernamePasswordAuthenticationToken.class);
    }
}
