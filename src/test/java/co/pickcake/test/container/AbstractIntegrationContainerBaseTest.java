package co.pickcake.test.container;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest
public abstract class AbstractIntegrationContainerBaseTest {

    @Container
    public static GenericContainer<?> redis =
            new GenericContainer<>(DockerImageName.parse("redis:6")).withExposedPorts(6379);


    @Container
    public static MariaDBContainer container = new MariaDBContainer<>()
            .withDatabaseName("pickcake")
            .withUsername("test")
            .withPassword("test");

    @BeforeAll
    public static void setup() {
        container.withReuse(true);
//        container.withInitScript()
        container.start();
        redis.start();
    }

    @AfterAll
    public static void tearDown() {
        container.stop();
        redis.stop();
    }

    @DynamicPropertySource
    public static void overridePropertiesForRedis(DynamicPropertyRegistry registry) {
        System.setProperty("spring.redis.host", redis.getHost());
        System.setProperty("spring.redis.port", redis.getMappedPort(6379).toString());
    }

    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.driver-class-name", container::getDriverClassName);
    }

    @Test
    public void testCreateContainer() {
        Assertions.assertThat(container).isNotNull();
    }





}
