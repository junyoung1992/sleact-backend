package cj.task.sleact.core;

import cj.task.sleact.config.auth.LoginUser;
import cj.task.sleact.config.auth.dto.SessionUser;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class IndexController {

    @RequestMapping("/")
    public ModelAndView redirect(@LoginUser SessionUser user) {
        String redirectUrl = user != null
                ? "http://localhost:3090/workspace/sleact/channel/일반"
                : "http://localhost:3090";

        RedirectView vf = new RedirectView(redirectUrl);
        vf.setStatusCode(HttpStatus.FOUND);
        return new ModelAndView(vf);
    }
}
