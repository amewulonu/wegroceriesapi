package com.wegroceries.controllers;  // Adjust the package name to match the new package

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/")
    public String testEndpoint() {
        return "Test endpoint is working!";
    }
}
