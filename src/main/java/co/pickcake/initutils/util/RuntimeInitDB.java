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

    @PostConstruct
    @Override
    public void preinit() {

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
