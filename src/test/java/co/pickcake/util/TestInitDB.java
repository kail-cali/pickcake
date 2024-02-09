package co.pickcake.util;

import co.pickcake.testconfig.InitCreate;
import co.pickcake.testconfig.TestDataSize;
import jakarta.annotation.Nonnull;
import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/*
*
* 형상 TestInit -> 구현부 DbInitService -> 실행부 TestInitDB
*
* */

@Component
@RequiredArgsConstructor
public class TestInitDB implements TestInit{

    @Value("${service.active}")
    private String serviceName;

    private final DBInitService initService;  // -> TestInitDB 구현체는 수정하지 말고  서비스를 customize 해서 사용할 것

    /* TODO 메서드 실행 시 파라미터 옵션을 설정할 수 있도록 변경할 예정 */

    @Override
    public void preinit(InitCreate initCreate) {
        /* 테스트 전에 만약 미리 만들어서 넣어야 할 경우 사용할 것
        *  이 메서드는 테스트 정합성을 깨트릴 수 있기 때문에 우선 사용하지 않는 것을 권장 */
        initService.preinit(initCreate);
    }

    @Override
    public TestDataSize dbInitWithDynamic() {
        return initService.dbInitWithDynamic();
    }

    @Override
    public TestDataSize dbInitWithMember() {
        return initService.dbInitWithMember();
    }

    @Override
    public TestDataSize dbInitWithItem() {
        return initService.dbInitWithItem();
    }

}
