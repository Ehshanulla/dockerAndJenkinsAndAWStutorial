package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ViewController {
    @GetMapping(value = "")
    public String getIndex(){
        return "index";
    }

    @GetMapping(value = "demo")
    public String getDemo(){
        return "demo";
    }
}
