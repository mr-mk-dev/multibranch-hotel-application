package me.manishcodes.hotelapplication.service;

import me.manishcodes.hotelapplication.dto.AppUserDto;
import me.manishcodes.hotelapplication.entity.Users;
import me.manishcodes.hotelapplication.entity.Hotel;
import me.manishcodes.hotelapplication.enums.Gender;
import me.manishcodes.hotelapplication.enums.Role;
import me.manishcodes.hotelapplication.exception.HotelNotFoundException;
import me.manishcodes.hotelapplication.exception.ImageUploadException;
import me.manishcodes.hotelapplication.repository.UserRepo;
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
public class UserService {

    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final HotelRepo hotelRepo;
    private final CloudinaryService cloudinaryService;

    public UserService(HotelRepo hotelRepo,
                       CloudinaryService cloudinaryService,
                       UserRepo userRepo,
                       JwtService jwtService ,
                       PasswordEncoder bCryptPasswordEncoder,
                       AuthenticationManager authenticationManager){
        this.userRepo = userRepo;
        this.passwordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.hotelRepo = hotelRepo;
        this.cloudinaryService = cloudinaryService;
    }

    public Users register(AppUserDto dto , MultipartFile file){
        Hotel hotel = null;
        Role role = Role.valueOf(dto.getRole().toUpperCase());
        if(role == Role.MANAGER || role == Role.STAFF){
            hotel = hotelRepo.findById(dto.getHotelId())
                    .orElseThrow(() -> new HotelNotFoundException("Hotel Not Found with id "+dto.getHotelId()));
        }

        Users users = new Users();
        users.setEmail(dto.getEmail());
        users.setName(dto.getName());
        users.setHotel(hotel);
        users.setRole(role);
        users.setPassword(passwordEncoder.encode(dto.getPassword()));
        users.setGender(Gender.valueOf(dto.getGender().toUpperCase()));
        users.setPhoneNumber(dto.getPhoneNumber());

        if(file != null && !file.isEmpty()){
                try {
                    CloudinaryService.UploadResult result = cloudinaryService.upload(file);
                    users.setProfileImgUrl(result.url());
                    users.setPublicId(result.publicId());
                } catch (IOException e) {
                    throw new ImageUploadException("Cloudinary Service may be busy retry mechanism may get success ", e);
                }
        }

        return userRepo.save(users);
    }

    public String login(String email, String password){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            Users user = userRepo.findByEmail(email);
            if(authentication.isAuthenticated()){
                return jwtService.generateToken(user.getEmail());
            }
            return "Fail";

        } catch (AuthenticationException e) {
            throw new UsernameNotFoundException("Invalid Credentials" , e);
        }
    }
}
