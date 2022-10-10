package cj.task.sleact.core;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class IndexController {

    @RequestMapping("/")
    public ModelAndView redirect() {
        String redirectUrl = "http://localhost:3090";
        RedirectView vf = new RedirectView(redirectUrl);
        vf.setStatusCode(HttpStatus.FOUND);
        ModelAndView md = new ModelAndView(vf);
        return md;
    }
}
