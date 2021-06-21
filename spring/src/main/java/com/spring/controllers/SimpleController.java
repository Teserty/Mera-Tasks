package com.spring.controllers;

import com.spring.json.Hello;
import org.springframework.web.bind.annotation.*;

@RestController
public class SimpleController {
    @GetMapping("/get/{name}")
    public String testGet(@PathVariable String name){
        return "Hello "+name;
    }
    @PostMapping("/post")
    public Hello testPost(@RequestBody Hello in){
        return new Hello("Hello "+in.getName());
    }

}
