package net.github.rtc.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("versionController")
@RequestMapping("version")
public class VersionController {

    @RequestMapping(method = RequestMethod.GET)
    String index() {
        return "version";
    }
}
