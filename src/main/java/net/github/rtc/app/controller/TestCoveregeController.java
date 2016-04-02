package net.github.rtc.app.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("tests")
public class TestCoveregeController {

    @RequestMapping(method = RequestMethod.GET)
    String index() {
        return "redirect:/resources/pages/cobertura/index.html";
    }
}
