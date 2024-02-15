package co.pickcake.config;


import co.pickcake.policies.filename.policy.FileNamePolicy;
import co.pickcake.policies.filename.policy.FileUuidGeneratePolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FileSystemConfig {

    @Bean
    public FileNamePolicy fileNamePolicy() {
        return new FileUuidGeneratePolicy();
    }
}
