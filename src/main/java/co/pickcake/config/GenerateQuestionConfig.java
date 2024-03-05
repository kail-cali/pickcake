package co.pickcake.config;

import co.pickcake.chatGPT.service.ChatGPTService;
import co.pickcake.recommend.service.GenerateQuestion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GenerateQuestionConfig {
    @Bean
    public GenerateQuestion generateQuestion(){
        return new ChatGPTService();
    }
}
