package co.pickcake.test.container;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

/* DEPRECIATE 이렇게 관리하면 라이프사이클 문제로 싱글톤 깨트리기 때문에 더이상 사용하지 않음.
*  이 예시는 예제로 놔두기로 함. 나와 같은 실수를 하는 사람이 없도록...
* */


//@Testcontainers
//@SpringBootTest
public abstract class AbstractIntegrationContainerBaseTest {
//    @Container
//    public static GenericContainer<?> redis =
//            new GenericContainer<>(DockerImageName.parse("redis:6")).withExposedPorts(6379);
//    @Container
//    public static MariaDBContainer container = new MariaDBContainer<>()
//            .withDatabaseName("pickcake")
//            .withUsername("test")
//            .withPassword("test");
//    @BeforeAll
//    public static void setup() {
//        container.withReuse(true);
////        container.withInitScript()
//        container.start();
//        redis.start();
//    }
//    @AfterAll
//    public static void tearDown() {
//        container.stop();
//        redis.stop();
//    }
//    @DynamicPropertySource
//    public static void overridePropertiesForRedis(DynamicPropertyRegistry registry) {
//        System.setProperty("spring.redis.host", redis.getHost());
//        System.setProperty("spring.redis.port", redis.getMappedPort(6379).toString());
//    }
//    @DynamicPropertySource
//    public static void overrideProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", container::getJdbcUrl);
//        registry.add("spring.datasource.username", container::getUsername);
//        registry.add("spring.datasource.password", container::getPassword);
//        registry.add("spring.datasource.driver-class-name", container::getDriverClassName);
//    }
}
