package alexanderhamedinger.friendzone.security;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTempalteBuilderConfiguration {

    @Bean
    public RestTemplate createRestTemplateBuilder(RestTemplateBuilder builder) {
        return builder.build();
    }
}
