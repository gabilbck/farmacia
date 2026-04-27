package com.farmacia.farmacia.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String raiz() {
        return "redirect:/index.html";
    }
}