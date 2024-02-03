package co.pickcake.aop.apigateway;


import lombok.Data;
import org.springframework.stereotype.Component;

@Component @Data
public class ApiGatewayConfig {

    private String imageServerGateWay = "http://localhost:8080/";

    private String uploadApi = "api/image/store";

    private String searchCakeGateWay = "http://localhost:8080/";
    private String searchCakeApi = "/api/cake";




}
