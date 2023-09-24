package com.example.lasttest.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping(path = "/hello-world")
    public String getMessage() {
        return "HMMMM";
    }

    @GetMapping(path = "/hello-bean")
    public helloBean getBean() {
        return new helloBean("testHMMMM");
    }

    @GetMapping(path = "/hello-bean/{name}")
    public helloBean getBeanVar(@PathVariable String name) {
        return new helloBean("Hello " + name);
    }
}
