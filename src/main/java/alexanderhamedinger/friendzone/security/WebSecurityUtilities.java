package alexanderhamedinger.friendzone.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

@Configuration
public class WebSecurityUtilities {
    private static String salt = "Super-streng-geh€1m";

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(15, new SecureRandom(salt.getBytes()));
    }
}
