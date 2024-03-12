package com.backend.DonoDash.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/donodash/secured")
public class TestController {

    @GetMapping("/v1")
    public String securedEndpoint() {
        return "Congratulations! You've reached a secured endpoint!";
    }
}
