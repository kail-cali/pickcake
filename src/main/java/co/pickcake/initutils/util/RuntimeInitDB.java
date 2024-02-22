package co.pickcake.initutils.util;

import co.pickcake.initutils.testconfig.RuntimeDataItem;
import co.pickcake.initutils.testconfig.RuntimeTestDataSize;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


/*
*
* 형상 TestInit -> 구현부 DbInitService -> 실행부 TestInitDB
*
* */

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class RuntimeInitDB implements RuntimeInit{

    @Value("${deploy.service.active}")
    private String serviceName;

    private final RuntimeDBInitService initService;  // -> TestInitDB 구현체는 수정하지 말고  서비스를 customize 해서 사용할 것

    /* TODO 메서드 실행 시 파라미터 옵션을 설정할 수 있도록 변경할 예정 */

    @PostConstruct
    @Override
    public void preinit() {
        /* 테스트 전에 만약 미리 만들어서 넣어야 할 경우 사용할 것
        *  이 메서드는 테스트 정합성을 깨트릴 수 있기 때문에 우선 사용하지 않는 것을 권장 */
        if (!Objects.equals(serviceName, "test")) {

            initService.preinit();
        }
    }


    @Override
    public RuntimeTestDataSize dbInitWithDynamic() {
        return initService.dbInitWithDynamic();
    }

    @Override
    public RuntimeTestDataSize dbInitWithMember() {
        return initService.dbInitWithMember();
    }

    @Override
    public RuntimeTestDataSize dbInitWithItems() {
        return initService.dbInitWithItems();
    }

    @Override
    public RuntimeDataItem dbInitWithSingleItem() {
        return initService.dbInitWithSingleItem();
    }

}
