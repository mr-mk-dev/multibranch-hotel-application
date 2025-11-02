package me.manishcodes.hotelapplication.repository;

import me.manishcodes.hotelapplication.entity.HotelImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelImageRepo extends JpaRepository<HotelImage,Long> {
}
