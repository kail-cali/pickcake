package co.pickcake.aop.apigateway;



import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component @Getter
public class ApiGatewayConfig {

    private String httpPortocol = "http://";
    @Value("http://${server.address}:${server.port}/")
    private String imageServerGateWay;
    private String uploadApi = "api/image/store";
    @Value("http://${server.address}:${server.port}/")
    private String searchCakeGateWay;
    private String searchCakeApi = "api/cake";

}
