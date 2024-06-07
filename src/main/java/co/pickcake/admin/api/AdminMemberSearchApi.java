package co.pickcake.admin.api;

import co.pickcake.auth.domain.Member;
import co.pickcake.auth.service.MemberService;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminMemberSearchApi {

    private final MemberService memberService;

    @GetMapping("/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    @GetMapping("/v2/members")
    public Result<Object> memberV2() {
        List<Member> findMembers = memberService.findMembers();

        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getUsername()))
                .collect(Collectors.toList());

        return new Result<>(collect.size(), collect);
    }

    @PostMapping("/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Validated Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Validated CreateMemberRequest req) {
        Member member = new Member();
        member.setUsername(req.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Validated UpdateMemberRequest request) {

        memberService.update(id, request.getName());
        Member findMembers = memberService.findById(id);
        return new UpdateMemberResponse(findMembers.getId(), findMembers.getUsername());
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }
    @Data
    @AllArgsConstructor
    static class Result<D> {
        private Integer count;
        private D data;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }
    @Data
    static class CreateMemberRequest {
        @NotEmpty
        private String name;

    }

    @Data
    static class CreateMemberResponse {
        private Long id;
        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
