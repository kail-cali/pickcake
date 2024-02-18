package co.pickcake.authdomain.api;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberSearchApiController {

    /* 일반 유저가 조회할 수 있는 멤버는 정책에 따라 이곳에 남길 예정이며 기존 코드는 admin 으로 refactoring */
}

