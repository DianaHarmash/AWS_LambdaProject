package com.example.diplomaspringproject1_0.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deanery")
public class DeaneryController {

    @GetMapping("/main")
    public String getDeaneryInformation() {
        return "Hello, World!";
    }
}
