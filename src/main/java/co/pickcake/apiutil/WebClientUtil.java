package co.pickcake.apiutil;

import co.pickcake.config.WebClientConfig;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerErrorException;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class WebClientUtil {

    private final WebClientConfig webClientConfig;

    public <T> T get(String url, Class<T> responseDto) {
        return webClientConfig.webClient().get()
                .uri(url)
                .retrieve()
                .onStatus(
                        HttpStatus.INTERNAL_SERVER_ERROR::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new))
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new))
//                .onStatus(
//                        HttpStatus.INTERNAL_SERVER_ERROR::equals,
//                        response -> response.bodyToMono(String.class).map(Exception::new))
//                .onStatus(HttpStatus::isError,
//                        response -> switch (response.statusCode().value() ){
//                            case 400 -> Mono.error(new BadRequestException("bad request made"));
//                            case 401, 403 -> Mono.error(new Exception("auth error"));
//                            case 404 -> Mono.error(new Exception("Maybe not an error?"));
//                            case 500 -> Mono.error(new Exception("server error"));
//                            default -> Mono.error(new Exception("something went wrong"));
//                        })
////                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new HttpClientErrorException.BadRequest()))
//                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new ServerErrorException()))

                .bodyToMono(responseDto)
                .block();
//                .subscribe(System.out::println);

    }
    public <T,V> T post(String url, V requestDto, Class<T> responseDto) {
        return webClientConfig.webClient().post()
                .uri(url)
                .bodyValue(requestDto)
                .retrieve()
                .onStatus(
                        HttpStatus.INTERNAL_SERVER_ERROR::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new))
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new))
                .bodyToMono(responseDto)
                .block();
    }



}
