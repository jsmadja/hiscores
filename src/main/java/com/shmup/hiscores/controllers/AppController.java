package com.shmup.hiscores.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Deprecated
@AllArgsConstructor
@RestController
public class AppController {

    @RequestMapping("/")
    public String getHome() {
        return "OK";
    }

}
