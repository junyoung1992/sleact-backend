package cj.task.sleact.core.member.controller.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class LoginReq {

    @Email
    @NotBlank
    String email;

    @NotBlank
    String password;

    @Builder
    public LoginReq(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
