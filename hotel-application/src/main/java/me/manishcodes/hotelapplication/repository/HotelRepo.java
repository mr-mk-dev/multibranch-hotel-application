package me.manishcodes.hotelapplication.repository;

import me.manishcodes.hotelapplication.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepo extends JpaRepository<Hotel,Long> {
}
