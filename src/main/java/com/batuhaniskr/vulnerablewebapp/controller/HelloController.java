package com.batuhaniskr.vulnerablewebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

    /**
     * vulnerable xss payload for jsp
     * @param model
     * @param name
     * @return
     */
    @GetMapping("/hello")
    public String hello(Model model,
                        @RequestParam(value = "name", required = false,
                                defaultValue = "World") String name) {
        model.addAttribute("name", name);

        return "hello";
    }
}
