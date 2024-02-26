package co.pickcake;

import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import org.hibernate.validator.messageinterpolation.HibernateMessageInterpolatorContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
public class PickCakeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PickCakeApplication.class, args);
    }

    @Bean
    Hibernate6Module hibernate6Module() {
        return new Hibernate6Module();
    }

}
