package co.pickcake.test.container;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
public abstract class AbstractIntegrationContainerTest {
    public static GenericContainer<?> redis =
            new GenericContainer<>(DockerImageName.parse("redis:6")).withExposedPorts(6379);
    /* TODO
    *   test container 가 제공하는 db를 커스텀 세팅하여 사용하고 싶은데
    *   아래와 같이 코드짜면 아무 일도 하지 않는 db 컨테이너만 하나 더 띄우게 됨. */
//    public static MariaDBContainer<?> mariadb  = new MariaDBContainer<>(DockerImageName.parse("mariadb:10.3.1"))
//            .withDatabaseName("pickcake")
//            .withUsername("test")
//            .withPassword("test");

    static {
//        mariadb.withReuse(true);
//        mariadb.start();
        redis.start();
        System.setProperty("spring.data.test.redis.host", redis.getHost());
        System.setProperty("spring.data.test.redis.port", redis.getMappedPort(6379).toString());
    }

    @BeforeAll
    static void beforeAll() {

    }
}

