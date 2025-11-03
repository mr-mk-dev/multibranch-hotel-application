package me.manishcodes.hotelapplication.controller;

import me.manishcodes.hotelapplication.dto.AppUserDto;
import me.manishcodes.hotelapplication.entity.AppUser;
import me.manishcodes.hotelapplication.service.AppUserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AuthController {

    private final AppUserService appUserService;

    public AuthController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> register(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phoneNumber,
            @RequestParam String gender,
            @RequestParam String password,
            @RequestParam String role,
            @RequestParam Long hotelId,
            @RequestParam(value = "image", required = false) MultipartFile profileImg
    ) {
        AppUserDto appUser = new AppUserDto(name,email,phoneNumber,gender,password,role,hotelId);
        try {
            return ResponseEntity.ok(appUserService.register(appUser, profileImg));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        try {
            String val = appUserService.login(email, password);
            if (val.equals("Fail")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(val);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/jwt")
    public String jwt() {
        return "jwt token is working";
    }


}
