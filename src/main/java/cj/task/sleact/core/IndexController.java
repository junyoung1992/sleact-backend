package cj.task.sleact.core;

import cj.task.sleact.config.auth.LoginUser;
import cj.task.sleact.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor
public class IndexController {

    @Value("${oauth2.redirectUrl}")
    private String redirectUrl;

    @RequestMapping("/")
    public ModelAndView redirect(@LoginUser SessionUser user) {
        String redirect = user != null
                ? redirectUrl + "/workspace/sleact/channel/일반"
                : redirectUrl;

        RedirectView vf = new RedirectView(redirect);
        vf.setStatusCode(HttpStatus.FOUND);
        return new ModelAndView(vf);
    }
    
}
