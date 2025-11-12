package me.manishcodes.hotelapplication.service;

import lombok.extern.slf4j.Slf4j;
import me.manishcodes.hotelapplication.entity.Users;
import me.manishcodes.hotelapplication.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    public MyUserDetailsService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepo.findByEmail(username);
        if(user == null){
            log.info("User Not Found in MyUserDetailsService , user is null");
            throw  new UsernameNotFoundException("UserNotFound");
        }
        return new MyUserDetails(user);
    }
}
