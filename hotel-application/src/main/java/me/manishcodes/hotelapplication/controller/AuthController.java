package me.manishcodes.hotelapplication.controller;

import me.manishcodes.hotelapplication.dto.AppUserDto;
import me.manishcodes.hotelapplication.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
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
            return ResponseEntity.ok(userService.register(appUser, profileImg));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        try {
            String val = userService.login(email, password);
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
