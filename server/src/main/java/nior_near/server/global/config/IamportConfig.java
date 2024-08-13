package nior_near.server.global.config;

import com.siot.IamportRestClient.IamportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class IamportConfig {

    @Value("${iamport.key}")
    String apiKey;

    @Value("${iamport.secret}")
    String secretKey;

    private IamportClient iamportClient;

    @Bean
    public IamportClient iamportClient() {
        return new IamportClient(apiKey, secretKey);
    }
}
