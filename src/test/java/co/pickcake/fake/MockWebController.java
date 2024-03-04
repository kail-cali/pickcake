package co.pickcake.fake;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockWebController {
    /* ISU #33
    *  컨테이너 베이스 테스트로 변경함에 따라 기존에 가벼운 테스트, 유닛 테스트(서비스 계층)의 비즈니스 로직들을 테스트하기 위한 Mock Controller
    *  가짜 컨트롤러만 테스트에 올려서 로직들 위주로 검증하기 위함 */
}
