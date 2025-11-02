package me.manishcodes.hotelapplication.repository;

import me.manishcodes.hotelapplication.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepo extends JpaRepository<AppUser , Long> {

}
