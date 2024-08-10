package com.pujun.apiaccessinterfacesdk;
import com.pujun.apiaccessinterfacesdk.client.PuApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@Configuration
@ConfigurationProperties(prefix = "pujun.api.client")
@Data
public class ApiAccessClientConfig {

    private String accessKey;
    private String secretKey;

    @Bean
    public PuApiClient puApiClient(){
        return new PuApiClient(accessKey, secretKey);
    }
}
