package com.geotag.demo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "GeoTag Backend Connected Successfully!";
    }
}