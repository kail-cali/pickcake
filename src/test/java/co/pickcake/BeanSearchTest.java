package co.pickcake;

import co.pickcake.config.FileSystemConfig;
import co.pickcake.mapapi.distance.DistanceAlgorithm;
import co.pickcake.policies.filename.policy.FileNamePolicy;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;



@TestConfiguration
public class BeanSearchTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();

    @Test
    public void whichConfig() {

        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String defination : beanDefinitionNames) {
            System.out.println("defination = " + defination);
        }



//        System.out.println("distanceAlgorithm = " + distanceAlgorithm);
//        System.out.println("fileNamePolicy = " + fileNamePolicy);
    }


}
