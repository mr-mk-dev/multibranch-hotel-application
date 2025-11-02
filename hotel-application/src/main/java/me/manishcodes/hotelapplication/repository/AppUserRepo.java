package me.manishcodes.hotelapplication.repository;

import me.manishcodes.hotelapplication.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface AppUserRepo extends JpaRepository<AppUser , Long> {

    AppUser findByEmail(String email);

}
