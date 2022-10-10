package cj.task.sleact.core.member.controller;

import cj.task.sleact.common.constants.ApiUrlConstants;
import cj.task.sleact.config.auth.LoginUser;
import cj.task.sleact.config.auth.dto.SessionUser;
import cj.task.sleact.core.member.controller.dto.response.UserInfoRes;
import cj.task.sleact.core.member.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = ApiUrlConstants.User.BASE_URL)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public UserInfoRes getUserInfo(@LoginUser SessionUser user) {
        if (user == null) {
            return null;
        }

        return userService.findUserInfo(user.getId());
    }

}
