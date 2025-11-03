package me.manishcodes.hotelapplication.service;

import me.manishcodes.hotelapplication.dto.AppUserDto;
import me.manishcodes.hotelapplication.entity.AppUser;
import me.manishcodes.hotelapplication.entity.Hotel;
import me.manishcodes.hotelapplication.enums.Gender;
import me.manishcodes.hotelapplication.enums.Role;
import me.manishcodes.hotelapplication.repository.AppUserRepo;
import me.manishcodes.hotelapplication.repository.HotelRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AppUserService {

    private final AppUserRepo appUserRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final HotelRepo hotelRepo;
    private final CloudinaryService cloudinaryService;

    public AppUserService(HotelRepo hotelRepo,
                          CloudinaryService cloudinaryService,
                          AppUserRepo appUserRepo,
                          JwtService jwtService ,
                          PasswordEncoder bCryptPasswordEncoder,
                          AuthenticationManager authenticationManager){
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.hotelRepo = hotelRepo;
        this.cloudinaryService = cloudinaryService;
    }

    public AppUser register(AppUserDto dto , MultipartFile file){
        Hotel hotel = null;
        Role role = Role.valueOf(dto.getRole().toUpperCase());
        if(role == Role.MANAGER || role == Role.STAFF){
            hotel = hotelRepo.findById(dto.getHotelId())
                    .orElseThrow(() -> new RuntimeException("Hotel not found"));
        }

        String imgUrl = null;
        String publicId = null;

        if(file != null && !file.isEmpty()){
            try {
                CloudinaryService.UploadResult result = cloudinaryService.upload(file);
                imgUrl = result.url();
                publicId = result.publicId();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        AppUser appUser = new AppUser();
        appUser.setEmail(dto.getEmail());
        appUser.setName(dto.getName());
        appUser.setHotel(hotel);
        appUser.setRole(role);
        appUser.setProfileImgUrl(imgUrl);
        appUser.setPublicId(publicId);
        appUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        appUser.setGender(Gender.valueOf(dto.getGender().toUpperCase()));
        appUser.setPhoneNumber(dto.getPhoneNumber());

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
