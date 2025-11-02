package me.manishcodes.hotelapplication.service;

import lombok.extern.slf4j.Slf4j;
import me.manishcodes.hotelapplication.entity.AppUser;
import me.manishcodes.hotelapplication.repository.AppUserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    private final AppUserRepo appUserRepo;

    public MyUserDetailsService(AppUserRepo appUserRepo){
        this.appUserRepo = appUserRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepo.findByEmail(username);
        if(user == null){
            log.info("User Not Found in MyUserDetailsService , user is null");
            throw  new UsernameNotFoundException("UserNotFound");
        }
        return new MyUserDetails(user);
    }
}
