package me.manishcodes.hotelapplication.service;

import me.manishcodes.hotelapplication.entity.AppUser;
import me.manishcodes.hotelapplication.repository.AppUserRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

    private final AppUserRepo appUserRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public AppUserService(AppUserRepo appUserRepo,JwtService jwtService , PasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager){
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AppUser register(AppUser appUser){
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return appUserRepo.save(appUser);
    }

    public String login(String email, String password){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            AppUser user = appUserRepo.findByEmail(email);
            if(authentication.isAuthenticated()){
                return jwtService.generateToken(user.getEmail());
            }
            return "Fail";

        } catch (AuthenticationException e) {
            throw new UsernameNotFoundException("Invalid Credentials");
        }
    }
}
