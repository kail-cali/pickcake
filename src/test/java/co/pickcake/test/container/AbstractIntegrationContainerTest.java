package co.pickcake.test.container;

import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.utility.DockerImageName;


public abstract class AbstractIntegrationContainerTest {
    public static GenericContainer<?> redis =
            new GenericContainer<>(DockerImageName.parse("redis:6")).withExposedPorts(6379);
    public static MariaDBContainer<?> mariadb  = new MariaDBContainer<>(DockerImageName.parse("mariadb:10.3.1"))
            .withDatabaseName("pickcake")
            .withUsername("test")
            .withPassword("test");

    static {
        mariadb.withReuse(true);
        mariadb.start();
        redis.start();
        System.setProperty("spring.redis.host", redis.getHost());
        System.setProperty("spring.redis.port", redis.getMappedPort(6379).toString());
    }

    @BeforeAll
    static void beforeAll() {

    }
}

