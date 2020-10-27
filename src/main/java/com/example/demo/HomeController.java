package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final LocalhostService localhostService;

    @GetMapping("/info")
    public String getInfo() {
        return localhostService.getInfo();
    }
}
