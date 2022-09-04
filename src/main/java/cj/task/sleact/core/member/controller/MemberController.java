package cj.task.sleact.core.member.controller;

import cj.task.sleact.common.constants.ApiUrlConstants;
import cj.task.sleact.core.member.controller.dto.request.SignUpReq;
import cj.task.sleact.core.member.controller.dto.response.MemberInfoRes;
import cj.task.sleact.core.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = ApiUrlConstants.User.BASE_URL)
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public MemberInfoRes getUserInfo() {
        return MemberInfoRes.builder().build();
    }

    @PostMapping
    public void signUp(@RequestBody @Valid SignUpReq request) {
        memberService.registerMember(request);
    }

}
