package me.manishcodes.hotelapplication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/health")
    public String healthChecker(){
        return "The health of application 'Hotel project' is ok and working fine";
    }
}
