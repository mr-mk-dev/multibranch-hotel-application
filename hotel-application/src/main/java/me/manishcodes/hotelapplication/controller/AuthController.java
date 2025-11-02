package me.manishcodes.hotelapplication.controller;

import me.manishcodes.hotelapplication.entity.AppUser;
import me.manishcodes.hotelapplication.repository.AppUserRepo;
import me.manishcodes.hotelapplication.service.AppUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private final AppUserService appUserService;

    public AuthController(AppUserService appUserService){
        this.appUserService=appUserService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AppUser appUser){
        return ResponseEntity.ok(appUserService.register(appUser));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestParam String email , @RequestParam String password){
        try{
            String val = appUserService.login(email,password);
            if(val.equals("Fail")){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(val);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/jwt")
    public String jwt(){
        return "jwt token is working";
    }


}
