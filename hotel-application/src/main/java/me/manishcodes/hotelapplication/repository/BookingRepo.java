package me.manishcodes.hotelapplication.repository;

import me.manishcodes.hotelapplication.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepo extends JpaRepository<Booking , Long> {

}
