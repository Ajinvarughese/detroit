package com.Detriot.detroit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
public class Controller {
    @GetMapping("/show")
    public String helloWorld() {
        return "Hello World!";
    }
}
